use super::ngram_table::NGramTable;

/// Sums the values of the ngrams in the text. Useful as a fitness function.
pub fn evaluate_ngrams<T: NGramTable + Sync>(text: &Vec<u8>, table: &T) -> f64 {
    let size = table.get_ngram_size();
    text.windows(size)
        .map(|tetragram| table.get_value(tetragram))
        .sum()
}