package main.breakers.periodicpolyalphabetic;

import main.breakers.CipherBreakerOutput;
import main.ciphers.periodicpolyalphabetic.Quagmire2Cipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class that provides a method to break a Quagmire2 cipher using a dictionary attack.
 */
public class Quagmire2CipherBreaker {
    /**
     * Breaks a Quagmire2 cipher using a dictionary attack.
     *
     * @param cipherText       the cipher text to break
     * @param alphabetKeyLength the length of the alphabet key
     * @param shiftsKeyLength   the length of the shifts key
     * @return a {@link CipherBreakerOutput} object containing the plain text, key, fitness, and success status
     */
    public static CipherBreakerOutput<byte[]> dictionaryAttack(byte[] cipherText, int alphabetKeyLength, int shiftsKeyLength) {
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("Quagmire2Cipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        byte[] bestAKey = null;
        byte[] bestSKey = null;
        byte[][] alphabetKeyWordlist = Constants.smallWordlistSplit[alphabetKeyLength];
        byte[][] shiftsKeyWordlist = Constants.smallWordlistSplit[shiftsKeyLength];
        int n = 0;
        for (byte[] alphabetKey : alphabetKeyWordlist) {
            for (byte[] shiftsKey : shiftsKeyWordlist) {
                byte[] text = Quagmire2Cipher.decipher(cipherText, alphabetKey, shiftsKey);
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if (newFitness > output.fitness) {
                    output.fitness = newFitness;
                    bestAKey = Arrays.copyOf(alphabetKey, alphabetKey.length);
                    bestSKey = Arrays.copyOf(shiftsKey, shiftsKey.length);
                    output.plainText = text;
                }
            }
            if (n % 100 == 0) System.out.println(n * 100. / alphabetKeyWordlist.length + "% done.");
            n++;
        }
        output.isSuccessful = (output.plainText != null);
        output.key = new ArrayList<>();
        output.key.add(bestAKey);
        output.key.add(bestSKey);
        return output;
    }
}
