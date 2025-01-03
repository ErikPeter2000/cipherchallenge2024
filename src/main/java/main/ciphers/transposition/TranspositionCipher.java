package main.ciphers.transposition;

import main.utils.Analyser;
import main.utils.FitnessCalculator;

import java.util.Arrays;

/**
 * TranspositionCipher class is used to identify if the given cipher text is a transposition cipher or not.
 */
public class TranspositionCipher {
    /**
     * isLikely method is used to identify if the given
     * cipher text is a transposition cipher or not.
     * @param cipherText byte[] - cipher text to be identified.
     * @return boolean - true if the given
     * cipher text is likely a transposition cipher, false otherwise.
     */
    public static boolean isLikely(byte[] cipherText) {
        double ioc = Analyser.getIndexOfCoincedence(cipherText, true);
        double mFrequency = FitnessCalculator.MonogramABVFitness(cipherText);
        System.out.println("[Transposition cipher Identifier] IoC: " + ioc);
        return mFrequency > 0.9;
    }

    /**
     * appendToPlaintext method is used to append a padding to the given plain text.
     * @param plainText byte[] - plain text to be padded.
     * @param blockSize int - block size to be used for padding.
     * @return byte[] - padded plain text.
     */
    public static byte[] appendToPlaintext(byte[] plainText, int blockSize) {
        byte[] newText = new byte[(int) (Math.ceil((double) plainText.length / blockSize) * blockSize)];
        Arrays.fill(newText, (byte) 23);
        System.arraycopy(plainText, 0, newText, 0, plainText.length);
        return newText;
    }
}
