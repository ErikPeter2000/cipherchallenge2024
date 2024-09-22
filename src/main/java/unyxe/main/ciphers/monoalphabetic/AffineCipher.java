package main.ciphers.monoalphabetic;

import main.utils.Constants;
import main.utils.maths.ModularArithmetics;

public class AffineCipher {
    public static boolean isKeyInvalid(int a){
        return !ModularArithmetics.isCoprime(a, Constants.monogramCount);
    }

    public static byte[] convertToMAKey(int a, int b){
        if(isKeyInvalid(a)) {
            throw new IllegalArgumentException("Key is not valid");
        }
        byte[] key = new byte[Constants.monogramCount];
        for(int i = 0; i < key.length; i++){
            key[i] = (byte) ((i * a + b) % 26);
        }
        return key;
    }
    public static byte[] encipher(byte[] plainText, int a, int b){
        if(isKeyInvalid(a)) {
            throw new IllegalArgumentException("Key is not valid");
        }
        byte[] cipherText = new byte[plainText.length];
        for(int i = 0; i < plainText.length; i++){
            cipherText[i] = (byte) ((plainText[i] * a + b) % 26);
        }
        return cipherText;
    }
    public static byte[] decipher(byte[] cipherText, int a, int b){
        if(isKeyInvalid(a)) {
            throw new IllegalArgumentException("Key is not valid");
        }
        int inverseA = ModularArithmetics.inverse(a, Constants.monogramCount);
        byte[] plainText = new byte[cipherText.length];
        for(int i = 0; i < cipherText.length; i++){
            plainText[i] = (byte)(((cipherText[i] - b)*inverseA + 26*2000)%26);
        }
        return plainText;
    }
}
