#!/usr/bin/env python3

import json
import sys

from ciphers.transposition import transposition_decrypt, transposition_key_generate, transposition_key_lengths, transposition_key_mutate
from ciphers.vigenere import vigenere_decrypt, vigenere_key_generate, vigenere_key_mutate
from ngram.ngram import n_gram_fitness

ciphers = {
    "transposition": {
        "decrypt": transposition_decrypt,
        "key_generate": transposition_key_generate,
        "key_mutate": transposition_key_mutate,
        "key_lengths": transposition_key_lengths,
    },
    "vigenere": {
        "decrypt": vigenere_decrypt,
        "key_generate": vigenere_key_generate,
        "key_mutate": vigenere_key_mutate,
        "key_lengths": lambda _: range(1, 16),
    },
    "autokey": {
        "decrypt": lambda ciphertext, key: vigenere_decrypt(ciphertext, key, variant="autokey"),
        "key_mutate": vigenere_key_mutate,
        "key_generate": vigenere_key_generate,
        "key_lengths": lambda _: range(1, 16),
    },
    "beaufort": {
        "decrypt": lambda ciphertext, key: vigenere_decrypt(ciphertext, key, variant="beaufort"),
        "key_generate": vigenere_key_generate,
        "key_mutate": vigenere_key_mutate,
        "key_lengths": lambda _: range(1, 16),
    },
}


def hill_climb_decrypt(ciphertext: str, type: str, n_gram_log_freq: dict) -> tuple[str, str, float]:
    """
    Performs a stochastic hill climbing algorithm to find the best key and plaintext for the given cipher

    Args:
        ciphertext (str): The encrypted text to be decrypted.
        n_gram_log_freq (dict): A dictionary containing n-gram normalized frequencies used to evaluate the fitness of the plaintext.

    Returns:
        tuple[str, str, float]: A tuple containing the best plaintext found, the corresponding key, and the fitness score of the plaintext.
    """
    decrypt_func = ciphers[type]["decrypt"]
    key_generate_func = ciphers[type]["key_generate"]
    key_mutate_func = ciphers[type]["key_mutate"]
    key_lengths = ciphers[type]["key_lengths"](len(ciphertext))

    best_plaintext = ""
    best_key = ""
    best_fitness = float("-inf")

    def run_hill_climb(ciphertext, key, decrypt_func, key_mutate_func):
        fitness = n_gram_fitness(decrypt_func(ciphertext, key), n_gram_log_freq)
        stagnation_counter = 0

        while stagnation_counter < 10000:  # Stops after 10000 iterations without improvement
            new_key = key_mutate_func(key)
            new_plaintext = decrypt_func(ciphertext, new_key)
            new_fitness = n_gram_fitness(new_plaintext, n_gram_log_freq)

            if new_fitness > fitness:
                key, fitness = new_key, new_fitness
                stagnation_counter = 0
            else:
                stagnation_counter += 1

        return decrypt_func(ciphertext, key), key, fitness

    for key_length in key_lengths:
        print(f"Trying key length {key_length}...")
        key = key_generate_func(key_length)
        plaintext, key, fitness = run_hill_climb(ciphertext, key, decrypt_func, key_mutate_func)

        if fitness > best_fitness:
            best_plaintext, best_key, best_fitness = plaintext, key, fitness
        if best_fitness > -10:
            # It's probably english
            print("Done!")
            break

    return best_plaintext, best_key, best_fitness


if __name__ == "__main__":
    # Load n-gram sequence frequency table from json, generated using ngram/ngram.py
    with open("ngram/n_gram_sequence_freq.json", "r") as file:
        n_gram_sequence_freq: dict = json.load(file)

    if len(sys.argv) != 2:
        print("Usage: hillclimb <input_json_file>")
        sys.exit(1)

    input_json_file = sys.argv[1]

    with open(input_json_file, "r") as file:
        data = json.load(file)
        ciphertext, type = data["ciphertext"], data["type"]

    # Perform hill climbing decryption
    plaintext, key, fitness = hill_climb_decrypt(ciphertext, type, n_gram_sequence_freq)

    # Save the result as JSON
    result = {"ciphertext": ciphertext, "type": type, "plaintext": plaintext, "key": key, "fitness": fitness}
    with open(input_json_file, "w") as file:
        json.dump(result, file, indent=4)
