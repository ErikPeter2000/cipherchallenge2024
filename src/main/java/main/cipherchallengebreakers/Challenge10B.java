package main.cipherchallengebreakers;

import main.breakers.CipherBreakerOutput;
import main.breakers.monoalphabetic.MonoAlphabeticCipherBreaker;
import main.ciphers.transposition.PermutationCipher;
import main.utils.Constants;
import main.utils.TextUtilities;
import main.utils.maths.Permutations;

import java.util.Arrays;

public class Challenge10B {
    final static String cipherTextAlphabet = "\\|/";

    final static byte minPermutationLength = 2; //inclusive
    final static byte maxPermutationLength = 8; //exclusive

    final static byte minDistinctBlockLength = 4; //inclusive
    final static byte maxDistinctBlockLength = 12; //exclusive

    final static byte minDistinctBlocksAllowed = 23; //inclusive
    final static byte maxDistinctBlocksAllowed = 28; //exclusive

    final static byte permutationOutputLimit = 5;


    // Main function for deciphering the ciphertext from challenge 10b
    public static String decipher(String rawCipherText) {
        // Filter the input ciphertext and convert it to a byte array
        byte[] cipherText = TextUtilities.filterAndConvertToBytes(rawCipherText, cipherTextAlphabet);
        //? The "filterText" function should accept a char array of filter characters, not a string. This is confusing as it seems like you are removing the whole string "\\|/" from the input string.

        // Brute force all possible permutations of length 2-7 of the ciphertext and return permutations which have 23-27 unique blocks of length 4-11
        FoundPermutation[] foundPermutations = bruteForcePermutations(cipherText);
        //? bruteForcePermutations does way too much. You should split this function into smaller functions that each do one thing. This makes it much more readable, as "bruteForcePermutations" does not really summarise what the function does. A function name should be a verb that describes what the function does.
        //? The use of bytes is also confusing. Ints are more typical in normal use, and CPUs are slightly more optimised to use them, so you should explain why you chose bytes.

        // Print the found permutations
        for (FoundPermutation foundPermutation : foundPermutations) {
            byte[] key = foundPermutation.key();
            byte[][] lengths = foundPermutation.lengths();
            System.out.println("Permutation Key: " + Arrays.toString(key));
            for (byte[] length : lengths) {
                System.out.println("\tLength: " + length[0] + " Unique Blocks: " + length[1]);
            }
        }

        // After manually inspecting the output, we can see that the first permutation is the correct one. Choose the first permutation and convert the ciphertext to a substitution cipher
        byte[] candidateKey = foundPermutations[0].key();
        byte candidateLength = foundPermutations[0].lengths()[0][0];
        // Decipher the ciphertext with the candidate key
        byte[] candidateCipherText = PermutationCipher.decipher(cipherText, candidateKey);
        // Get all distinct blocks of the candidate ciphertext
        byte[][] distinctBlocks = getDistinctBlocks(candidateCipherText, candidateLength);
        // Convert the candidate ciphertext to a substitution cipher
        byte[] substitutionCipherText = convertToSubstitutionCipherText(candidateCipherText, distinctBlocks, candidateLength);

        // Perform an evolutionary hill climbing attack on the substitution cipher
        CipherBreakerOutput<byte[]> substitutionCipherBreakerOutput = MonoAlphabeticCipherBreaker.evolutionaryHillClimbingAttack(substitutionCipherText);
        // Convert the plaintext to a string and return it
        return TextUtilities.convertToString(substitutionCipherBreakerOutput.plainText, Constants.alphabet);
    }

    // Function to convert a ciphertext to a substitution cipher given distinct blocks and block length
    static byte[] convertToSubstitutionCipherText(byte[] cipherText, byte[][] distinctBlocks, byte blockLength) {
        //? This would also be considerably clearer if you used a list, or performed a map operation on the array.
        byte[] substitutionCipherText = new byte[cipherText.length / blockLength];
        for (int i = 0; i < cipherText.length; i += blockLength) {
            for (int j = 0; j < distinctBlocks.length; j++) {
                if (Arrays.equals(Arrays.copyOfRange(cipherText, i, i + blockLength), distinctBlocks[j])) {
                    substitutionCipherText[i / blockLength] = (byte) (j);
                    break;
                }
            }
        }
        return substitutionCipherText;
    }

