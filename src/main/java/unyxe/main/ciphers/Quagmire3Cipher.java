package main.ciphers;

import main.utils.TextUtilities;

public class Quagmire3Cipher {
    public static byte[][] getPolyKeys(byte[] keywordAlphabet, byte[] keywordShifts){
        keywordAlphabet = KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);
        byte[][] keys = Quagmire2Cipher.getPolyKeys(keywordAlphabet, keywordShifts);
        byte[][] polyKeys = new byte[keywordShifts.length][26];
        for(int i = 0; i < 26;i++){
            int shift = TextUtilities.indexOf(keywordAlphabet, (byte)i);
            for(int j = 0; j < keywordShifts.length; j++){
                polyKeys[j][i] = keys[j][shift];
            }
        }
        return polyKeys;
    }

    public static byte[][] getMonoSubstitutionAndVigenereKeys(byte[] keywordAlphabet, byte[] keywordShifts){
        return null;
    }

    public static byte[] encipher(byte[] plainText, byte[] keywordAlphabet, byte[] keywordShifts){
        return PeriodicPolyAlphabeticSubstitution.encipher(plainText, getPolyKeys(keywordAlphabet, keywordShifts));
    }
    public static byte[] decipher(byte[] cipherText, byte[] keywordAlphabet, byte[] keywordShifts){
        return PeriodicPolyAlphabeticSubstitution.decipher(cipherText, getPolyKeys(keywordAlphabet, keywordShifts));
    }
}