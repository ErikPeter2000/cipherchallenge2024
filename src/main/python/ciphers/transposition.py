import json
import random
import string
import sys

from ngram.ngram import n_gram_fitness, process_text


def transposition_decrypt(ciphertext: str, key: str) -> str:
    """
    Decrypt a transposition cipher encipherd text with the provided key.

    Args:
        ciphertext (str): The encrypted text to be decrypted.
        key (str): The key used for decryption.

    Returns:
        str: The decrypted plaintext.
    """
    key = [int(i) for i in key]
    chunks = [ciphertext[i : i + len(key)] for i in range(0, len(ciphertext), len(key))]
    rearranged = ["".join(chunk[i] for i in key) for chunk in chunks]
    return "".join(rearranged)


def transposition_key_lengths(text_len: int) -> list[int]:
    """
    Get the possible key lengths for a transposition cipher.

    Returns:
        list[int]: A list of possible key lengths.
    """
    key_lengths = []
    for i in range(2, text_len + 1):
        if text_len % i == 0:
            key_lengths.append(i)
    return key_lengths


def transposition_key_generate(key_length: int) -> str:
    """
    Generate a random transposition key.

    Args:
            key_length (int): The length of the key.

    Returns:
            str: The generated key.
    """
    # return random arrangement of integers from 0 to key_length - 1
    return "".join(map(str, random.sample(list(range(key_length)), key_length)))


def transposition_key_mutate(key: str) -> str:
    """
    Mutate a transposition cipher key by swapping two random positions.

    Args:
        key (str): The current key as a string of integers (e.g., "0123").

    Returns:
        str: The mutated key.
    """
    key_list = list(key)
    idx1, idx2 = random.sample(range(len(key_list)), 2)  # Randomly pick two indices
    key_list[idx1], key_list[idx2] = key_list[idx2], key_list[idx1]  # Swap them
    return "".join(key_list)
