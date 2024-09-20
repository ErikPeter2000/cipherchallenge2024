use std::fs::File;
use std::io::Read;
use std::path::Path;

const RESOURCES_DIR: &str = r".\..\..\..\..\resources\text\";

mod string_utils {
    pub mod string_utils;
}

mod ciphers {
    pub mod substitution_cipher;
}

fn load_example_file(file_name: &str, num_bytes: Option<usize>) -> String {
    let num_bytes_eval = num_bytes.unwrap_or(1024);
    let path = Path::new(RESOURCES_DIR).join(file_name);
    let file = File::open(path).expect("File not found");
    let mut contents = String::new();
    file.take(num_bytes_eval as u64).read_to_string(&mut contents).expect("Failed to read file");

    contents
}

fn main() {
    let data = load_example_file("Orwell1984.txt", None);
    let formatted_data = string_utils::string_utils::string_to_alphabet_index(&data);
    let key = string_utils::string_utils::string_to_alphabet_index("QWERTYUIOPASDFGHJKLZXCVBNM");
    let reverse_key = ciphers::substitution_cipher::reverse_key(&key);
    
    let encrypted = ciphers::substitution_cipher::substitute(&formatted_data, &key);
    let decrypted = ciphers::substitution_cipher::substitute(&encrypted, &reverse_key);

    let reconstructed_data = string_utils::string_utils::alphabet_index_to_string(&decrypted);
    println!("{:?}", reconstructed_data);
}

