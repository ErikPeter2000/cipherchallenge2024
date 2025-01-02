package main.breakers.periodicpolyalphabetic;

import main.breakers.CipherBreakerOutput;
import main.breakers.monoalphabetic.MonoAlphabeticCipherBreaker;
import main.ciphers.periodicpolyalphabetic.PeriodicPolyAlphabeticSubstitutionCipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;
import main.utils.TextUtilities;
import main.utils.maths.Random;

import java.util.ArrayList;
import java.util.Arrays;

public class PeriodicPolyAlphabeticSubstitutionCipherBreaker {
    public static CipherBreakerOutput<byte[][]> hillClimber(byte[] cipherText, int period, int limit) {
        CipherBreakerOutput<byte[][]> output = new CipherBreakerOutput<>("PeriodicPolyAlphabeticSubstitutionCipher", cipherText);

        int globalCounter = 0;
        double bestFitness = FitnessCalculator.TetragramFitness(cipherText);
        byte[][] bestKey = new byte[period][];
        byte[][] parentKey = initializeKeys(cipherText, period);
        byte[] bestPlaintext = new byte[cipherText.length];
        while (globalCounter < limit * period * period && bestFitness < -9.7) {
            for (int i = 0; i < period; i++) {
                parentKey[i] = MonoAlphabeticCipherBreaker.generateRandomKey();
                byte[] plainText = PeriodicPolyAlphabeticSubstitutionCipher.decipher(cipherText, parentKey);
                double parentFitness = FitnessCalculator.TetragramFitness(plainText);
                int localCounter = 0;
                while (localCounter < 5000) {
                    byte[][] childKey = new byte[period][];
                    for (int j = 0; j < period; j++) {
                        childKey[j] = Arrays.copyOf(parentKey[j], 26);
                    }
                    childKey[i] = MonoAlphabeticCipherBreaker.swapRandomInKey(childKey[i]);
                    byte[] childPlainText = PeriodicPolyAlphabeticSubstitutionCipher.decipher(cipherText, childKey);
                    double childFitness = FitnessCalculator.TetragramFitness(childPlainText);

                    if (childFitness > parentFitness) {
                        for (int j = 0; j < period; j++) {
                            parentKey[j] = Arrays.copyOf(childKey[j], 26);
                        }
                        parentFitness = childFitness;
                        localCounter = 0;
                    }
                    localCounter++;
                    if (childFitness > bestFitness) {
                        bestFitness = childFitness;
                        for (int j = 0; j < period; j++) {
                            bestKey[j] = Arrays.copyOf(childKey[j], 26);
                        }
                        bestPlaintext = childPlainText;
                        globalCounter = 0;
                    }
                    globalCounter++;
                }
            }
            if (globalCounter % 100 < 50)
                System.out.println(globalCounter * 100. / limit / period / period + "% Done. Best fitness: " + bestFitness + " " + TextUtilities.convertToString(bestPlaintext, Constants.alphabet));
        }
        output.fitness = bestFitness;
        output.isSuccessful = true;
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        output.plainText = PeriodicPolyAlphabeticSubstitutionCipher.decipher(cipherText, bestKey);
        return output;
    }

    public static byte[][] initializeKeys(byte[] cipherText, int period) {
        byte[][] parentKey = new byte[period][26];
        for (int i = 0; i < period; i++) {
            parentKey[i] = MonoAlphabeticCipherBreaker.generateRandomKey();
        }
        byte[] buffer = new byte[26];
        double bestFitness = FitnessCalculator.MonogramABVFitness(cipherText);
        for (int c = 0; c < 10000; c++) {
            int rInd = Random.random.nextInt(period);
            byte[] newKey = MonoAlphabeticCipherBreaker.swapRandomInKey(parentKey[rInd]);
            System.arraycopy(parentKey[rInd], 0, buffer, 0, 26);
            System.arraycopy(newKey, 0, parentKey[rInd], 0, 26);
            double newFitness = FitnessCalculator.MonogramABVFitness(PeriodicPolyAlphabeticSubstitutionCipher.decipher(cipherText, parentKey));
            if (newFitness > bestFitness) {
                bestFitness = newFitness;
            } else {
                System.arraycopy(buffer, 0, parentKey[rInd], 0, 26);
            }
        }
        System.out.println("[PPASBreaker] Initial monogram frequency: " + bestFitness);
        return parentKey;
    }
}
