use std::fs::File;
use std::io::BufRead;
use std::path::Path;

/// Reads a CSV file containing frequency data. Returns the log base 10 of the frequency.
pub fn read_csv_frequency_data_log<P: AsRef<Path>>(file_path: P) -> Vec<(String, f32)> {
    let file = File::open(file_path).expect("File not found");
    std::io::BufReader::new(file)
        .lines()
        .map(|line| {
            let line = line.unwrap();
            let mut split = line.split(",");
            let key = split.nth(0).unwrap().to_string();
            let value: f32 = split.next().unwrap().parse::<f32>().expect("Failed to parse f32").log10().max(-10.0);
            (key, value)
        })
        .collect()
}