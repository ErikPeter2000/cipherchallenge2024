package main.ciphers.monoalphabetic;

import main.utils.Analyser;
import main.utils.TextUtilities;

/**
 * MonoAlphabeticCipher class is used to encipher and decipher text using a monoalphabetic cipher.
 * A monoalphabetic cipher is a substitution cipher where each letter in an alphabet is mapped to a single letter.
 * The key is a permutation of the alphabet.
 */
public class MonoAlphabeticCipher {
    /**
     * Generates a random key for the monoalphabetic cipher.
     *
     * @return a random key for the monoalphabetic cipher.
     */
    public static boolean isLikely(byte[] text) {
        double ioc = Analyser.getIndexOfCoincedence(text, true);
        // This is the range of index of coincidence in natural english text.
        return (ioc >= 0.85) || (ioc <= 0.93);
    }

    /**
     * Generates an inverse key for the given key.
     *
     * @param key the key to generate the inverse key for.
     * @return the inverse key for the given key.
     */
    public static byte[] inverseKey(byte[] key) {
        byte[] inverseKey = new byte[key.length];
        for (int i = 0; i < key.length; i++) {
            inverseKey[i] = (byte) TextUtilities.indexOf(key, (byte) i);
        }
        return inverseKey;
    }

    /**
     * Enciphers the given plain text using the given key.
     *
     * @param plainText the plain text to encipher.
     * @param key       the key to use for enciphering.
     * @return the enciphered text.
     */
    public static byte[] encipher(byte[] plainText, byte[] key) {
        byte[] cipherText = new byte[plainText.length];
        for (int i = 0; i < plainText.length; i++) {
            cipherText[i] = key[plainText[i]];
        }
        return cipherText;
    }

    /**
     * Deciphers the given cipher text using the given key.
     *
     * @param cipherText the cipher text to decipher.
     * @param key        the key to use for deciphering.
     * @return the deciphered text.
     */
    public static byte[] decipher(byte[] cipherText, byte[] key) {
        return encipher(cipherText, inverseKey(key));
    }
}
