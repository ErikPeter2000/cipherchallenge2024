package main.ciphers;

import main.utils.Constants;

public class BeaufortCipher {
    public static String encipher(String plainText, String key){
        StringBuilder cipherText = new StringBuilder();
        for(int i = 0; i < plainText.length(); i++){
            cipherText.append((char) ((key.charAt(i%key.length()) - plainText.charAt(i) + Constants.monogramCount*10)% Constants.monogramCount + 65));
        }
        return cipherText.toString();
    }
    public static String decipher(String cipherText, String key){
        return encipher(cipherText, key);
    }
}
