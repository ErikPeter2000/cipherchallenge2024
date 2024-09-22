package main.ciphers.periodicpolyalphabetic;

public class VariantBeaufortCipher {
    public static byte[] encipher(byte[] plainText, byte[] key){
        return VigenereCipher.decipher(plainText, key);
    }
    public static byte[] decipher(byte[] cipherText, byte[] key){
        return VigenereCipher.encipher(cipherText, key);
    }
}
