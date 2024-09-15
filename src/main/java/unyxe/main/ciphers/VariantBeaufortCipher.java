package main.ciphers;

public class VariantBeaufortCipher {
    public static String encipher(String plainText, String key){
        return VigenereCipher.decipher(plainText, key);
    }
    public static String decipher(String cipherText, String key){
        return VigenereCipher.encipher(cipherText, key);
    }
}
