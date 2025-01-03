package main.breakers.transposition;

import main.breakers.CipherBreakerOutput;
import main.ciphers.transposition.PermutationCipher;
import main.utils.FitnessCalculator;
import main.utils.maths.Permutations;
import main.utils.maths.Random;

import java.util.ArrayList;

/**
 * A class that contains methods to break the Permutation Cipher.
 */
public class PermutationCipherBreaker {
    /**
     * A method that uses brute force to break the Permutation Cipher.
     *
     * @param cipherText          The cipher text to be broken.
     * @param maxBlockSize        The maximum block size to be considered.
     * @param checkDivisibility    A boolean that indicates whether the block size should be divisible by the length of the cipher text.
     * @return                    A CipherBreakerOutput object that contains the key, plain text and fitness of the deciphered text.
     */
    public static CipherBreakerOutput<byte[]> bruteforce(byte[] cipherText, int maxBlockSize, boolean checkDivisibility) {
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("PermutationCipher", cipherText);
        byte[] bestKey = null;
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        for (int i = 2; i <= maxBlockSize; i++) {
            if (cipherText.length % i != 0 && checkDivisibility) continue;
            byte[][] possiblePermutations = Permutations.generateAllPossiblePermutations(i);
            for (byte[] permutation : possiblePermutations) {
                byte[] text = PermutationCipher.decipher(cipherText, permutation);
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if (newFitness > output.fitness) {
                    output.fitness = newFitness;
                    output.plainText = text;
                    bestKey = permutation;
                }
            }
        }
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }

    /**
     * Performs a hill climbing attack on the Permutation Cipher.
     * @param cipherText The cipher text to be broken.
     * @param blockLength The block length of the cipher.
     * @param marginOfError The margin of error to be considered.
     * @param probabilityOfDescent The probability of descending to a lower fitness.
     * @return A CipherBreakerOutput object that contains the key, plain text and fitness of the deciphered text.
     */
    public static CipherBreakerOutput<byte[]> hillClimberAttack(byte[] cipherText, int blockLength, double marginOfError, double probabilityOfDescent) {
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("PermutationCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        byte[] bestKey;
        byte[] parentKey = Permutations.getRandomPermutation(blockLength);
        int counter = 0;
        while (counter < 100 * blockLength) {
            byte[] childKey;
            if (Random.random.nextBoolean()) {
                childKey = Permutations.swapTwoRandomDigits(parentKey);
            } else {
                childKey = Permutations.rollPermutationRandomly(parentKey);
            }
            byte[] text = PermutationCipher.decipher(cipherText, childKey);
            double newFitness = FitnessCalculator.TetragramFitness(text);
            if (newFitness > output.fitness || (newFitness > output.fitness - marginOfError && Random.random.nextDouble() < probabilityOfDescent)) {
                parentKey = childKey;
                output.fitness = newFitness;
                output.plainText = text;
                counter = 0;
            }
            counter++;
        }
        bestKey = parentKey;
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }

    /**
     * Performs a hill climbing attack on the Permutation Cipher.
     * @param cipherText The cipher text to be broken.
     * @param blockLength The block length of the cipher.
     * @return A CipherBreakerOutput object that contains the key, plain text and fitness of the deciphered text.
     */
    public static CipherBreakerOutput<byte[]> hillClimberAttack(byte[] cipherText, int blockLength) {
        return hillClimberAttack(cipherText, blockLength, 0.15, 0.05);
    }

    /**
     * A method that uses brute force to break the Permutation Cipher by trying all possible block sizes.
     *
     * @param cipherText          The cipher text to be broken.
     * @param maxBlockSize        The maximum block size to be considered.
     * @return                    A CipherBreakerOutput object that contains the key, plain text and fitness of the deciphered text.
     */
    public static CipherBreakerOutput<byte[]> bruteforceBlockSizeUsingHillClimb(byte[] cipherText, int maxBlockSize, double marginOfError, double probabilityOfDescent) {
        CipherBreakerOutput<byte[]> bestCBO = hillClimberAttack(cipherText, 2, marginOfError, probabilityOfDescent);
        for (int i = 3; i <= maxBlockSize; i++) {
            CipherBreakerOutput<byte[]> CBO = hillClimberAttack(cipherText, i, marginOfError, probabilityOfDescent);
            if (CBO.fitness > bestCBO.fitness) {
                bestCBO = CBO;
            }
        }
        return bestCBO;
    }

    /**
     * A method that uses brute force to break the Permutation Cipher by trying all possible block sizes.
     *
     * @param cipherText          The cipher text to be broken.
     * @param maxBlockSize        The maximum block size to be considered.
     * @return                    A CipherBreakerOutput object that contains the key, plain text and fitness of the deciphered text.
     */
    public static CipherBreakerOutput<byte[]> bruteforceBlockSizeUsingHillClimb(byte[] cipherText, int maxBlockSize) {
        return bruteforceBlockSizeUsingHillClimb(cipherText, maxBlockSize, 0.15, 0.05);
    }
}
