package main.ciphers;

public class CaesarCipher {
    public static String encipher(String plaintext, int offset){
        StringBuilder ciphertext = new StringBuilder();
        for(int i = 0; i < plaintext.length(); i++){
            ciphertext.append((char)((plaintext.charAt(i)-65+offset)%26 +65));
        }
        return ciphertext.toString();
    }
    public static String decipher(String ciphertext, int offset){
        return encipher(ciphertext, 26-offset);
    }
}
