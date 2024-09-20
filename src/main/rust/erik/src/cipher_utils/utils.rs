use rand::{rngs::StdRng, Rng, SeedableRng};

/// Converts a string to a 0-indexed byte vector where A=0, B=1, ..., Z=25. Any other characters are ignored.
pub fn string_to_alphabet_index(text: &str) -> Vec<u8> {
    text.chars()
        .filter_map(|c| match c {
            'A'..='Z' => Some(c as u8 - b'A'),
            'a'..='z' => Some(c as u8 - b'a'),
            _ => None,
        })
        .collect()
}

/// Converts a 0-indexed byte vector back to a string where A=0, B=1, ..., Z=25.
pub fn alphabet_index_to_string(index: &Vec<u8>) -> String {
    index.iter().map(|i| (*i + b'A') as char).collect()
}

/// A random number utility that caches random numbers between 0 and 25, for fast random indexing.
pub struct FastRandomUsize {
    index: usize,
    data: [usize; 4096],
}

impl FastRandomUsize {
    pub fn new(seed: u64) -> FastRandomUsize {
        let mut data = [0; 4096];
        let mut rng = StdRng::seed_from_u64(seed);

        // Fill the data with random numbers between 0 and 25.
        for i in 0..data.len() {
            data[i] = rng.gen_range(0..26);
        }
        
        FastRandomUsize { index: 0, data }
    }
    /// Get the next usize between 0 and 25.
    pub fn next(&mut self) -> usize {
        let value = self.data[self.index];
        self.index = (self.index + 1) % self.data.len();
        value
    }
}