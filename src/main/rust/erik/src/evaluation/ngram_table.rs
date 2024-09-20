pub trait NGramTable{
    fn get_value(&self, ngram: &[u8]) -> f64;
    fn get_index(ngram: &[u8]) -> usize;
    fn get_ngram_size(&self) -> usize;
    fn new(ngram_data: Vec<(String, f64)>) -> Self;
}