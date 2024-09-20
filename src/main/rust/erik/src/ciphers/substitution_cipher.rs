use crate::{
    cipher_utils::utils::{self, FastRandomUsize},
    evaluation::{ngram_evaluation, tetragram_table::TetragramsTable},
};

/// Transforms the data using a substitution cipher.
pub fn substitute(cipher_data: &[u8], key: &[u8]) -> Vec<u8> {
    cipher_data.into_iter().map(|c| key[*c as usize]).collect()
}

/// Creates a key that reverses the input key. Encrypting with the reversed key is the same as decrypting with the original key.
pub fn reverse_key(key: &[u8]) -> Vec<u8> {
    (0..26)
        .map(|c| key.iter().position(|&x| x == c).unwrap() as u8)
        .collect()
}
/// Breaks a substitution cipher using a simple hill climbing algorithm. It's not very accurate, but it's fast.
pub fn break_substitution_cipher(
    cipher_data: &[u8],
    tetragram_table: &TetragramsTable,
    rng: &mut FastRandomUsize,
    generations: Option<usize>,
) -> (Vec<u8>, Vec<u8>) {
    let mut best_key = utils::string_to_alphabet_index("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    let mut best_decryption = substitute(&cipher_data, &best_key);
    let mut best_evaluation = ngram_evaluation::evaluate_ngrams(&best_decryption, tetragram_table);

    for _ in 0..generations.unwrap_or(10000) {
        // Swap two random elements in the key.
        let index1 = rng.next();
        let index2 = rng.next();
        best_key.swap(index1, index2);

        // Evaluate the new decryption.
        let new_decryption = substitute(&cipher_data, &best_key);
        let new_evaluation = ngram_evaluation::evaluate_ngrams(&new_decryption, tetragram_table);

        if new_evaluation > best_evaluation {
            // If the new decryption is better, keep the swaps.
            best_decryption = new_decryption;
            best_evaluation = new_evaluation;
        } else {
            // Otherwise, swap the elements back.
            best_key.swap(index1, index2);
        }
    }
    (best_decryption, reverse_key(&best_key))
}
