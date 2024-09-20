package main.ciphers;

import main.utils.Analyser;
import main.utils.FitnessCalculator;

import java.util.Arrays;

public class TranspositionCipher {
    public static boolean isLikely(byte[] cipherText){
        double ioc = Analyser.getIndexOfCoincedence(cipherText, true);
        double mFrequency = FitnessCalculator.MonogramABVFitness(cipherText);
        System.out.println("[Transposition cipher Identifier] IoC: " + ioc);
        return mFrequency > 0.9;
    }
    public static byte[] appendToPlaintext(byte[] plainText, int period){
        byte[] newText = new byte[(int) (Math.ceil((double) plainText.length /period)*period)];
        Arrays.fill(newText, (byte)23);
        System.arraycopy(plainText, 0, newText, 0, plainText.length);
        return newText;
    }
}