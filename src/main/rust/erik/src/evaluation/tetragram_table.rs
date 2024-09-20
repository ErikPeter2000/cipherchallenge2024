use super::ngram_table::NGramTable;

pub struct TetragramsTable {
    table: Vec<f64>,
}

impl NGramTable for TetragramsTable {
    #[inline(always)]
    fn get_value(&self, tetragram: &[u8]) -> f64 {
        let index = TetragramsTable::get_index(tetragram);
        self.table[index]
    }
    #[inline(always)]
    fn get_index(tetragram: &[u8]) -> usize {
        tetragram[0] as usize * 17576 + tetragram[1] as usize * 676 + tetragram[2] as usize * 26 + tetragram[3] as usize
    }
    #[inline(always)]
    fn get_ngram_size(&self) -> usize {
        4
    }
    fn new(tetragram_data: Vec<(String, f64)>) -> TetragramsTable {
        let mut table = vec![0.0; 456976];
        tetragram_data.iter().for_each(|(tetragram, value)| {
            let slice: Vec<u8> = tetragram.as_bytes().into_iter().map(|&x| x - 65).collect();
            let index = TetragramsTable::get_index(&slice);
            table[index] = *value;
        });
        TetragramsTable { table }
    }
}