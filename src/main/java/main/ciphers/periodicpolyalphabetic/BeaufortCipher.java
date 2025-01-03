package main.ciphers.periodicpolyalphabetic;

import main.utils.Constants;

/**
 * Beaufort Cipher class to encipher and decipher text using Beaufort Cipher.
 */
public class BeaufortCipher {
    /**
     * Enciphers the given plain text using the given key.
     *
     * @param plainText The plain text to encipher.
     * @param key       The key to use for enciphering.
     * @return The enciphered text.
     */
    public static byte[] encipher(byte[] plainText, byte[] key) {
        byte[] cipherText = new byte[plainText.length];
        for (int i = 0; i < plainText.length; i++) {
            cipherText[i] = (byte) ((key[i % key.length] - plainText[i] + Constants.monogramCount * 10) % Constants.monogramCount);
        }
        return cipherText;
    }

    /**
     * Deciphers the given cipher text using the given key.
     *
     * @param cipherText The cipher text to decipher.
     * @param key        The key to use for deciphering.
     * @return The deciphered text.
     */
    public static byte[] decipher(byte[] cipherText, byte[] key) {
        return encipher(cipherText, key);
    }
}
