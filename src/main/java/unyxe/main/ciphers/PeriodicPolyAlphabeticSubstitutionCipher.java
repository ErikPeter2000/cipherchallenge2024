package main.ciphers;

import main.utils.TextUtilities;

public class PeriodicPolyAlphabeticSubstitutionCipher {
    public static byte[] encipher(byte[] plainText, byte[][] keys){
        byte[] cipherText = new byte[plainText.length];
        for(int i = 0; i < plainText.length; i++){
            cipherText[i] = (keys[i%keys.length][(plainText[i])]);
        }
        return cipherText;
    }

    public static byte[] decipher(byte[] cipherText, byte[][] keys){
        byte[] plainText = new byte[cipherText.length];
        for(int i = 0; i < cipherText.length; i++){
            plainText[i] = (byte)(TextUtilities.indexOf(keys[i%keys.length], cipherText[i]));
        }
        return plainText;
    }
}
