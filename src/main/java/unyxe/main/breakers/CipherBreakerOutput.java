package main.breakers;

import main.utils.Constants;
import main.utils.TextUtilities;

import java.util.ArrayList;

public class CipherBreakerOutput<T> {
    public String cipherType;
    public byte[] cipherText;
    public boolean isSuccessful = false;
    public byte[] plainText;
    public ArrayList<T> key;
    public double fitness;

    public CipherBreakerOutput(String cipherType, byte[]  cipherText) {
        this.cipherType = cipherType;
        this.cipherText = cipherText;
    }
    public void displayPlaintext() {
        System.out.println(TextUtilities.convertToString(plainText, Constants.alphabet));
    }
}
