package main.ciphers.periodicpolyalphabetic;

import main.ciphers.monoalphabetic.CaesarCipher;
import main.ciphers.monoalphabetic.KeywordSubstitutionCipher;
import main.ciphers.monoalphabetic.MonoAlphabeticCipher;
import main.utils.TextUtilities;

public class Quagmire1Cipher {
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

    public static byte[][] getMonoSubstitutionAndVigenereKeys(byte[] keywordAlphabet, byte[] keywordShifts) {
        keywordAlphabet = KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);
        byte[] monoSubstitutionKey = MonoAlphabeticCipher.inverseKey(keywordAlphabet);
        byte[] vigenereKey = CaesarCipher.encipher(keywordShifts, 26 - keywordShifts[0]);
        return new byte[][]{monoSubstitutionKey, vigenereKey};
    }

    public static byte[] encipher(byte[] plainText, byte[] keywordAlphabet, byte[] keywordShifts) {
        return PeriodicPolyAlphabeticSubstitutionCipher.encipher(plainText, getPolyKeys(keywordAlphabet, keywordShifts));
    }

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
