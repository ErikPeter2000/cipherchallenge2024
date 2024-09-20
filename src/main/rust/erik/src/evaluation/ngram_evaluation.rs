use super::ngram_table::NGramTable;

pub fn evaluate_ngrams<T: NGramTable>(text: &Vec<u8>, table: &T) -> f64 {
    let size = table.get_ngram_size();
    text.windows(size)
        .map(|tetragram| table.get_value(tetragram))
        .sum()
}