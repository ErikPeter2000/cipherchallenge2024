#!/usr/bin/env python3

import json
import math
import string
from collections import Counter

N = 4


def process_text(filename: str) -> list[str]:
    """
    Process the input text file, removing punctuation and converting all words to lowercase, and return a list of words.

    Args:
        filename (str): The name of the input text file.

    Returns:
        list[str]: The list of words in the input text file.
    """
    with open(filename, "r") as file:
        return [word.strip(string.punctuation).lower() for word in file.read().split() if word.strip(string.punctuation).isalpha()]


def n_gram_log_frequency(text: str, n: int = N) -> dict:
    """
    Generate a normalised frequency table of n-grams by analysing the input text.

    Args:
        text (str): The input text to analyse.
        n (int, optional): The length of the n-grams. Defaults to N.

    Returns:
        dict: The normalised frequency table of n-grams.
    """
    n_gram_count: Counter = Counter(text[i : i + n] for i in range(len(text) - n + 1))
    # Normalise using logs
    return Counter({n_gram: math.log(count / (len(text) - n + 1)) for n_gram, count in n_gram_count.items()})


def n_gram_fitness(text: str, n_gram_log_freq: dict, n: int = N) -> float:
    """
    Calculate the fitness of the input text based on the n-gram frequency table.

    Args:
        text (str): The input text to calculate the fitness of.
        n_gram_log_freq (dict): The normalised frequency table of n-grams.
        n (int, optional): The length of the n-grams. Defaults to N.

    Returns:
        float: The fitness of the input text.
    """
    return sum(n_gram_log_freq.get(text[i : i + n], -16) for i in range(len(text) - n + 1)) / (len(text) - n + 1)


if __name__ == "__main__":
    """
    If the script is run directly, process the input text file and generate the n-gram sequence frequency table.
    """
    words: list[str] = process_text("brown.txt")
    n_gram_sequence_freq: dict = n_gram_log_frequency("".join(words))
    with open("n_gram_sequence_freq.json", "w") as file:
        json.dump(n_gram_sequence_freq, file, indent=4)
