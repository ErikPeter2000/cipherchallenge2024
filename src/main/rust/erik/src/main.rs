use std::fs::File;
use std::io::Read;
use std::path::Path;

use cipher_utils::utils;
use evaluation::data_manager;
use evaluation::ngram_table::NGramTable;
use indicatif::{ProgressBar, ProgressStyle};

const RESOURCES_DIR: &str = r"./../../../../resources/";

mod evaluation {
    pub mod data_manager;
    pub mod ngram_evaluation;
    pub mod ngram_table;
    pub mod tetragram_table;
}

mod cipher_utils {
    pub mod utils;
}

mod ciphers {
    pub mod substitution_cipher;
}

fn load_example_file(file_name: &str, num_bytes: Option<usize>) -> String {
    let num_bytes_eval = num_bytes.unwrap_or(1024);
    let path = Path::new(RESOURCES_DIR).join(file_name);
    let file = File::open(path).expect("File not found");
    let mut contents = String::new();
    file.take(num_bytes_eval as u64)
        .read_to_string(&mut contents)
        .expect("Failed to read file");

    contents
}

fn job(seed: u64) -> bool {
    let plaintext = load_example_file(r"text/Orwell1984.txt", Some(512));
    let formatted_data = utils::string_to_alphabet_index(&plaintext);
    let key = utils::string_to_alphabet_index("QWERTYUIOPASDFGHJKLZXCVBNM");

    let encrypted = ciphers::substitution_cipher::substitute(&formatted_data, &key);

    let csv_path = Path::new(RESOURCES_DIR).join("polygrams/Tetragram.csv");
    let tetragram_frequencies = data_manager::read_csv_frequency_data_log(csv_path);
    let tetragram_table = evaluation::tetragram_table::TetragramsTable::new(tetragram_frequencies);
    let mut rng: utils::FastRandomUsize = utils::FastRandomUsize::new(seed);

    // let start = std::time::Instant::now();
    let broken = ciphers::substitution_cipher::break_substitution_cipher(
        &encrypted,
        &tetragram_table,
        &mut rng,
        Some(10000),
    );
    // let end = std::time::Instant::now();
    let decrypted = utils::alphabet_index_to_string(&broken.0);
    // println!("Time taken: {:?}", end - start);
    // println!("Decrypted: {}", decrypted);
    return decrypted.starts_with("BYGEORGEORWELL");
}

fn main() {
    let binary_name = env!("CARGO_PKG_NAME");
    println!("Binary name: {}", binary_name);
    let mut success = 0.0;
    let total = 100;

    let pb = ProgressBar::new(total);
    pb.set_style(
        ProgressStyle::default_bar()
            .template(
                "{spinner:.green} [{elapsed_precise}] [{bar:40.cyan/blue}] {pos}/{len} ({eta})",
            )
            .unwrap()
            .progress_chars("█▉▊▋▌▍▎▏  "),
    );

    for i in 0..total {
        if job(i) {
            success += 1.0;
        }
        pb.inc(1);
    }
    println!("Accuracy: {}", success / total as f32);
}
