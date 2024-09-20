pub trait NGram_Table{
    fn get_value(&self, ngram: &[u8]) -> f64;
    fn new(ngram_data: Vec<(&str, f64)>) -> Self;
}