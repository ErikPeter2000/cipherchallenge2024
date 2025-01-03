package main.ciphers.periodicpolyalphabetic;

/**
 * Variant of Beaufort Cipher class that provides methods to encipher and decipher text using the Variant Beaufort Cipher.
 */
public class VariantBeaufortCipher {
    /**
     * Enciphers the given plain text using the given key.
     *
     * @param plainText The plain text to encipher.
     * @param key       The key to use for enciphering.
     * @return The enciphered text.
     */
    public static byte[] encipher(byte[] plainText, byte[] key) {
        return VigenereCipher.decipher(plainText, key);
    }

    /**
     * Deciphers the given cipher text using the given key.
     *
     * @param cipherText The cipher text to decipher.
     * @param key        The key to use for deciphering.
     * @return The deciphered text.
     */
    public static byte[] decipher(byte[] cipherText, byte[] key) {
        return VigenereCipher.encipher(cipherText, key);
    }
}
