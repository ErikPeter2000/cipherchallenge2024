package main.ciphers.monoalphabetic;

/**
 * CaesarCipher class is used to encipher and decipher text using Caesar cipher.
 */
public class CaesarCipher {
    /**
     * encipher method is used to encipher the plaintext using Caesar cipher.
     *
     * @param plaintext byte[] - The plaintext to be enciphered.
     * @param offset    int - The offset to be used for enciphering.
     * @return byte[] - The enciphered text.
     */
    public static byte[] encipher(byte[] plaintext, int offset) {
        byte[] cipherText = new byte[plaintext.length];
        for (int i = 0; i < plaintext.length; i++) {
            cipherText[i] = (byte) ((plaintext[i] + offset) % 26);
        }
        return cipherText;
    }

    /**
     * decipher method is used to decipher the ciphertext using Caesar cipher.
     *
     * @param cipherText byte[] - The ciphertext to be deciphered.
     * @param offset     int - The offset to be used for deciphering.
     * @return byte[] - The deciphered text.
     */
    public static byte[] decipher(byte[] cipherText, int offset) {
        return encipher(cipherText, 26 - offset);
    }
}
