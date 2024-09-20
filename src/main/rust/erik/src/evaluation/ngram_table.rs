/// For fast access to ngram data, such as ngram frequencies.
pub trait NGramTable{
    /// Get the value associated with an ngram.
    fn get_value(&self, ngram: &[u8]) -> f64;
    /// Get the lookup index of an ngram.
    fn get_index(ngram: &[u8]) -> usize;
    /// The size of the ngram.
    fn get_ngram_size(&self) -> usize;
    /// Create a new ngram table.
    fn new(ngram_data: Vec<(String, f64)>) -> Self;
}