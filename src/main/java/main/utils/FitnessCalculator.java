package main.utils;

public class FitnessCalculator {
    public static byte[][] bufferTetragrams = new byte[10000][4];

    public static double MonogramChiFitness(byte[] text) {
        return 1 / Analyser.ChiSquaredStatistic(Analyser.getMonogramStatistic(text), Constants.monogramStatistics);
    }

    public static double MonogramABVFitness(byte[] text) {
        return Analyser.AngleBetweenVectors(Analyser.getMonogramStatistic(text), Constants.monogramStatistics);
    }

    public static double TetragramFitness(byte[] text) {
        double fitness = 0.;
        int g = text.length - 3;
        for (int i = 0; i < g; i++) {
            System.arraycopy(text, i, bufferTetragrams[i], 0, 4);
        }
        for (int i = 0; i < g; i++) {
            fitness += Constants.getTetragramFrequency(bufferTetragrams[i]);
        }
        return fitness / g;
    }
}
