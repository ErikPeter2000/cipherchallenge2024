package main.utils;

import java.util.Arrays;

public class FitnessCalculator {
    public static double MonogramChiFitness(byte[] text){
        return 1/Analyser.ChiSquaredStatistic(Analyser.getMonogramStatistic(text), Constants.monogramStatistics);
    }
    public static double MonogramABVFitness(byte[] text){
        return Analyser.AngleBetweenVectors(Analyser.getMonogramStatistic(text), Constants.monogramStatistics);
    }

    public static double TetragramFitness(byte[] text){
        double fitness = 0.;
        int g = text.length-3;
        byte[][] substrings = new byte[g][];
        for(int i = 0; i < g;i++){
            substrings[i] = Arrays.copyOfRange(text, i, i+4);
        }
        for (int i = 0; i < g; i++) {
            fitness += Constants.getTetragramFrequency(substrings[i]);
        }
        return fitness/g;
    }
}
