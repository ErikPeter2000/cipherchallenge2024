package main.ciphers;

import java.util.Arrays;

public class Quagmire1Cipher {
    public static String[] getPolyKeys(String keywordAlphabet, String keywordShifts){
        keywordAlphabet = KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);
        int beginShift = keywordAlphabet.indexOf('A');
        String[] keys = new String[keywordShifts.length()];
        for(int i = 0; i < keywordShifts.length(); i++){
            StringBuilder key = new StringBuilder();
            for(int j = 0; j < 26;j++){
                key.append((char) ((j - beginShift + (keywordShifts.charAt(i) - 65) + 26 * 2) % 26 + 65));
            }
            keys[i] = key.toString();
        }
        String[] polyKeys = new String[keywordShifts.length()];
        Arrays.fill(polyKeys, "");
        for(int i = 0; i < 26;i++){
            int shift = keywordAlphabet.indexOf((char)(i+65));
            for(int j = 0; j < keywordShifts.length(); j++){
                polyKeys[j] += keys[j].charAt(shift);
            }
        }
        return polyKeys;
    }

    public static String[] getMonoSubstitutionAndVigenereKeys(String keywordAlphabet, String keywordShifts){
        keywordAlphabet = KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);
        String monoSubstitutionKey = MonoAlphabeticCipher.inverseKey(keywordAlphabet);
        String vigenereKey = CaesarCipher.encipher(keywordShifts, 26 - keywordShifts.charAt(0)+65);
        return new String[]{monoSubstitutionKey, vigenereKey};
    }

    public static String encipher(String plainText, String keywordAlphabet, String keywordShifts){
        return PeriodicPolyAlphabeticSubstitution.encipher(plainText, getPolyKeys(keywordAlphabet, keywordShifts));
    }
    public static String decipher(String cipherText, String keywordAlphabet, String keywordShifts){
        return PeriodicPolyAlphabeticSubstitution.decipher(cipherText, getPolyKeys(keywordAlphabet, keywordShifts));
    }
}
