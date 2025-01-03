package main.breakers.transposition;

import main.breakers.CipherBreakerOutput;
import main.ciphers.transposition.MatrixTranspositionCipher;
import main.utils.FitnessCalculator;
import main.utils.maths.ModularArithmetics;

import java.util.ArrayList;

/**
 * Class for breaking the Matrix Transposition cipher.
 */
public class MatrixTranspositionCipherBreaker {
    /**
     * Performs a brute-force attack on the Matrix Transposition cipher.
     *
     * @param cipherText The cipher text to break.
     * @return A CipherBreakerOutput object containing the key and plain text.
     */
    public static CipherBreakerOutput<int[]> bruteforce(byte[] cipherText) {
        CipherBreakerOutput<int[]> output = new CipherBreakerOutput<>("MatrixTransposition", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        int[] bestKey = new int[2];

        int[][] factors = ModularArithmetics.findAllFactorPairs(cipherText.length);
        boolean[] widths_checked = new boolean[cipherText.length + 1];
        for (int[] factor : factors) {
            widths_checked[factor[0]] = true;
            byte[] text = MatrixTranspositionCipher.decipher(cipherText, factor[0], factor[1]);
            double newFitness = FitnessCalculator.TetragramFitness(text);
            if (newFitness > output.fitness) {
                output.fitness = newFitness;
                bestKey = factor;
                output.plainText = text;
            }
        }

        for (int width = 2; width < cipherText.length; width++) {
            if (widths_checked[width]) continue;
            int height = (int) Math.ceil((double) cipherText.length / width);
            byte[] text = MatrixTranspositionCipher.decipher(cipherText, width, height);
            double newFitness = FitnessCalculator.TetragramFitness(text);
            if (newFitness > output.fitness) {
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
