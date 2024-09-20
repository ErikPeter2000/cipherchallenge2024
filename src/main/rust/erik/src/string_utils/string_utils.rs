pub fn string_to_alphabet_index(text: &str) -> Vec<u8> {
    text.chars()
        .filter_map(|c| match c {
            'A'..='Z' => Some(c as u8 - b'A'),
            'a'..='z' => Some(c as u8 - b'a'),
            _ => None,
        })
        .collect()
}

pub fn alphabet_index_to_string(index: &Vec<u8>) -> String {
    index.iter()
        .map(|i| (*i + b'A') as char)
        .collect()
}