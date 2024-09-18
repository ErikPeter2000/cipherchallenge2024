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
        byte[] sub1Key = KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);
        byte[] sub2Key = MonoAlphabeticCipher.inverseKey(sub1Key);
        byte[] vigenereKey = MonoAlphabeticCipher.encipher(keywordShifts,sub2Key);
        return new byte[][]{sub1Key, vigenereKey, sub2Key};
    }

    public static byte[] encipher(byte[] plainText, byte[] keywordAlphabet, byte[] keywordShifts){
        return PeriodicPolyAlphabeticSubstitution.encipher(plainText, getPolyKeys(keywordAlphabet, keywordShifts));
    }
    public static byte[] decipher(byte[] cipherText, byte[] keywordAlphabet, byte[] keywordShifts){
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
