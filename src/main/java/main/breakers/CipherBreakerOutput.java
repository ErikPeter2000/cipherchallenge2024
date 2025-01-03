package main.breakers;

import main.utils.Constants;
import main.utils.TextUtilities;

import java.util.ArrayList;

/**
 * Class to store the output of a cipher breaker
 * @param <T> The type of the key
 */
public class CipherBreakerOutput<T> {
    /**
     * The type of the cipher
     */
    public String cipherType;
    /**
     * The cipher text
     */
    public byte[] cipherText;
    /**
     * Whether the decryption was successful
     */
    public boolean isSuccessful = true;
    /**
     * The plaintext
     */
    public byte[] plainText;
    /**
     * The key
     */
    public ArrayList<T> key;
    /**
     * The fitness of the plaintext
     */
    public double fitness;

    /**
     * Constructor
     * @param cipherType The type of the cipher
     * @param cipherText The cipher text
     */
    public CipherBreakerOutput(String cipherType, byte[] cipherText) {
        this.cipherType = cipherType;
        this.cipherText = cipherText;
    }

    /**
     * Prints the plaintext
     */
    public void displayPlaintext() {
        System.out.println(getStringPlaintext());
    }

    /**
     * Gets the plaintext as a string
     * @return The plaintext as a string
     */
    public String getStringPlaintext() {
        if (plainText == null) return "null";
        return TextUtilities.convertToString(plainText, Constants.alphabet);
    }
}
