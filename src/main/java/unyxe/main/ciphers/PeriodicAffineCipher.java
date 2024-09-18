package main.ciphers;

import main.utils.Constants;
import main.utils.ModularArithmetics;

public class PeriodicAffineCipher {
    public static boolean areKeysInvalid(int[][] keys){
        for (int[] key : keys) {
            if (AffineCipher.isKeyInvalid(key[0])) return true;
        }
        return false;
    }
    public static byte[] encipher(byte[] plainText, int[][] keys){
        if(areKeysInvalid(keys)) {
            throw new IllegalArgumentException("Keys are not valid");
        }
        byte[] cipherText = new byte[plainText.length];
        for(int i = 0; i < plainText.length; i++){
            int[] keypair = keys[i% keys.length];
            cipherText[i] = (byte)((plainText[i] * keypair[0] + keypair[1]) % 26);
        }
        return cipherText;
    }
    public static byte[] decipher(byte[] cipherText, int[][] keys){
        if(areKeysInvalid(keys)) {
            throw new IllegalArgumentException("Keys are not valid");
        }
        int[][] inverseAKeys = new int[keys.length][2];
        for(int i = 0; i < keys.length; i++){
            inverseAKeys[i][0] = ModularArithmetics.inverse(keys[i][0], Constants.monogramCount);
            inverseAKeys[i][1] = keys[i][1];
        }
        byte[] plainText = new byte[cipherText.length];
        for(int i = 0; i < cipherText.length; i++){
            int[] keypair = inverseAKeys[i%inverseAKeys.length];
            plainText[i] = (byte) (Math.floorMod((cipherText[i] - keypair[1])*keypair[0],26));
        }
        return plainText;
    }
}
