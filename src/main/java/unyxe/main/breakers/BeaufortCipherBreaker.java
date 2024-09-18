package main.breakers;

import main.ciphers.BeaufortCipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;

import java.util.ArrayList;
import java.util.Arrays;

public class BeaufortCipherBreaker {
    public static CipherBreakerOutput<byte[]> bruteforce(byte[] cipherText, double maxLength){
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("BeaufortCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        byte[] best_key=null;

        for(int n = 1; n <= maxLength; n++){
            byte[][] possibleKeys = VigenereCipherBreaker.generateKeys(n);
            for (byte[] possibleKey : possibleKeys) {
                best_key = updateBestBruteforceKey(cipherText, output, best_key, possibleKey);
            }
        }
        output.isSuccessful = (output.plainText!=null);
        output.key = new ArrayList<>();
        output.key.add(best_key);
        return output;
    }

    private static byte[] updateBestBruteforceKey(byte[] cipherText, CipherBreakerOutput<byte[]> output, byte[] best_key, byte[] possibleKey) {
        byte[] text = BeaufortCipher.decipher(cipherText, possibleKey);
        double newFitness = FitnessCalculator.TetragramFitness(text);
        if (newFitness > output.fitness) {
            output.fitness = newFitness;
            best_key = Arrays.copyOf(possibleKey, possibleKey.length);
            output.plainText = text;
        }
        return best_key;
    }

    public static CipherBreakerOutput<byte[]> bruteforceWithWordlist(byte[] cipherText){
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("BeaufortCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        byte[] best_key=null;

        for(int n = 0; n < Constants.wordlist.length; n++){
            byte[] key = Constants.wordlist[n];
            best_key = updateBestBruteforceKey(cipherText, output, best_key, key);
        }
        output.isSuccessful = (output.plainText!=null);
        output.key = new ArrayList<>();
        output.key.add(best_key);
        return output;
    }
    public static CipherBreakerOutput<byte[]> hillClimberAttack(byte[] cipherText, int period){
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("BeaufortCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);

        byte[] best_key;
        byte[] key = new byte[period];
        while(true){
            double oldFitness = output.fitness;
            for(int i = 0; i < period;i++){
                double maxFitness = -9999999;
                byte bestLetter = key[i];
                for(int j = 0; j < Constants.monogramCount; j++){
                    key[i] = (byte)(j);
                    byte[] text = BeaufortCipher.decipher(cipherText, key);
                    double newFitness = FitnessCalculator.TetragramFitness(text);
                    if (newFitness > maxFitness) {
                        maxFitness = newFitness;
                        bestLetter = (byte)(j);
                    }
                }
                key[i] = bestLetter;
            }
            best_key = Arrays.copyOfRange(key, 0, period);
            output.plainText = BeaufortCipher.decipher(cipherText, key);
            output.fitness = FitnessCalculator.TetragramFitness(output.plainText);
            if(output.fitness == oldFitness){break;}
        }
        output.isSuccessful = true;
        output.key = new ArrayList<>();
        output.key.add(best_key);
        return output;
    }
}
