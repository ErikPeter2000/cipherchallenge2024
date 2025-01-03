package main.ciphers.periodicpolyalphabetic;

import main.ciphers.monoalphabetic.AffineCipher;
import main.utils.Constants;
import main.utils.maths.ModularArithmetics;

/**
 * Periodic Affine Cipher class for enciphering and deciphering text using periodic affine cipher
 */
public class PeriodicAffineCipher {
    /**
     * Checks if the keys are invalid
     *
     * @param keys keys to be checked
     * @return true if keys are invalid, false otherwise
     */
    public static boolean areKeysInvalid(int[][] keys) {
        for (int[] key : keys) {
            if (AffineCipher.isKeyInvalid(key[0])) return true;
        }
        return false;
    }

    /**
     * Enciphers the given plain text using the given keys
     *
     * @param plainText plain text to be enciphered
     * @param keys      keys to be used for enciphering
     * @return enciphered text
     */
    public static byte[] encipher(byte[] plainText, int[][] keys) {
        if (areKeysInvalid(keys)) {
            throw new IllegalArgumentException("Keys are not valid");
        }
        byte[] cipherText = new byte[plainText.length];
        for (int i = 0; i < plainText.length; i++) {
            int[] keypair = keys[i % keys.length];
            cipherText[i] = (byte) ((plainText[i] * keypair[0] + keypair[1]) % 26);
        }
        return cipherText;
    }

    /**
     * Deciphers the given cipher text using the given keys
     *
     * @param cipherText cipher text to be deciphered
     * @param keys       keys to be used for deciphering
     * @return deciphered text
     */
    public static byte[] decipher(byte[] cipherText, int[][] keys) {
        if (areKeysInvalid(keys)) {
            throw new IllegalArgumentException("Keys are not valid");
        }
        int[][] inverseAKeys = new int[keys.length][2];
        for (int i = 0; i < keys.length; i++) {
            inverseAKeys[i][0] = ModularArithmetics.inverse(keys[i][0], Constants.monogramCount);
            inverseAKeys[i][1] = keys[i][1];
        }
        byte[] plainText = new byte[cipherText.length];
        for (int i = 0; i < cipherText.length; i++) {
            int[] keypair = inverseAKeys[i % inverseAKeys.length];
            plainText[i] = (byte) (((cipherText[i] - keypair[1]) * keypair[0] + 26 * 20000) % 26);
        }
        return plainText;
    }
}
