package main.ciphers.periodicpolyalphabetic;

import main.ciphers.monoalphabetic.KeywordSubstitutionCipher;
import main.ciphers.monoalphabetic.MonoAlphabeticCipher;

/**
 * Quagmire III Cipher class for enciphering and deciphering text using Quagmire III Cipher algorithm.
 */
public class Quagmire3Cipher {
    /**
     * This method generates the polyalphabetic keys for Quagmire III Cipher.
     *
     * @param keywordAlphabet The keyword alphabet.
     * @param keywordShifts   The keyword shifts.
     * @return The polyalphabetic keys.
     */
    public static byte[][] getPolyKeys(byte[] keywordAlphabet, byte[] keywordShifts) {
        return Quagmire4Cipher.getPolyKeys(keywordAlphabet, keywordAlphabet, keywordShifts);
    }

    /**
     * This method generates the monoalphabetic substitution and Vigenere keys for Quagmire III Cipher.
     *
     * @param keywordAlphabet The keyword alphabet.
     * @param keywordShifts   The keyword shifts.
     * @return The monoalphabetic substitution and Vigenere keys.
     */
    public static byte[][] getMonoSubstitutionAndVigenereKeys(byte[] keywordAlphabet, byte[] keywordShifts) {
        byte[] sub1Key = KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);
        byte[] sub2Key = MonoAlphabeticCipher.inverseKey(sub1Key);
        byte[] vigenereKey = MonoAlphabeticCipher.encipher(keywordShifts, sub2Key);
        return new byte[][]{sub1Key, vigenereKey, sub2Key};
    }

    /**
     * This method enciphers the given plain text using Quagmire III Cipher algorithm.
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
     * This method deciphers the given cipher text using Quagmire III Cipher algorithm.
     *
     * @param cipherText      The cipher text to decipher.
     * @param keywordAlphabet The keyword alphabet.
     * @param keywordShifts   The keyword shifts.
     * @return The deciphered text.
     */
    public static byte[] decipher(byte[] cipherText, byte[] keywordAlphabet, byte[] keywordShifts) {
        byte[][] keys = getMonoSubstitutionAndVigenereKeys(keywordAlphabet, keywordShifts);
        return MonoAlphabeticCipher.decipher(
                VigenereCipher.decipher(
                        MonoAlphabeticCipher.decipher(
                                cipherText,
                                keys[0]
                        ),
                        keys[1]
                ),
                keys[2]
        );
    }
}
