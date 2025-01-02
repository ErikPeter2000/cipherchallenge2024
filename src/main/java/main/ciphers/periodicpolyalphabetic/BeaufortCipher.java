package main.ciphers.periodicpolyalphabetic;

import main.utils.Constants;

public class BeaufortCipher {
    public static byte[] encipher(byte[] plainText, byte[] key) {
        byte[] cipherText = new byte[plainText.length];
        for (int i = 0; i < plainText.length; i++) {
            cipherText[i] = (byte) ((key[i % key.length] - plainText[i] + Constants.monogramCount * 10) % Constants.monogramCount);
        }
        return cipherText;
    }

    public static byte[] decipher(byte[] cipherText, byte[] key) {
        return encipher(cipherText, key);
    }
}
