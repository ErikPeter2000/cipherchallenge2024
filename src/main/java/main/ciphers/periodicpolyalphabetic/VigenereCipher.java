package main.ciphers.periodicpolyalphabetic;

import main.utils.Constants;

public class VigenereCipher {
    public static byte[] encipher(byte[] plainText, byte[] key) {
        byte[] cipherText = new byte[plainText.length];
        for (int i = 0; i < plainText.length; i++) {
            cipherText[i] = (byte) ((plainText[i] + key[i % key.length]) % Constants.monogramCount);
        }
        return cipherText;
    }

    public static byte[] decipher(byte[] cipherText, byte[] key) {
        byte[] plainText = new byte[cipherText.length];
        for (int i = 0; i < cipherText.length; i++) {
            plainText[i] = (byte) ((cipherText[i] - key[i % key.length] + Constants.monogramCount * 10) % Constants.monogramCount);
        }
        return plainText;
    }
}
