package main.ciphers;

import main.utils.Constants;

public class VigenereCipher {
    public static String encipher(String plainText, String key){
        StringBuilder cipherText = new StringBuilder();
        for(int i = 0; i < plainText.length(); i++){
            cipherText.append((char) ((plainText.charAt(i) - 65 + key.charAt(i%key.length())-65)% Constants.monogramCount + 65));
        }
        return cipherText.toString();
    }
    public static String decipher(String cipherText, String key){
        StringBuilder plainText = new StringBuilder();
        for(int i = 0; i < cipherText.length(); i++){
            plainText.append((char) ((cipherText.charAt(i) - key.charAt(i%key.length()) + Constants.monogramCount*10)% Constants.monogramCount + 65));
        }
        return plainText.toString();
    }

}
