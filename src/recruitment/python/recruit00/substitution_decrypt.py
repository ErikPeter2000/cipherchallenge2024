# This code has been modified from the initial submission to work

import math
import os
import random
import string
from collections import Counter
from pathlib import Path

# Positives
# Good use of functions. They describe to some extent what is going on.
# Good use of Python libraries to do the work.
# Variable names are sufficiently descriptive in most places, (except "n")
# Casing is good, as it sticks to normal snake-case convention.
# Good use of pure functions, allowing for maintainability.
# Type annotations are good.
# The code works deciphers correctly (although the key is wrong)

# Negatives
# Comments should be use to explain "how" behaviour is achieved. Although member names are descriptive enough, the long list comprehensions are slightly ambiguous.
# Would be nice to use a main entry "if __name__ == "__main__":, although this is minor.
# Some feedback to the user would be beneficial.
# Does the program always terminate?
# Inputs should always ask for prompts

# Questions
# Why did you decide to normalise your fitness?
# What happens if you get an n-gram that is not in the table? How does this affect the fitness? What implications does this have for your source n-grams files?
# How would you optimise your code?
# How did you test your code? (reverse key?)

def process_text(filename: str) -> list[str]:
	with open(filename, "r") as file:
		return [word.strip(string.punctuation).lower() for word in file.read().split() if word.strip(string.punctuation).isalpha()]

def n_gram_log_frequency(text: str, n: int) -> Counter:
	n_gram_count: Counter = Counter(text[i:i+n] for i in range(len(text) - n + 1)) 
	return Counter({n_gram: math.log(count / (len(text) - n + 1)) for n_gram, count in n_gram_count.items()})

def n_gram_fitness(text: str, n_gram_log_freq: Counter, n: int) -> float:
	return sum(n_gram_log_freq.get(text[i:i+n], -16) for i in range(len(text) - n + 1)) / (len(text) - n + 1)

def substitution_decrypt(ciphertext: str, key: str) -> str:
    #* This is a good use of translate.
	return ciphertext.translate(str.maketrans(string.ascii_lowercase, key))

def key_swap(key: str, i: int, j: int) -> str:
    # This could be done ever so slightly better, but is sufficient and clear
    key_list: list[str] = list(key)
    key_list[i], key_list[j] = key_list[j], key_list[i]
    return "".join(key_list)

def hill_climb_decrypt(ciphertext: str, n_gram_log_freq: Counter, n: int, max_iterations: int = 10000) -> tuple[str, str, float]:
    key: str = string.ascii_lowercase
    best_plaintext: str = substitution_decrypt(ciphertext, key)
    best_fitness: float = n_gram_fitness(best_plaintext, n_gram_log_freq, n)
    
    counter: int = 0
    while counter < max_iterations:
        i, j = random.sample(range(26), 2)
        new_key: str = key_swap(key, i, j)
        new_plaintext: str = substitution_decrypt(ciphertext, new_key)
        new_fitness: str = n_gram_fitness(new_plaintext, n_gram_log_freq, n)
        
        if new_fitness > best_fitness:
            best_plaintext = new_plaintext
            best_fitness = new_fitness
            key = new_key
            counter = 0
        else:
            counter += 1 
            
    return best_plaintext, key, best_fitness


n = 4

file_dir = Path(os.path.dirname(__file__))
orwell_1984_path = (file_dir / Path("../../../../resources/text/Orwell1984.txt")).resolve()

words: list[str] = process_text(str(orwell_1984_path))
n_gram_sequence_freq: Counter = n_gram_log_frequency("".join(words), n)

ciphertext: str = input().lower()

best_plaintext, best_key, best_fitness = hill_climb_decrypt(ciphertext, n_gram_sequence_freq, n)
print(f"Plaintext: {best_plaintext}")
print(f"Key: {best_key}")
print(f"Fitness: {best_fitness}")
