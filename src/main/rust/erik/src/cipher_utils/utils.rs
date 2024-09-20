use rand::prelude::*;

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

pub fn swap_elements<T>(data: &mut Vec<T>, i: usize, j: usize) {
    let (left, right) = data.split_at_mut(std::cmp::min(i, j));
    std::mem::swap(&mut left[i.min(j)], &mut right[0]);
}

pub fn swap_random_elements<T>(data: &mut Vec<T>, rng: &mut ThreadRng) {
    let i = rng.gen_range(0..data.len());
    let j = rng.gen_range(0..data.len());
    swap_elements(data, i, j);
}