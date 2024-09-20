package main.ciphers;

public class Quagmire3Cipher {
    public static byte[][] getPolyKeys(byte[] keywordAlphabet, byte[] keywordShifts){
        return Quagmire4Cipher.getPolyKeys(keywordAlphabet, keywordAlphabet, keywordShifts);
    }

    public static byte[][] getMonoSubstitutionAndVigenereKeys(byte[] keywordAlphabet, byte[] keywordShifts){
        byte[] sub1Key = KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);
        byte[] sub2Key = MonoAlphabeticCipher.inverseKey(sub1Key);
        byte[] vigenereKey = MonoAlphabeticCipher.encipher(keywordShifts,sub2Key);
        return new byte[][]{sub1Key, vigenereKey, sub2Key};
    }

    public static byte[] encipher(byte[] plainText, byte[] keywordAlphabet, byte[] keywordShifts){
        return PeriodicPolyAlphabeticSubstitutionCipher.encipher(plainText, getPolyKeys(keywordAlphabet, keywordShifts));
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
