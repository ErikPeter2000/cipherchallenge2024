package main.ciphers.periodicpolyalphabetic;

/**
 * Porta Cipher class for enciphering and deciphering text using Porta Cipher algorithm.
 */
public class PortaCipher {
    /**
     * Table for Porta Cipher.
     */
    public static int[][] tableU = new int[26][26];

    /**
     * Generates the table for Porta Cipher.
     */
    public static void generateTableU() {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                tableU[i][j] = 13 + (j - i + 26) % 13;
            }
            for (int j = 13; j < 26; j++) {
                tableU[i][j] = (j + i + 26) % 13;
            }
        }
        for (int i = 13; i < 26; i++) {
            for (int j = 0; j < 13; j++) {
                tableU[i][j] = (i - j - 1) % 13;
            }
            for (int j = 13; j < 26; j++) {
                tableU[i][j] = 13 + (12 - j - i + 26 + 26) % 13;
            }
        }
    }

    /**
     * Enciphers the plain text using the key and version.
     *
     * @param plainText The plain text to encipher.
     * @param key       The key to use for enciphering.
     * @param version   The version of the cipher to use.
     * @return The enciphered text.
     */
    public static byte[] encipher(byte[] plainText, byte[] key, int version) {
        byte[] cipherText = new byte[plainText.length];
        for (int i = 0; i < plainText.length; i++) {
            if (version == 2) {
                cipherText[i] = (byte) tableU[key[i % key.length] / 2][plainText[i]];
            } else if (version == 1) {
                cipherText[i] = (byte) tableU[(13 - key[i % key.length] / 2) % 13][plainText[i]];
            }
        }
        return cipherText;
    }

    /**
     * Deciphers the cipher text using the key and version.
     *
     * @param cipherText The cipher text to decipher.
     * @param key        The key to use for deciphering.
     * @param version    The version of the cipher to use.
     * @return The deciphered text.
     */
    public static byte[] decipher(byte[] cipherText, byte[] key, int version) {
        return encipher(cipherText, key, version);
    }

    /**
     * Enciphers the plain text using the version Bellaso 1552 cipher.
     *
     * @param plainText The plain text to encipher.
     * @param key       The key to use for enciphering.
     * @return The enciphered text.
     */
    public static byte[] encipherBellaso1552(byte[] plainText, byte[] key) {
        byte[] cipherText = new byte[plainText.length];
        for (int i = 0; i < plainText.length; i++) {
            cipherText[i] = (byte) tableU[key[i % key.length]][plainText[i]];
        }
        return cipherText;
    }

    /**
     * Deciphers the cipher text using the version Bellaso 1552 cipher.
     *
     * @param cipherText The cipher text to decipher.
     * @param key        The key to use for deciphering.
     * @return The deciphered text.
     */
    public static byte[] decipherBellaso1552(byte[] cipherText, byte[] key) {
        return encipherBellaso1552(cipherText, key);
    }
}
