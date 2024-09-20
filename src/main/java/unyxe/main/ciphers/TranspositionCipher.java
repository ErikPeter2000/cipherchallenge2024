package main.ciphers;

import main.utils.Analyser;
import main.utils.FitnessCalculator;

public class TranspositionCipher {
    public static boolean isLikely(byte[] cipherText){
        double ioc = Analyser.getIndexOfCoincedence(cipherText, true);
        double mFrequency = FitnessCalculator.MonogramABVFitness(cipherText);
        System.out.println("[Transposition cipher Identifier] IoC: " + ioc);
        return mFrequency > 0.9;
    }
}
