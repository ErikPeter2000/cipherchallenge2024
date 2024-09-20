use std::fs::File;
use std::io::BufRead;
use std::path::Path;

pub fn read_csv_frequency_data<P: AsRef<Path>>(file_path: P) -> Vec<(String, f64)> {
    let file = File::open(file_path).expect("File not found");
    std::io::BufReader::new(file)
        .lines()
        .map(|line| {
            let line = line.unwrap();
            let mut split = line.split(",");
            let key = split.nth(0).unwrap().to_string();
            let value: f64 = split.next().unwrap().parse::<f64>().expect("Failed to parse f64").log10().max(-10.0);
            (key, value)
        })
        .collect()
}