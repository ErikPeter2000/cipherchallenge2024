package main.breakers;

public class CipherBreakerOutput {
    public String cipherType;
    public String cipherText;
    public boolean isSuccessfull = false;
    public String plainText;
    public String key;
    public double fitness;

    public CipherBreakerOutput(String cipherType, String cipherText) {
        this.cipherType = cipherType;
        this.cipherText = cipherText;
    }
    public void displayPlaintext() {
        System.out.println(plainText);
    }
}
