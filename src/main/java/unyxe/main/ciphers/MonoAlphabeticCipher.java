package main.ciphers;

import main.utils.Analyser;
import main.utils.Constants;

public class MonoAlphabeticCipher {
    public static boolean likeliness(String text){
        double ioc = Analyser.getIndexOfCoincedence(text, true);
        return (ioc >= 0.85) || (ioc <= 0.93);
    }

    public static String inverseKey(String key){
        StringBuilder inverseKey = new StringBuilder();
        for(int i = 0; i < Constants.monogramCount; i++){
            int index = key.indexOf(Constants.alphabet.charAt(i));
            char c = Constants.alphabet.charAt(index);
            inverseKey.append(c);
        }
        return inverseKey.toString();
    }

    public static String encipher(String plainText, String key){
        StringBuilder cipherText = new StringBuilder();
        for(int i = 0; i < plainText.length(); i++){
            cipherText.append(key.charAt(plainText.charAt(i)-65));
        }
        return cipherText.toString();
    }

    public static String decipher(String cipherText, String key){
        StringBuilder plainText = new StringBuilder();
        for(int i = 0; i < cipherText.length(); i++){
            plainText.append((char)(key.indexOf(cipherText.charAt(i))+65));
        }
        return plainText.toString();
    }

}
