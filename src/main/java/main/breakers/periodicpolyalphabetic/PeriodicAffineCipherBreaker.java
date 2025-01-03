package main.breakers.periodicpolyalphabetic;

import main.breakers.CipherBreakerOutput;
import main.breakers.monoalphabetic.AffineCipherBreaker;
import main.ciphers.periodicpolyalphabetic.PeriodicAffineCipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;
import main.utils.periodanalysers.IOCPeriodAnalyser;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class to break periodic affine cipher.
 */
public class PeriodicAffineCipherBreaker {
    /**
     * Method to break periodic affine cipher using monogram frequency attack.
     *
     * @param cipherText cipher text to break.
     * @param period     period of the cipher.
     * @return output of the breaker.
     */
    public static CipherBreakerOutput<int[][]> monogramFreqAttack(byte[] cipherText, int period) {
        CipherBreakerOutput<int[][]> output = new CipherBreakerOutput<>("PeriodicAffineCipher", cipherText);

        int[][] keys = new int[period][2];
        byte[][] slices = IOCPeriodAnalyser.splitText(cipherText, period);
        for (int i = 0; i < period; i++) {
            CipherBreakerOutput<Integer> cbo = AffineCipherBreaker.bruteforceMFA(slices[i]);
            keys[i][0] = cbo.key.get(0);
            keys[i][1] = cbo.key.get(1);
        }

        output.key = new ArrayList<>();
        output.key.add(keys);
        output.plainText = PeriodicAffineCipher.decipher(cipherText, keys);

        for (int i = 0; i < output.plainText.length; i++) {
            if (output.plainText[i] < 0) {
                System.out.println(output.plainText[i]);
            }
        }
        output.fitness = FitnessCalculator.MonogramABVFitness(output.plainText);
        output.isSuccessful = true;
        return output;
    }

    /**
     * Shifts the array by p.
     *
     * @param array array to shift.
     * @param p     shift value.
     */
    public static void shift(byte[] array, int p) {
        for (int i = 0; i < array.length; i++) {
            array[i]++;
            array[i] = (byte) (array[i] % p);
        }
    }

    /**
     * Checks if the word is in the wordlist.
     *
     * @param word word to check.
     * @return true if word is in the wordlist, false otherwise.
     */
    public static boolean isInWorlist(byte[] word) {
        for (int i = 0; i < Constants.wordlist.length; i++) {
            if (Constants.wordlist[i].length != word.length) {
                continue;
            }
            boolean found = true;
            for (int j = 0; j < word.length; j++) {
                if (Constants.wordlist[i][j] != word[j]) {
                    found = false;
                    break;
                }
            }
            if (found) return true;
        }
        return false;
    }

    /**
     * Extracts the vigenere keyword from the keys.
     *
     * @param keys keys to extract from.
     * @return extracted keyword.
     */
    public static byte[] extractVigenereKeyword(int[][] keys) {
        for (int i = 1; i < keys.length; i++) {
            if (keys[i][0] != keys[0][0]) throw new IllegalArgumentException("Multipliers are not the same.");
        }
        byte[] keyword = new byte[keys.length];
        for (int i = 0; i < keys.length; i++) {
            keyword[i] = (byte) (keys[i][1]);
        }
        if (isInWorlist(keyword)) {
            return keyword;
        }
        double bestFitness = FitnessCalculator.TetragramFitness(keyword);
        byte[] bestKeyword = Arrays.copyOf(keyword, keyword.length);
        for (int i = 0; i < 25; i++) {
            shift(keyword, 26);
            double newFitness = FitnessCalculator.TetragramFitness(keyword);
            if (newFitness > bestFitness) {
                bestFitness = newFitness;
                bestKeyword = Arrays.copyOf(keyword, keyword.length);
            }
            if (isInWorlist(keyword)) {
                return keyword;
            }
        }
        return bestKeyword;
    }
}
