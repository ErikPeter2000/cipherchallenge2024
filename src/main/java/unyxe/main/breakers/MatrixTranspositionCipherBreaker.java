package main.breakers;

import main.ciphers.MatrixTranspositionCipher;
import main.utils.FitnessCalculator;
import main.utils.maths.ModularArithmetics;

import java.util.ArrayList;

public class MatrixTranspositionCipherBreaker {
    public static CipherBreakerOutput<int[]> bruteforce(byte[] cipherText){
        CipherBreakerOutput<int[]> output = new CipherBreakerOutput<>("MatrixTransposition", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        int[] bestKey = new int[2];

        int[][] factors = ModularArithmetics.findAllFactorPairs(cipherText.length);
        boolean[] widths_checked = new boolean[cipherText.length+1];
        for(int i = 0; i < factors.length;i++){
            widths_checked[factors[i][0]] = true;
            byte[] text = MatrixTranspositionCipher.decipher(cipherText, factors[i][0], factors[i][1]);
            double newFitness = FitnessCalculator.TetragramFitness(text);
            if(newFitness > output.fitness){
                output.fitness = newFitness;
                bestKey = factors[i];
                output.plainText = text;
            }
        }

        for(int width = 2; width < cipherText.length;width++){
            if(widths_checked[width])continue;
            int height = (int)Math.ceil((double) cipherText.length /width);
            byte[] text = MatrixTranspositionCipher.decipher(cipherText, width, height);
            double newFitness = FitnessCalculator.TetragramFitness(text);
            if(newFitness > output.fitness){
                output.fitness = newFitness;
                bestKey = new int[]{width, height};
                output.plainText = text;
            }
        }
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }
}
