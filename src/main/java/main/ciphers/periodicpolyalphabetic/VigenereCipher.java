package main.ciphers.periodicpolyalphabetic;

import main.utils.Constants;

/**
 * Vigenere Cipher class that provides methods to encipher and decipher text using the Vigenere Cipher.
 */
public class VigenereCipher {
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
            cipherText[i] = (byte) ((plainText[i] + key[i % key.length]) % Constants.monogramCount);
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
        byte[] plainText = new byte[cipherText.length];
        for (int i = 0; i < cipherText.length; i++) {
            plainText[i] = (byte) ((cipherText[i] - key[i % key.length] + Constants.monogramCount * 10) % Constants.monogramCount);
        }
        return plainText;
    }
}
