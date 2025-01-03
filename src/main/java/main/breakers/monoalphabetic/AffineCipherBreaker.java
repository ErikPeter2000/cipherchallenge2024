package main.breakers.monoalphabetic;

import main.breakers.CipherBreakerOutput;
import main.ciphers.monoalphabetic.AffineCipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;

import java.util.ArrayList;

/**
 * Class to break Affine Cipher
 */
public class AffineCipherBreaker {
    /**
     * Perform brute force attack on Affine Cipher using Tetragram Fitness
     *
     * @param cipherText cipher text
     * @return output of the attack
     */
    public static CipherBreakerOutput<Integer> bruteforceTF(byte[] cipherText) {
        CipherBreakerOutput<Integer> output = new CipherBreakerOutput<>("AffineCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        int[] keys = new int[2];

        for (int a = 0; a < Constants.monogramCount; a++) {
            if (AffineCipher.isKeyInvalid(a)) continue;
            for (int b = 0; b < Constants.monogramCount; b++) {
                byte[] text = AffineCipher.decipher(cipherText, a, b);
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if (newFitness > output.fitness) {
                    output.fitness = newFitness;
                    keys[0] = a;
                    keys[1] = b;
                    output.plainText = text;
                }
            }
        }
        output.isSuccessful = (output.plainText != null);
        output.key = new ArrayList<>();
        output.key.add(keys[0]);
        output.key.add(keys[1]);
        return output;
    }

    //TODO: Attack with cribs

    /**
     * Perform brute force attack on Affine Cipher using Monogram ABV Fitness
     *
     * @param cipherText cipher text
     * @return output of the attack
     */
    public static CipherBreakerOutput<Integer> bruteforceMFA(byte[] cipherText) {
        CipherBreakerOutput<Integer> output = new CipherBreakerOutput<>("AffineCipher", cipherText);
        output.fitness = FitnessCalculator.MonogramABVFitness(cipherText);
        int[] keys = new int[2];

        for (int a = 0; a < Constants.monogramCount; a++) {
            if (AffineCipher.isKeyInvalid(a)) continue;
            for (int b = 0; b < Constants.monogramCount; b++) {
                byte[] text = AffineCipher.decipher(cipherText, a, b);
                double newFitness = FitnessCalculator.MonogramABVFitness(text);
                if (newFitness > output.fitness) {
                    output.fitness = newFitness;
                    keys[0] = a;
                    keys[1] = b;
                    output.plainText = text;
                }
            }
        }
        output.isSuccessful = (output.plainText != null);
        output.key = new ArrayList<>();
        output.key.add(keys[0]);
        output.key.add(keys[1]);
        return output;
    }
}
