import json
import random
import string
import sys

from ngram.ngram import n_gram_fitness, process_text


def vigenere_decrypt(ciphertext: str, key: str, variant: str = "") -> str:
    """
    Decrypts a given Vigenere-variant enciphered text with the provided key.

    Args:
        ciphertext (str): The encrypted text to be decrypted.
        key (str): The key used for decryption.
        variant (str): The variant of the Vigenere cipher used for encryption. Defaults to "", no variant. Can be "autokey" or "beaufort".

    Returns:
        str: The decrypted plaintext.
    """
    plaintext = []
    extended_key = key

    for i, char in enumerate(ciphertext):
        shift = string.ascii_lowercase.index(extended_key[i % len(extended_key)])
        if variant == "beaufort":
            original_index = (shift - string.ascii_lowercase.index(char)) % 26
        else:
            original_index = (string.ascii_lowercase.index(char) - shift) % 26
        decrypted_char = string.ascii_lowercase[original_index]
        plaintext.append(decrypted_char)
        if variant == "autokey":
            extended_key += decrypted_char

    return "".join(plaintext)


def vigenere_key_generate(key_length: int) -> str:
    """
    Generates a random key of a given length for the Vigenere cipher.

    Args:
        key_length (int): The length of the key to be generated.

    Returns:
        str: A randomly generated key of the specified length.
    """
    return "".join(random.choices(string.ascii_lowercase, k=key_length))


def vigenere_key_mutate(key: str) -> str:
    """
    Mutates a given key by randomly changing one of its characters to a random lowercase letter.

    Args:
            key (str): The original key to be mutated.

    Returns:
            str: The mutated key with one character randomly changed.
    """
    new_key = list(key)
    new_key[random.randint(0, len(new_key) - 1)] = random.choice(string.ascii_lowercase)
    return "".join(new_key)
