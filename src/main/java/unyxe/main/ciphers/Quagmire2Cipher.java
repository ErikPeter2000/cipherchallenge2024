package main.ciphers;

public class Quagmire2Cipher {
    public static String[] getPolyKeys(String keywordAlphabet, String keywordShifts){
        String cipherKey = KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);
        String[] polyKeys = new String[keywordShifts.length()];
        for(int i = 0; i < keywordShifts.length(); i++){
            int shift = cipherKey.indexOf(keywordShifts.charAt(i));
            polyKeys[i] = cipherKey.substring(shift, 26)+cipherKey.substring(0, shift);
        }
        return polyKeys;
    }

    public static String[] getMonoSubstitutionAndVigenereKeys(String keywordAlphabet, String keywordShifts){
        String substitutionKey = KeywordSubstitutionCipher.generateKey(keywordAlphabet, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);
        String vigenereKey = MonoAlphabeticCipher.decipher(keywordShifts, substitutionKey);
        return new String[]{vigenereKey, substitutionKey};
    }

    public static String encipher(String plainText, String keywordAlphabet, String keywordShifts){
        return PeriodicPolyAlphabeticSubstitution.encipher(plainText, getPolyKeys(keywordAlphabet, keywordShifts));
    }
    public static String decipher(String cipherText, String keywordAlphabet, String keywordShifts){
        return PeriodicPolyAlphabeticSubstitution.decipher(cipherText, getPolyKeys(keywordAlphabet, keywordShifts));
    }
}
