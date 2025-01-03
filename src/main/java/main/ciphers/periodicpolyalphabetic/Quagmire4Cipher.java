package main.ciphers.periodicpolyalphabetic;

import main.ciphers.monoalphabetic.KeywordSubstitutionCipher;
import main.ciphers.monoalphabetic.MonoAlphabeticCipher;
import main.utils.TextUtilities;

/**
 * Quagmire IV Cipher class that provides methods to encipher and decipher text using Quagmire IV Cipher.
 */
public class Quagmire4Cipher {
    /**
     * This method generates the polyalphabetic keys for Quagmire IV Cipher.
     *
     * @param keywordAlphabet           The keyword alphabet.
     * @param keywordCiphertextAlphabet The keyword ciphertext alphabet.
     * @param keywordShifts             The keyword shifts.
     * @return The polyalphabetic keys.
     */
    public static byte[][] getPolyKeys(byte[] keywordAlphabet, byte[] keywordCiphertextAlphabet, byte[] keywordShifts) {
        keywordAlphabet = KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);
        byte[][] keys = Quagmire2Cipher.getPolyKeys(keywordCiphertextAlphabet, keywordShifts);
        byte[][] polyKeys = new byte[keywordShifts.length][26];
        for (int i = 0; i < 26; i++) {
            int shift = TextUtilities.indexOf(keywordAlphabet, (byte) i);
            for (int j = 0; j < keywordShifts.length; j++) {
                polyKeys[j][i] = keys[j][shift];
            }
        }
        return polyKeys;
    }

    /**
     * This method generates the monoalphabetic substitution and Vigenere keys for Quagmire IV Cipher.
     *
     * @param keywordAlphabet           The keyword alphabet.
     * @param keywordCiphertextAlphabet The keyword ciphertext alphabet.
     * @param keywordShifts             The keyword shifts.
     * @return The monoalphabetic substitution and Vigenere keys.
     */
    public static byte[][] getMonoSubstitutionAndVigenereKeys(byte[] keywordAlphabet, byte[] keywordCiphertextAlphabet, byte[] keywordShifts) {
        byte[] sub1Key = KeywordSubstitutionCipher.generateKey(keywordCiphertextAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);
        byte[] sub2Key = KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, true);
        byte[] vigenereKey = MonoAlphabeticCipher.decipher(keywordShifts, sub1Key);
        return new byte[][]{sub1Key, vigenereKey, sub2Key};
    }

    /**
     * This method enciphers the given plain text using Quagmire IV Cipher.
     *
     * @param plainText                 The plain text.
     * @param keywordAlphabet           The keyword alphabet.
     * @param keywordCiphertextAlphabet The keyword ciphertext alphabet.
     * @param keywordShifts             The keyword shifts.
     * @return The enciphered text.
     */
    public static byte[] encipher(byte[] plainText, byte[] keywordAlphabet, byte[] keywordCiphertextAlphabet, byte[] keywordShifts) {
        return PeriodicPolyAlphabeticSubstitutionCipher.encipher(plainText, getPolyKeys(keywordAlphabet, keywordCiphertextAlphabet, keywordShifts));
    }

    /**
     * This method deciphers the given cipher text using Quagmire IV Cipher.
     *
     * @param cipherText                The cipher text.
     * @param keywordAlphabet           The keyword alphabet.
     * @param keywordCiphertextAlphabet The keyword ciphertext alphabet.
     * @param keywordShifts             The keyword shifts.
     * @return The deciphered text.
     */
    public static byte[] decipher(byte[] cipherText, byte[] keywordAlphabet, byte[] keywordCiphertextAlphabet, byte[] keywordShifts) {
        byte[][] keys = getMonoSubstitutionAndVigenereKeys(keywordAlphabet, keywordCiphertextAlphabet, keywordShifts);
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
