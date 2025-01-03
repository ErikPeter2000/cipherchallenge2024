package main.ciphers.periodicpolyalphabetic;

import main.utils.TextUtilities;

/**
 * Periodic PolyAlphabetic Substitution Cipher class to encipher and decipher text using a periodic polyalphabetic substitution cipher.
 */
public class PeriodicPolyAlphabeticSubstitutionCipher {
    /**
     * Enciphers the given plain text using the given keys.
     *
     * @param plainText The plain text to encipher.
     * @param keys      The keys to use for enciphering.
     * @return The enciphered text.
     */
    public static byte[] encipher(byte[] plainText, byte[][] keys) {
        byte[] cipherText = new byte[plainText.length];
        for (int i = 0; i < plainText.length; i++) {
            cipherText[i] = (keys[i % keys.length][(plainText[i])]);
        }
        return cipherText;
    }

    /**
     * Deciphers the given cipher text using the given keys.
     *
     * @param cipherText The cipher text to decipher.
     * @param keys       The keys to use for deciphering.
     * @return The deciphered text.
     */
    public static byte[] decipher(byte[] cipherText, byte[][] keys) {
        byte[] plainText = new byte[cipherText.length];
        for (int i = 0; i < cipherText.length; i++) {
            plainText[i] = (byte) (TextUtilities.indexOf(keys[i % keys.length], cipherText[i]));
        }
        return plainText;
    }
}
