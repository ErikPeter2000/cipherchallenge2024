use std::fs::File;
use std::io::Read;
use std::path::Path;

use evaluation::data_manager;
use evaluation::ngram_table::NGramTable;
use cipher_utils::utils;
use rand::Rng;

const RESOURCES_DIR: &str = r".\..\..\..\..\resources\\";

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

fn main() {
    let plaintext = load_example_file(r"text\Orwell1984.txt", None);
    let formatted_data = utils::string_to_alphabet_index(&plaintext);
    let key = utils::string_to_alphabet_index("QWERTYUIOPASDFGHJKLZXCVBNM");

    let encrypted = ciphers::substitution_cipher::substitute(&formatted_data, &key);

    let csv_path = Path::new(RESOURCES_DIR).join("polygrams/Tetragram.csv");
    let tetragram_frequencies = data_manager::read_csv_frequency_data(csv_path);
    let tetragram_table = evaluation::tetragram_table::TetragramsTable::new(tetragram_frequencies);

    println!("Plaintext: {}", utils::alphabet_index_to_string(&formatted_data));
    println!("Encrypted: {}", utils::alphabet_index_to_string(&encrypted));

    let stopwatch = std::time::Instant::now();
    let evaluation = evaluation::ngram_evaluation::evaluate_ngrams(&formatted_data, &tetragram_table);
    println!("Evaluation: {}", evaluation);
    let encrypt_evaluation = evaluation::ngram_evaluation::evaluate_ngrams(&encrypted, &tetragram_table);
    println!("Encrypted evaluation: {}", encrypt_evaluation);

    // hill-climbing attack
    let mut best_key = utils::string_to_alphabet_index("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    let mut best_decryption = ciphers::substitution_cipher::substitute(&encrypted, &best_key);
    let mut best_evaluation = evaluation::ngram_evaluation::evaluate_ngrams(&best_decryption, &tetragram_table);
    let mut rng: rand::prelude::ThreadRng = rand::thread_rng();
    for _ in 0..1000 {
        let mut new_key = best_key.clone();
        let index1 = rng.gen_range(0..26);
        let index2 = rng.gen_range(0..26);
        let temp = new_key[index1];
        new_key[index1] = new_key[index2];
        new_key[index2] = temp;

        let new_decryption = ciphers::substitution_cipher::substitute(&encrypted, &new_key);
        let new_evaluation = evaluation::ngram_evaluation::evaluate_ngrams(&new_decryption, &tetragram_table);
        if new_evaluation > best_evaluation {
            best_decryption = new_decryption;
            best_evaluation = new_evaluation;
            best_key = new_key;
        }
    }
    println!("Time taken: {:?}", stopwatch.elapsed());
    println!("Best evaluation: {}", best_evaluation);
    let result = utils::alphabet_index_to_string(&best_decryption);
    println!("Decryption: {}", result);
}
