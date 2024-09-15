package main.breakers;

import main.ciphers.AffineCipher;
import main.utils.FitnessCalculator;

public class AffineCipherBreaker {
    public static CipherBreakerOutput bruteforceTF(String cipherText){
        CipherBreakerOutput output = new CipherBreakerOutput("AffineCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);

        for(int a = 0; a < 26;a++){
            if(AffineCipher.isKeyInvalid(a)) continue;
            for(int b = 0; b < 26;b++){
                String text = AffineCipher.decipher(cipherText, a, b);
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if(newFitness > output.fitness){output.fitness = newFitness;output.key = a+" "+b;output.plainText = text;}
            }
        }
        output.isSuccessfull = (output.plainText!=null);
        return output;
    }

    //TODO: Attack with cribs

    public static CipherBreakerOutput bruteforceMFA(String cipherText){
        CipherBreakerOutput output = new CipherBreakerOutput("AffineCipher", cipherText);
        output.fitness = FitnessCalculator.MonogramABVFitness(cipherText);

        for(int a = 0; a < 26;a++){
            if(AffineCipher.isKeyInvalid(a)) continue;
            for(int b = 0; b < 26;b++){
                String text = AffineCipher.decipher(cipherText, a, b);
                double newFitness = FitnessCalculator.MonogramABVFitness(text);
                if(newFitness > output.fitness){output.fitness = newFitness;output.key = a+" "+b;output.plainText = text;}
            }
        }
        output.isSuccessfull = (output.plainText!=null);
        return output;
    }
}
