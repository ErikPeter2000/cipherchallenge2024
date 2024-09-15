package main.ciphers;

public class PeriodicPolyAlphabeticSubstitution {
    public static String encipher(String plainText, String[] keys){
        StringBuilder cipherText = new StringBuilder();
        for(int i = 0; i < plainText.length(); i++){
            cipherText.append(keys[i%keys.length].charAt(plainText.charAt(i)-65));
        }
        return cipherText.toString();
    }

    public static String decipher(String cipherText, String[] keys){
        StringBuilder plainText = new StringBuilder();
        for(int i = 0; i < cipherText.length(); i++){
            plainText.append((char)(keys[i%keys.length].indexOf(cipherText.charAt(i))+65));
        }
        return plainText.toString();
    }
}
