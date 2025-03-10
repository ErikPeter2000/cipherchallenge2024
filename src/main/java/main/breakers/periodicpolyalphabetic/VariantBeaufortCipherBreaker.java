package main.breakers.periodicpolyalphabetic;

import main.breakers.CipherBreakerOutput;
import main.ciphers.periodicpolyalphabetic.VariantBeaufortCipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class to break Variant Beaufort Cipher
 */
public class VariantBeaufortCipherBreaker {
    /**
     * Method to brute force Variant Beaufort Cipher
     *
     * @param cipherText The cipher text
     * @param maxLength  The maximum length of the key
     * @return The output of the brute force attack
     */
    public static CipherBreakerOutput<byte[]> bruteforce(byte[] cipherText, double maxLength) {
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("VariantBeaufortCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        byte[] bestKey = null;
        for (int n = 1; n <= maxLength; n++) {
            byte[][] possibleKeys = VigenereCipherBreaker.generateKeys(n);
            for (byte[] possibleKey : possibleKeys) {
                bestKey = updateBestKey(cipherText, output, bestKey, possibleKey);
            }
            //System.out.println("Key-length " + n + " finished. Best: " + output.plainText);
        }
        output.isSuccessful = (output.plainText != null);
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }

    /**
     * Method to update the best key
     *
     * @param cipherText  The cipher text
     * @param output      The output of the attack
     * @param bestKey     The best key
     * @param possibleKey The possible key
     * @return The updated best key
     */
    private static byte[] updateBestKey(byte[] cipherText, CipherBreakerOutput<byte[]> output, byte[] bestKey, byte[] possibleKey) {
        byte[] text = VariantBeaufortCipher.decipher(cipherText, possibleKey);
        double newFitness = FitnessCalculator.TetragramFitness(text);
        if (newFitness > output.fitness) {
            output.fitness = newFitness;
            bestKey = Arrays.copyOf(possibleKey, possibleKey.length);
            output.plainText = text;
        }
        return bestKey;
    }

    /**
     * Method to brute force Variant Beaufort Cipher with a wordlist
     *
     * @param cipherText The cipher text
     * @return The output of the brute force attack
     */
    public static CipherBreakerOutput<byte[]> bruteforceWithWordlist(byte[] cipherText) {
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("VariantBeaufortCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        byte[] bestKey = null;
        for (int n = 0; n < Constants.wordlist.length; n++) {
            byte[] key = Constants.wordlist[n];
            bestKey = updateBestKey(cipherText, output, bestKey, key);
        }
        output.isSuccessful = (output.plainText != null);
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }

    /**
     * Method to perform a hill climber attack on Variant Beaufort Cipher
     *
     * @param cipherText The cipher text
     * @param period     The period of the cipher
     * @return The output of the attack
     */
    public static CipherBreakerOutput<byte[]> hillClimberAttack(byte[] cipherText, int period) {
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("VariantBeaufortCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);

        byte[] bestKey;

        byte[] key = new byte[period];
        while (true) {
            double oldFitness = output.fitness;
            for (int i = 0; i < period; i++) {
                double maxFitness = -999999;
                byte bestLetter = key[i];
                for (int j = 0; j < Constants.monogramCount; j++) {
                    key[i] = (byte) (j);
                    byte[] text = VariantBeaufortCipher.decipher(cipherText, key);
                    double newFitness = FitnessCalculator.TetragramFitness(text);
                    if (newFitness > maxFitness) {
                        maxFitness = newFitness;
                        bestLetter = (byte) (j);
                    }
                }
                key[i] = bestLetter;
            }
            bestKey = Arrays.copyOf(key, key.length);
            output.plainText = VariantBeaufortCipher.decipher(cipherText, key);
            output.fitness = FitnessCalculator.TetragramFitness(output.plainText);
            if (output.fitness == oldFitness) {
                break;
            }
        }
        output.isSuccessful = true;
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }
}
