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
    public static byte[] permutationComposition(byte[] permutationA, byte[] permutationB){
        if(permutationA.length != permutationB.length) throw new IllegalArgumentException("Permutations must have equal length to be able to form a composition.");
        byte[] product = new byte[permutationA.length];
        for(int i = 0; i < permutationA.length;i++){
            product[i] = permutationB[permutationA[i]];
        }
        return product;
    }
    public static byte[] permutationInverse(byte[] permutation){
        byte[] inverse = new byte[permutation.length];
        for(int i = 0; i < inverse.length;i++){
            inverse[permutation[i]] = (byte)i;
        }
        return inverse;
    }
}
