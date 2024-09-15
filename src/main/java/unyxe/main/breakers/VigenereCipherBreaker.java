package main.breakers;

import main.ciphers.VigenereCipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;

public class VigenereCipherBreaker {

    public static String[] generateKeys(int length){
        String[] keys = new String[(int)Math.pow(Constants.monogramCount, length)];
        for(int i = 0; i < keys.length; i++){
            int num = i;
            StringBuilder key = new StringBuilder();
            for(int k = 0; k < length; k++){
                int num1 = (int) (num/Math.pow(Constants.monogramCount, length - k -1));
                if(num1 > 0){
                    num -= (int) (Math.pow(Constants.monogramCount, length - k-1)*num1);
                }
                key.append((char) (num1 + 65));
            }
            keys[i] = key.toString();
        }
        return keys;
    }
    public static CipherBreakerOutput bruteforce(String cipherText, double maxLength){
        CipherBreakerOutput output = new CipherBreakerOutput("VigenereCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        for(int n = 1; n <= maxLength; n++){
            String[] possibleKeys = generateKeys(n);
            for (String possibleKey : possibleKeys) {
                String text = VigenereCipher.decipher(cipherText, possibleKey);
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if (newFitness > output.fitness) {
                    output.fitness = newFitness;
                    output.key = possibleKey;
                    output.plainText = text;
                }
            }
            System.out.println("Key-length " + n + " finished. Best: " + output.plainText);
        }
        output.isSuccessfull = (output.plainText!=null);
        return output;
    }
}
