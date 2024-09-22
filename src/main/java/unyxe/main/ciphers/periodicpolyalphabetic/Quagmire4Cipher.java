package main.ciphers.periodicpolyalphabetic;

import main.ciphers.monoalphabetic.KeywordSubstitutionCipher;
import main.ciphers.monoalphabetic.MonoAlphabeticCipher;
import main.utils.TextUtilities;

public class Quagmire4Cipher {
    public static byte[][] getPolyKeys(byte[] keywordAlphabet, byte[] keywordCiphertextAlphabet,byte[] keywordShifts){
        keywordAlphabet = KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);
        byte[][] keys = Quagmire2Cipher.getPolyKeys(keywordCiphertextAlphabet, keywordShifts);
        byte[][] polyKeys = new byte[keywordShifts.length][26];
        for(int i = 0; i < 26;i++){
            int shift = TextUtilities.indexOf(keywordAlphabet, (byte)i);
            for(int j = 0; j < keywordShifts.length; j++){
                polyKeys[j][i] = keys[j][shift];
            }
        }
        return polyKeys;
    }

    public static byte[][] getMonoSubstitutionAndVigenereKeys(byte[] keywordAlphabet, byte[] keywordCiphertextAlphabet,byte[] keywordShifts){
        byte[] sub1Key = KeywordSubstitutionCipher.generateKey(keywordCiphertextAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);
        byte[] sub2Key = KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, true);
        byte[] vigenereKey = MonoAlphabeticCipher.decipher(keywordShifts,sub1Key);
        return new byte[][]{sub1Key, vigenereKey, sub2Key};
    }

    public static byte[] encipher(byte[] plainText, byte[] keywordAlphabet, byte[] keywordCiphertextAlphabet, byte[] keywordShifts){
        return PeriodicPolyAlphabeticSubstitutionCipher.encipher(plainText, getPolyKeys(keywordAlphabet, keywordCiphertextAlphabet,keywordShifts));
    }
    public static byte[] decipher(byte[] cipherText, byte[] keywordAlphabet, byte[] keywordCiphertextAlphabet, byte[] keywordShifts){
        byte[][] keys = getMonoSubstitutionAndVigenereKeys(keywordAlphabet, keywordCiphertextAlphabet,keywordShifts);
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
