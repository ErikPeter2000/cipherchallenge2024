package main.breakers.monoalphabetic;

import main.breakers.CipherBreakerOutput;
import main.ciphers.monoalphabetic.MonoAlphabeticCipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;
import main.utils.TextUtilities;
import main.utils.maths.Permutations;
import main.utils.maths.Random;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that contains methods to break MonoAlphabeticCipher
 */
public class MonoAlphabeticCipherBreaker {

    /**
     * Initial fitness value set to the lowest possible value
     */
    private static final int INITIAL_FITNESS = -2147483648;

    /**
     * Swaps two random digits in the key
     *
     * @param key key to swap digits in
     * @return key with two random digits swapped
     */
    public static byte[] swapRandomInKey(byte[] key) {
        return Permutations.swapTwoRandomDigits(key);
    }

    /**
     * Generates a random key
     *
     * @return random key
     */
    public static byte[] generateRandomKey() {
        byte[] randomKey = TextUtilities.convertToByteArray(Constants.alphabet, Constants.alphabet);
        for (int i = 0; i < 1000; i++) {
            randomKey = swapRandomInKey(randomKey);
        }
        return randomKey;
    }

    /**
     * Performs a hill climbing attack on a cipher text with default parameters
     *
     * @param cipherText cipher text to break
     * @return output of the attack
     */
    public static CipherBreakerOutput<byte[]> evolutionaryHillClimbingAttack(byte[] cipherText) {
        return evolutionaryHillClimbingAttack(cipherText, 400, 400);
    }

    /**
     * Performs a hill climbing attack on a cipher text
     *
     * @param cipherText cipher text to break
     * @param genLimit   number of generations to run the attack for
     * @param keysPerGen number of keys to generate per generation
     * @return output of the attack
     */
    public static CipherBreakerOutput<byte[]> evolutionaryHillClimbingAttack(byte[] cipherText, int genLimit, int keysPerGen) {
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("MonoAlphabeticCipher", cipherText);
        byte[] bestKey = null;
        output.fitness = INITIAL_FITNESS;
        byte[][] generation = new byte[keysPerGen][];
        for (int i = 0; i < keysPerGen / 10; i++) {
            generation[i] = generateRandomKey();
        }
        for (int i = 0; i < genLimit; i++) {
            for (int j = keysPerGen / 10; j < keysPerGen; j++) {
                generation[j] = swapRandomInKey(generation[j / 10 - 1]);
            }
            double[] topFitness = new double[keysPerGen / 10];
            byte[][] topKeys = new byte[keysPerGen / 10][];
            Arrays.fill(topFitness, INITIAL_FITNESS);
            int lowestIndex = 0;
            for (int j = 0; j < keysPerGen; j++) {
                byte[] text = MonoAlphabeticCipher.decipher(cipherText, generation[j]);
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if (newFitness > output.fitness) {
                    output.fitness = newFitness;
                    bestKey = Arrays.copyOf(generation[j], generation[j].length);
                    output.plainText = text;
                }
                if (newFitness > topFitness[lowestIndex]) {
                    topFitness[lowestIndex] = newFitness;
                    topKeys[lowestIndex] = Arrays.copyOf(generation[j], generation[j].length);
                    double lowestFitness = 9999999;
                    for (int h = 0; h < topFitness.length; h++) {
                        if (topFitness[h] == INITIAL_FITNESS) {
                            lowestIndex = h;
                            break;
                        }
                        if (topFitness[h] < lowestFitness) {
                            lowestIndex = h;
                            lowestFitness = topFitness[h];
                        }
                    }
                }
            }
            System.arraycopy(topKeys, 0, generation, 0, topFitness.length);
        }

        output.isSuccessful = (output.plainText != null);
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }

    /**
     * Performs a hill climbing attack on a cipher text using monogram fitness
     *
     * @param cipherText cipher text to break
     * @param genLimit   number of generations to run the attack for
     * @param keysPerGen number of keys to generate per generation
     * @return output of the attack
     */
    public static CipherBreakerOutput<byte[]> evolutionaryHillClimbingAttackMF(byte[] cipherText, int genLimit, int keysPerGen) {
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("MonoAlphabeticCipher", cipherText);
        byte[] bestKey = null;
        output.fitness = INITIAL_FITNESS;
        byte[][] generation = new byte[keysPerGen][];
        for (int i = 0; i < keysPerGen / 10; i++) {
            generation[i] = generateRandomKey();
        }
        for (int i = 0; i < genLimit; i++) {
            for (int j = keysPerGen / 10; j < keysPerGen; j++) {
                generation[j] = swapRandomInKey(generation[j / 10 - 1]);
            }
            double[] topFitness = new double[keysPerGen / 10];
            byte[][] topKeys = new byte[keysPerGen / 10][];
            Arrays.fill(topFitness, INITIAL_FITNESS);
            int lowestIndex = 0;
            for (int j = 0; j < keysPerGen; j++) {
                byte[] text = MonoAlphabeticCipher.decipher(cipherText, generation[j]);
                double newFitness = FitnessCalculator.MonogramABVFitness(text);
                if (newFitness > output.fitness) {
                    output.fitness = newFitness;
                    bestKey = Arrays.copyOf(generation[j], generation[j].length);
                    output.plainText = text;
                }
                if (newFitness > topFitness[lowestIndex]) {
                    topFitness[lowestIndex] = newFitness;
                    topKeys[lowestIndex] = Arrays.copyOf(generation[j], generation[j].length);
                    double lowestFitness = 9999999;
                    for (int h = 0; h < topFitness.length; h++) {
                        if (topFitness[h] == INITIAL_FITNESS) {
                            lowestIndex = h;
                            break;
                        }
                        if (topFitness[h] < lowestFitness) {
                            lowestIndex = h;
                            lowestFitness = topFitness[h];
                        }
                    }
                }
            }
            System.arraycopy(topKeys, 0, generation, 0, topFitness.length);
        }

        output.isSuccessful = (output.plainText != null);
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }
}
