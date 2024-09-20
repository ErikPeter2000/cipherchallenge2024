package main.breakers;

import main.ciphers.PermutationCipher;
import main.utils.FitnessCalculator;
import main.utils.maths.Permutations;
import main.utils.maths.Random;

import java.util.ArrayList;

public class PermutationCipherBreaker {
    public static CipherBreakerOutput<byte[]> bruteforce(byte[] cipherText, int maxBlockSize, boolean checkDivisibility){
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("PermutationCipher", cipherText);
        byte[] bestKey = null;
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        for(int i =2; i <=maxBlockSize;i++){
            if(cipherText.length%i !=0 && checkDivisibility)continue;
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

    public static CipherBreakerOutput<byte[]> hillClimberAttack(byte[] cipherText, int blockLength, double marginOfError, double probabilityOfDescent){
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("PermutationCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        byte[] bestKey;
        byte[] parentKey = Permutations.getRandomPermutation(blockLength);
        int counter = 0;
        while(counter < 100*blockLength){
            byte[] childKey;
            if(Random.random.nextBoolean()){
                childKey = Permutations.swapTwoRandomDigits(parentKey);
            }else{
                childKey = Permutations.rollPermutationRandomly(parentKey);
            }
            byte[] text = PermutationCipher.decipher(cipherText, childKey);
            double newFitness = FitnessCalculator.TetragramFitness(text);
            if(newFitness > output.fitness || (newFitness > output.fitness-marginOfError && Random.random.nextDouble()<probabilityOfDescent)){
                parentKey = childKey;
                output.fitness = newFitness;
                output.plainText = text;
                counter = 0;
            }
            counter++;
        }
        bestKey = parentKey;
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }
    public static CipherBreakerOutput<byte[]> hillClimberAttack(byte[] cipherText, int blockLength){
        return hillClimberAttack(cipherText, blockLength, 0.15, 0.05);
    }
    public static CipherBreakerOutput<byte[]> bruteforceBlockSizeUsingHillClimb(byte[] cipherText, int maxBlockSize, double marginOfError, double probabilityOfDescent){
        CipherBreakerOutput<byte[]> bestCBO= hillClimberAttack(cipherText, 2, marginOfError, probabilityOfDescent);
        for(int i = 3; i <= maxBlockSize;i++){
            CipherBreakerOutput<byte[]> CBO = hillClimberAttack(cipherText, i, marginOfError, probabilityOfDescent);
            if(CBO.fitness > bestCBO.fitness){
                bestCBO = CBO;
            }
        }
        return bestCBO;
    }
    public static CipherBreakerOutput<byte[]> bruteforceBlockSizeUsingHillClimb(byte[] cipherText, int maxBlockSize){
        return bruteforceBlockSizeUsingHillClimb(cipherText, maxBlockSize, 0.15, 0.05);
    }
}
