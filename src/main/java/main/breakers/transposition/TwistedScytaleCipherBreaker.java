package main.breakers.transposition;

import main.breakers.CipherBreakerOutput;
import main.ciphers.transposition.TwistedScytaleCipher;
import main.utils.FitnessCalculator;
import main.utils.maths.ModularArithmetics;

import java.util.ArrayList;

public class TwistedScytaleCipherBreaker {
    public static CipherBreakerOutput<int[]> bruteforce(byte[] cipherText) {
        CipherBreakerOutput<int[]> output = new CipherBreakerOutput<>("TwistedScytale", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        int[] bestKey = new int[3];

        int[][] factors = ModularArithmetics.findAllFactorPairs(cipherText.length);
        for (int[] factor : factors) {
            for (int j = 0; j < factor[0]; j++) {
                byte[] text = TwistedScytaleCipher.decipher(cipherText, factor[0], j);
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if (newFitness > output.fitness) {
                    output.fitness = newFitness;
                    bestKey[0] = factor[0];
                    bestKey[1] = factor[1];
                    bestKey[2] = j;
                    output.plainText = text;
                }
            }
        }
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }
}
