pub fn read_csv_frequency_data(file_name: &str) -> Vec<(&str, f64)> {
    let path = Path::new(RESOURCES_DIR).join(file_name);
    let file = File::open(path).expect("File not found");
    let mut reader = csv::Reader::from_reader(file);
    let mut data = Vec::new();
    reader.records().for_each(|record| {
        let record = record.unwrap();
        let value: f64 = record[1].parse().expect("Failed to parse f64").log10().max(-10.0);
        data.push((record[0].to_str(), value));
    });
    data
}