    // Function to brute force all possible permutations of length 2-7 of a ciphertext and return permutations which have 23-27 unique blocks of length 4-11
    //? It would help if you used docstrings instead. This means the function description is a tooltip when you hover over the function name, and individual parameter descriptions are shown when you hover over the parameter name. You should also consider named and optional parameters.
    static FoundPermutation[] bruteForcePermutations(byte[] cipherText) {
        FoundPermutation[] foundPermutations = new FoundPermutation[Challenge10B.permutationOutputLimit];
        byte nextFreePointer = 0;
        boolean isOutputLimitReached = false;
        for (int keyLength = Challenge10B.minPermutationLength; keyLength < Challenge10B.maxPermutationLength && !isOutputLimitReached; keyLength++) {
            if (cipherText.length % keyLength != 0)
                continue; // Skipping key lengths which do not divide the ciphertext length, otherwise the permutation cipher deciphering algorithm will not work correctly
            byte[][] permutations = Permutations.generateAllPossiblePermutations(keyLength);
            for (byte[] permutation : permutations) {
                byte[][] testResult = testAllLengthsOfDistinctBlocks(PermutationCipher.decipher(cipherText, permutation));
                if (testResult.length > 0) {
                    //? A list would be more appropriate.
                    foundPermutations[nextFreePointer] = new FoundPermutation(permutation, testResult);
                    nextFreePointer++;
                    if (nextFreePointer == Challenge10B.permutationOutputLimit) {
                        isOutputLimitReached = true;
                        break;
                    }
                }
            }
        }
        return Arrays.copyOf(foundPermutations, nextFreePointer);
    }

    // Function to test all possible lengths of a ciphertext and return lengths which have 23-27 unique blocks of length 4-11
    static byte[][] testAllLengthsOfDistinctBlocks(byte[] cipherText) {
        //? Again, why not a dynamic collection? I'm not certain, but if you are copying the array at the end, the performance benefit over a list may not be worth it.
        byte[][] possibleLengths = new byte[Challenge10B.maxDistinctBlockLength - Challenge10B.minDistinctBlockLength][];
        byte nextFreePointer = 0;
        for (int i = Challenge10B.minDistinctBlockLength; i < Challenge10B.maxDistinctBlockLength; i++) {
            byte[] testResult = testAmountOfUniqueBlocksOfLength(cipherText, (byte) i);
            if (testResult[0] == 1) {
                possibleLengths[nextFreePointer] = new byte[]{(byte) i, testResult[1]};
                nextFreePointer++;
            }
        }
        return Arrays.copyOf(possibleLengths, nextFreePointer);
    }

    // Function to test the amount of unique blocks of a certain length in a ciphertext
    static byte[] testAmountOfUniqueBlocksOfLength(byte[] cipherText, byte length) {
        byte[][] distinctBlocks = new byte[Challenge10B.maxDistinctBlocksAllowed][];
        byte pointer = 0;
        for (int i = 0; i < cipherText.length; i += length) {
            byte[] block = Arrays.copyOfRange(cipherText, i, i + length);
            if (arrayContains(block, distinctBlocks)) {
                distinctBlocks[pointer] = block;
                pointer++;
                if (pointer == Challenge10B.maxDistinctBlocksAllowed) return new byte[]{0, pointer};
            }
        }
        if (pointer < Challenge10B.minDistinctBlocksAllowed) return new byte[]{0, pointer};
        return new byte[]{1, pointer}; //[0 if testing failed, 1 if succeeded; amount of distinct blocks]
    }

    // Function to get all distinct blocks of a certain length in a ciphertext
    static byte[][] getDistinctBlocks(byte[] cipherText, byte length) {
        byte[][] distinctBlocks = new byte[cipherText.length / length][];
        byte pointer = 0;
        for (int i = 0; i < cipherText.length; i += length) {
            byte[] block = Arrays.copyOfRange(cipherText, i, i + length);
            if (arrayContains(block, distinctBlocks)) {
                distinctBlocks[pointer] = block;
                pointer++;
            }
        }
        return Arrays.copyOf(distinctBlocks, pointer);
    }

    // Function to check if a block exists in a list of distinct blocks
    static boolean arrayContains(byte[] element, byte[][] array) {
        for (byte[] element_ : array) {
            if (Arrays.equals(element, element_)) return false;
        }
        return true;
    }
}

// Class to store a permutation key and lengths of unique blocks
//? Why is lengths a jagged array? Maybe use two 1D arrays.
record FoundPermutation(byte[] key, byte[][] lengths) {
}
