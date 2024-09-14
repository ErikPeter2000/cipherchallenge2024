package main.ciphers;

import main.utils.Constants;
import main.utils.ModularArithmetics;

public class AffineCipher {
    public static boolean isKeyInvalid(int a){
        return ModularArithmetics.gcd(a, Constants.monogramCount) != 1;
    }

    public static String convertToMAKey(int a, int b){
        if(isKeyInvalid(a)) {
            throw new IllegalArgumentException("Key is not valid");
        }
        StringBuilder key = new StringBuilder();
        for(int i = 0; i < Constants.monogramCount; i++){
            key.append((char) (65 + ((i * a + b) % 26)));
        }
        return key.toString();
    }
    public static String encipher(String plainText, int a, int b){
        if(isKeyInvalid(a)) {
            throw new IllegalArgumentException("Key is not valid");
        }
        StringBuilder cipherText = new StringBuilder();
        for(int i = 0; i < plainText.length(); i++){
            cipherText.append((char) (65 + (((plainText.charAt(i)-65) * a + b) % 26)));
        }
        return cipherText.toString();
    }
    public static String decipher(String cipherText, int a, int b){
        if(isKeyInvalid(a)) {
            throw new IllegalArgumentException("Key is not valid");
        }
        int inverseA = ModularArithmetics.inverse(a, Constants.monogramCount);
        StringBuilder plainText = new StringBuilder();
        for(int i = 0; i < cipherText.length(); i++){
            plainText.append((char) (65 + Math.floorMod(((cipherText.charAt(i)-65) - b)*inverseA,26)));
        }
        return plainText.toString();
    }
}
