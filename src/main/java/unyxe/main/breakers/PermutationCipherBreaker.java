package main.breakers;

import main.ciphers.PermutationCipher;
import main.utils.FitnessCalculator;
import main.utils.maths.Permutations;

import java.util.ArrayList;

public class PermutationCipherBreaker {
    public static CipherBreakerOutput<byte[]> bruteforce(byte[] cipherText, int maxBlockSize){
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("PermutationCipher", cipherText);
        byte[] bestKey = null;
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        for(int i =2; i <=maxBlockSize;i++){
            if(cipherText.length%i !=0)continue;
            byte[][] possiblePermutations = Permutations.generateAllPossiblePermutations(i);
            for(byte[] permutation : possiblePermutations){
                byte[] text = PermutationCipher.decipher(cipherText, permutation);
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if(newFitness > output.fitness){
                    output.fitness = newFitness;
                    output.plainText = text;
                    bestKey = permutation;
                }
            }
        }
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }
}
