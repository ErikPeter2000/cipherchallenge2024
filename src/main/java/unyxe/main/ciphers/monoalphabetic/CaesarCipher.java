package main.ciphers.monoalphabetic;

public class CaesarCipher {
    public static byte[] encipher(byte[] plaintext, int offset){
        byte[] cipherText = new byte[plaintext.length];
        for(int i = 0; i < plaintext.length; i++){
            cipherText[i] = (byte)((plaintext[i]+offset)%26);
        }
        return cipherText;
    }
    public static byte[] decipher(byte[] cipherText, int offset){
        return encipher(cipherText, 26-offset);
    }
}
