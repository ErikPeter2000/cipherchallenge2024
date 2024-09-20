pub fn substitute(cipher_data: &Vec<u8>, key: &Vec<u8>) -> Vec<u8> {
    cipher_data.into_iter().map(|c| key[*c as usize]).collect()
}

pub fn reverse_key(key: &Vec<u8>) -> Vec<u8> {
    (0..26)
        .map(|c| key.iter().position(|&x| x == c).unwrap() as u8)
        .collect()
}