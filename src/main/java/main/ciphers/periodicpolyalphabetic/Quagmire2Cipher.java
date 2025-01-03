package main.ciphers.periodicpolyalphabetic;

import main.ciphers.monoalphabetic.KeywordSubstitutionCipher;
import main.ciphers.monoalphabetic.MonoAlphabeticCipher;
import main.utils.TextUtilities;

/**
 * Quagmire II Cipher class that provides methods to encipher and decipher text using Quagmire II Cipher.
 */
public class Quagmire2Cipher {
    /**
     * Method to get the polyalphabetic keys for Quagmire II Cipher.
     *
     * @param keywordAlphabet The keyword alphabet.
     * @param keywordShifts   The keyword shifts.
     * @return The polyalphabetic keys.
     */
    public static byte[][] getPolyKeys(byte[] keywordAlphabet, byte[] keywordShifts) {
        byte[] cipherKey = KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);
        byte[][] polyKeys = new byte[keywordShifts.length][26];
        for (int i = 0; i < keywordShifts.length; i++) {
            int shift = TextUtilities.indexOf(cipherKey, keywordShifts[i]);
            System.arraycopy(cipherKey, shift, polyKeys[i], 0, 26 - shift);
            System.arraycopy(cipherKey, 0, polyKeys[i], 26 - shift, shift);
        }
        return polyKeys;
    }

    /**
     * Method to get the monoalphabetic substitution and Vigenere keys for Quagmire II Cipher.
     *
     * @param keywordAlphabet The keyword alphabet.
     * @param keywordShifts   The keyword shifts.
     * @return The monoalphabetic substitution and Vigenere keys.
     */
    public static byte[][] getMonoSubstitutionAndVigenereKeys(byte[] keywordAlphabet, byte[] keywordShifts) {
        byte[] substitutionKey = KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);
        byte[] vigenereKey = MonoAlphabeticCipher.decipher(keywordShifts, substitutionKey);
        return new byte[][]{vigenereKey, substitutionKey};
    }

    /**
     * Method to encipher text using Quagmire II Cipher.
     *
     * @param plainText       The plain text to encipher.
     * @param keywordAlphabet The keyword alphabet.
     * @param keywordShifts   The keyword shifts.
     * @return The enciphered text.
     */
    public static byte[] encipher(byte[] plainText, byte[] keywordAlphabet, byte[] keywordShifts) {
        return PeriodicPolyAlphabeticSubstitutionCipher.encipher(plainText, getPolyKeys(keywordAlphabet, keywordShifts));
    }

    /**
     * Method to decipher text using Quagmire II Cipher.
     *
     * @param cipherText      The cipher text to decipher.
     * @param keywordAlphabet The keyword alphabet.
     * @param keywordShifts   The keyword shifts.
     * @return The deciphered text.
     */
    public static byte[] decipher(byte[] cipherText, byte[] keywordAlphabet, byte[] keywordShifts) {
        byte[][] keys = getMonoSubstitutionAndVigenereKeys(keywordAlphabet, keywordShifts);
        return VigenereCipher.decipher(
                MonoAlphabeticCipher.decipher(
                        cipherText,
                        keys[1]
                ),
                keys[0]
        );
    }
}
