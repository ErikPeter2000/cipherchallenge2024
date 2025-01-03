package main.ciphers.periodicpolyalphabetic;

import main.ciphers.monoalphabetic.CaesarCipher;
import main.ciphers.monoalphabetic.KeywordSubstitutionCipher;
import main.ciphers.monoalphabetic.MonoAlphabeticCipher;
import main.utils.TextUtilities;

/**
 * Quagmire I Cipher class that provides methods to encipher and decipher text using Quagmire I Cipher.
 */
public class Quagmire1Cipher {
    /**
     * Method to get the polyalphabetic keys for Quagmire I Cipher.
     *
     * @param keywordAlphabet The keyword alphabet.
     * @param keywordShifts   The keyword shifts.
     * @return The polyalphabetic keys.
     */
    public static byte[][] getPolyKeys(byte[] keywordAlphabet, byte[] keywordShifts) {
        keywordAlphabet = KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);
        int beginShift = TextUtilities.indexOf(keywordAlphabet, (byte) 0);
        byte[][] keys = new byte[keywordShifts.length][];
        for (int i = 0; i < keywordShifts.length; i++) {
            byte[] key = new byte[26];
            for (int j = 0; j < key.length; j++) {
                key[j] = (byte) ((j - beginShift + (keywordShifts[i]) + 26 * 2) % 26);
            }
            keys[i] = key;
        }
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
     * Method to get the mono substitution and Vigenere keys for Quagmire I Cipher.
     *
     * @param keywordAlphabet The keyword alphabet.
     * @param keywordShifts   The keyword shifts.
     * @return The mono substitution and Vigenere keys.
     */
    public static byte[][] getMonoSubstitutionAndVigenereKeys(byte[] keywordAlphabet, byte[] keywordShifts) {
        keywordAlphabet = KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);
        byte[] monoSubstitutionKey = MonoAlphabeticCipher.inverseKey(keywordAlphabet);
        byte[] vigenereKey = CaesarCipher.encipher(keywordShifts, 26 - keywordShifts[0]);
        return new byte[][]{monoSubstitutionKey, vigenereKey};
    }

    /**
     * Method to encipher text using Quagmire I Cipher.
     *
     * @param plainText       The plain text.
     * @param keywordAlphabet The keyword alphabet.
     * @param keywordShifts   The keyword shifts.
     * @return The enciphered text.
     */
    public static byte[] encipher(byte[] plainText, byte[] keywordAlphabet, byte[] keywordShifts) {
        return PeriodicPolyAlphabeticSubstitutionCipher.encipher(plainText, getPolyKeys(keywordAlphabet, keywordShifts));
    }

    /**
     * Method to decipher text using Quagmire I Cipher.
     *
     * @param cipherText      The cipher text.
     * @param keywordAlphabet The keyword alphabet.
     * @param keywordShifts   The keyword shifts.
     * @return The deciphered text.
     */
    public static byte[] decipher(byte[] cipherText, byte[] keywordAlphabet, byte[] keywordShifts) {
        return MonoAlphabeticCipher.encipher(
                VigenereCipher.decipher(
                        cipherText,
                        CaesarCipher.encipher(keywordShifts, 26 - keywordShifts[0])
                ),
                KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false)
        );
    }
}
