package main.cipherchallengebreakers;

import main.breakers.CipherBreakerOutput;
import main.breakers.monoalphabetic.MonoAlphabeticCipherBreaker;
import main.ciphers.transposition.PermutationCipher;
import main.utils.Constants;
import main.utils.TextUtilities;
import main.utils.maths.Permutations;

import java.util.Arrays;

public class Challenge10B {
    // Main function for deciphering the ciphertext from challenge 10b
    public static String decipher(String input) {
        // Filter the input ciphertext and convert it to a byte array
        byte[] cipherText = TextUtilities.convertToByteArray(TextUtilities.filterText(input,"\\|/"), "\\|/");

        // Brute force all possible permutations of length 2-7 of the ciphertext and return permutations which have 23-27 unique blocks of length 4-11
        FoundPermutation[] foundPermutations = bruteForcePermutations(cipherText, (byte)2, (byte)8, (byte)5);
        // Print the found permutations
        for(FoundPermutation foundPermutation : foundPermutations){
            byte[] key = foundPermutation.key();
            byte[][] lengths = foundPermutation.lengths();
            System.out.println("Permutation Key: " + Arrays.toString(key));
            for(byte[] length : lengths){
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
        CipherBreakerOutput<byte[]> cbo = MonoAlphabeticCipherBreaker.evolutionaryHillClimbingAttack(substitutionCipherText, 400, 400);
        // Convert the plaintext to a string and return it
        return TextUtilities.convertToString(cbo.plainText, Constants.alphabet);
    }

    // Function to convert a ciphertext to a substitution cipher given distinct blocks and block length
    static byte[] convertToSubstitutionCipherText(byte[] cipherText, byte[][] distinctBlocks, byte blockLength){
        byte[] substitutionCipherText = new byte[cipherText.length/ blockLength];
        for(int i = 0; i < cipherText.length;i+=blockLength){
            for(int j = 0; j < distinctBlocks.length;j++){
                if(Arrays.equals(Arrays.copyOfRange(cipherText, i, i+blockLength), distinctBlocks[j])){
                    substitutionCipherText[i/blockLength] = (byte)(j);
                    break;
                }
            }
        }
        return substitutionCipherText;
    }

    // Function to brute force all possible permutations of length 2-7 of a ciphertext and return permutations which have 23-27 unique blocks of length 4-11
    static FoundPermutation[] bruteForcePermutations(byte[] cipherText, byte lowerBound, byte upperBound, byte outputLimit){
        FoundPermutation[] foundPermutations = new FoundPermutation[outputLimit];
        byte pointer = 0;
        for(int i = lowerBound; i < upperBound;i++){
            if(cipherText.length % i != 0)continue;
            byte[][] permutations = Permutations.generateAllPossiblePermutations(i);
            for(byte[] permutation : permutations){
                byte[][] testResult = testAllLengths(PermutationCipher.decipher(cipherText, permutation), (byte)4, (byte)12);
                if(testResult.length > 0){
                    foundPermutations[pointer] = new FoundPermutation(permutation, testResult);
                    pointer++;
                    if(pointer == outputLimit)return Arrays.copyOf(foundPermutations, pointer);
                }
            }
        }
        return Arrays.copyOf(foundPermutations, pointer);
    }

    // Function to test all possible lengths of a ciphertext and return lengths which have 23-27 unique blocks of length 4-11
    static byte[][] testAllLengths(byte[] cipherText, byte start, byte end){
        byte[][] possibleLengths = new byte[end - start][];
        byte pointer = 0;
        for(int i = start; i < end;i++){
            byte[] testResult = testAmountOfUniqueBlocksOfLength(cipherText, (byte)i, (byte)23, (byte)28);
            if(testResult[0] == 1) {
                possibleLengths[pointer] = new byte[]{(byte)i, testResult[1]};
                pointer++;
            }
        }
        return Arrays.copyOf(possibleLengths, pointer);
    }

    // Function to test the amount of unique blocks of a certain length in a ciphertext
    static byte[] testAmountOfUniqueBlocksOfLength(byte[] cipherText, byte length, byte lowerBound, byte upperBound){
        byte[][] distinctBlocks = new byte[upperBound][];
        byte pointer = 0;
        for(int i = 0; i < cipherText.length;i+=length){
            byte[] block = Arrays.copyOfRange(cipherText, i, i+length);
            if(isBlockUnique(block, distinctBlocks)){
                distinctBlocks[pointer] = block;
                pointer++;
                if(pointer == upperBound)return new byte[]{0, pointer};
            }
        }
        if(pointer < lowerBound)return new byte[]{0, pointer};
        return new byte[]{1, pointer};
    }

    // Function to get all distinct blocks of a certain length in a ciphertext
    static byte[][] getDistinctBlocks(byte[] cipherText, byte length){
        byte[][] distinctBlocks = new byte[cipherText.length/length][];
        byte pointer = 0;
        for(int i = 0; i < cipherText.length;i+=length){
            byte[] block = Arrays.copyOfRange(cipherText, i, i+length);
            if(isBlockUnique(block, distinctBlocks)){
                distinctBlocks[pointer] = block;
                pointer++;
            }
        }
        return Arrays.copyOf(distinctBlocks, pointer);
    }

    // Function to check if a block exists in a list of distinct blocks
    static boolean isBlockUnique(byte[] block, byte[][] distinctBlocks){
        for(byte[] distinctBlock : distinctBlocks){
            if(Arrays.equals(block, distinctBlock))return false;
        }
        return true;
    }
}

// Class to store a permutation key and lengths of unique blocks
record FoundPermutation(byte[] key, byte[][] lengths) {}
