pub struct TetragramsTable {
    table: Vec<f64>,
}

impl TetragramsTable for TetragramsTable {
    fn get_value(&self, tetragram: &[u8]) -> f64 {
        let index = TetragramsTable::get_index(tetragram);
        self.table[index as usize]
    }
    fn get_index(tetragram: &[u8]) -> usize {
        (tetragram[0] * 17576 + tetragram[1] * 676 + tetragram[2] * 26 + tetragram[3]) as usize
    }
    fn new(tetragram_data: Vec<(&str, f64)>) -> TetragramsTable {
        let mut table = vec![0.0; 456976];
        tetragram_data.iter().for_each(|(tetragram, value)| {
            let index = TetragramsTable::get_index(tetragram.chars().map(|c| c as u8 - b'A').collect());
            table[index] = *value;
        });
        TetragramsTable { table }
    }
}