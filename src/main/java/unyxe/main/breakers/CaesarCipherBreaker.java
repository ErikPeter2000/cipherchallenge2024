package main.breakers;

import main.ciphers.CaesarCipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;

import java.util.ArrayList;

public class CaesarCipherBreaker {
    public static CipherBreakerOutput<Integer> bruteforceTF(byte[] cipherText){
        CipherBreakerOutput<Integer> output = new CipherBreakerOutput<>("CaesarCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        int best_key = 0;
        for(int i = 1; i < Constants.monogramCount;i++){
            byte[] text = CaesarCipher.decipher(cipherText, i);
            double newFitness = FitnessCalculator.TetragramFitness(text);
            if(newFitness > output.fitness){output.fitness = newFitness;best_key = i;output.plainText = text;}
        }
        output.isSuccessful = (output.plainText!=null);
        output.key = new ArrayList<>();
        output.key.add(best_key);
        return output;
    }

    public static CipherBreakerOutput<Integer> bruteforceWithCrib(byte[] cipherText, byte[] crib){
        CipherBreakerOutput<Integer> output = new CipherBreakerOutput<>("CaesarCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);

        int bestKey = 0;

        for(int i = 0; i < cipherText.length-crib.length+1; i++){
            int key = (cipherText[i] - crib[0] + 2*Constants.monogramCount)% Constants.monogramCount;
            for(int j = 1; j < crib.length; j++){
                if(((cipherText[i+j] - crib[j] + Constants.monogramCount*2)% Constants.monogramCount) != key){
                    key = -1;
                    break;
                }
            }
            if(key == -1) continue;
            byte[] text = CaesarCipher.decipher(cipherText, key);
            double newFitness = FitnessCalculator.TetragramFitness(text);
            if(newFitness > output.fitness){output.fitness = newFitness;bestKey = key;output.plainText = text;}
        }
        output.isSuccessful = (output.plainText!=null);
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }

    public static CipherBreakerOutput<Integer> bruteforceWithCribs(byte[] cipherText, byte[][] cribs){
        CipherBreakerOutput<Integer> output = new CipherBreakerOutput<>("CaesarCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);

        for (byte[] crib : cribs) {
            CipherBreakerOutput<Integer> outputI = bruteforceWithCrib(cipherText, crib);
            if (outputI.fitness > output.fitness) {
                output.fitness = outputI.fitness;
                output.key = outputI.key;
                output.plainText = outputI.plainText;
            }
        }
        output.isSuccessful = (output.plainText!=null);
        return output;
    }

    public static CipherBreakerOutput<Integer> bruteforceMFC(byte[] cipherText){
        CipherBreakerOutput<Integer> output = new CipherBreakerOutput<>("CaesarCipher", cipherText);
        output.fitness = FitnessCalculator.MonogramChiFitness(cipherText);

        int bestKey = 0;

        for(int i = 1; i < Constants.monogramCount;i++){
            byte[] text = CaesarCipher.decipher(cipherText, i);
            double newFitness = FitnessCalculator.MonogramChiFitness(text);
            if(newFitness > output.fitness){output.fitness = newFitness;bestKey= i;output.plainText = text;}
        }
        output.isSuccessful = (output.plainText!=null);
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }

    public static CipherBreakerOutput<Integer> bruteforceMFA(byte[] cipherText){
        CipherBreakerOutput<Integer> output = new CipherBreakerOutput<>("CaesarCipher", cipherText);
        output.fitness = FitnessCalculator.MonogramABVFitness(cipherText);

        int bestKey = 0;

        for(int i = 1; i < Constants.monogramCount;i++){
            byte[] text = CaesarCipher.decipher(cipherText, i);
            double newFitness = FitnessCalculator.MonogramABVFitness(text);
            if(newFitness > output.fitness){output.fitness = newFitness;bestKey = i;output.plainText = text;}
        }
        output.isSuccessful = (output.plainText!=null);
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }
}
