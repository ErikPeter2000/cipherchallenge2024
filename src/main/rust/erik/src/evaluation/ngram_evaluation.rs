fn evaluate_ngrams(text: &vec<u8>, ngram_length: u8, table: NGram_Table) -> f64 {
    text.windows(ngram_length)
        .map(|tetragram| table.get_value(tetragram))
        .sum()
}