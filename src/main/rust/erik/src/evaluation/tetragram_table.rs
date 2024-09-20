use super::ngram_table::NGramTable;

/// For fast access to tetragram data, such as tetragram frequencies.
pub struct TetragramsTable {
    table: Vec<f32>,
}

impl NGramTable for TetragramsTable {
    /// Get the value of a tetragram.
    #[inline(always)]
    fn get_value(&self, tetragram: &[u8]) -> f32 {
        let index = TetragramsTable::get_index(tetragram);
        self.table[index]
    }
    /// Get the lookup index of a tetragram.
    #[inline(always)]
    fn get_index(tetragram: &[u8]) -> usize {
        tetragram[0] as usize  + tetragram[1] as usize * 26 + tetragram[2] as usize * 676 + tetragram[3] as usize * 17576
    }
    /// The size of the ngram.
    #[inline(always)]
    fn get_ngram_size(&self) -> usize {
        4
    }
    /// Create a new tetragram table.
    fn new(tetragram_data: Vec<(String, f32)>) -> TetragramsTable {
        let mut table = vec![-10.0; 456976];
        tetragram_data.iter().for_each(|(tetragram, value)| {
            let slice: Vec<u8> = tetragram.as_bytes().into_iter().map(|&x| x - 65).collect(); // Convert the tetragram to a 0-indexed slice.
            let index = TetragramsTable::get_index(&slice); // Get the index of the tetragram.
            table[index] = *value; // Set the value
        });
        TetragramsTable { table }
    }
}