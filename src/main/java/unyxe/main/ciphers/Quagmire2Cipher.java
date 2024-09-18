package main.ciphers;

import main.utils.TextUtilities;

public class Quagmire2Cipher {
    public static byte[][] getPolyKeys(byte[] keywordAlphabet, byte[] keywordShifts){
        byte[] cipherKey = KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);
        byte[][] polyKeys = new byte[keywordShifts.length][26];
        for(int i = 0; i < keywordShifts.length; i++){
            int shift = TextUtilities.indexOf(cipherKey, keywordShifts[i]);
            System.arraycopy(cipherKey, shift, polyKeys[i], 0, 26-shift);
            System.arraycopy(cipherKey, 0, polyKeys[i], 26-shift, shift);
        }
        return polyKeys;
    }

    public static byte[][] getMonoSubstitutionAndVigenereKeys(byte[] keywordAlphabet, byte[] keywordShifts){
        byte[] substitutionKey = KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);
        byte[] vigenereKey = MonoAlphabeticCipher.decipher(keywordShifts, substitutionKey);
        return new byte[][]{vigenereKey, substitutionKey};
    }

    public static byte[] encipher(byte[] plainText, byte[] keywordAlphabet, byte[] keywordShifts){
        return PeriodicPolyAlphabeticSubstitution.encipher(plainText, getPolyKeys(keywordAlphabet, keywordShifts));
    }
    public static byte[] decipher(byte[] cipherText, byte[] keywordAlphabet, byte[] keywordShifts){
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
