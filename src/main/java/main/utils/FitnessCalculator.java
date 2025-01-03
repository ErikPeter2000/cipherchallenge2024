package main.utils;

/**
 * FitnessCalculator class is used to calculate the fitness of a given text using various fitness functions.
 */
public class FitnessCalculator {
    /**
     * bufferTetragrams is a buffer to store tetragrams of a given text.
     */
    public static byte[][] bufferTetragrams = new byte[10000][4];

    /**
     * This method calculates the fitness of a given text using the monogram chi fitness function.
     * @param text The text for which the fitness is to be calculated.
     * @return The fitness of the given text.
     */
    public static double MonogramChiFitness(byte[] text) {
        return 1 / Analyser.ChiSquaredStatistic(Analyser.getMonogramStatistic(text), Constants.monogramStatistics);
    }

    /**
     * This method calculates the fitness of a given text using the angle between vectors fitness function.
     * @param text The text for which the fitness is to be calculated.
     * @return The fitness of the given text.
     */
    public static double MonogramABVFitness(byte[] text) {
        return Analyser.AngleBetweenVectors(Analyser.getMonogramStatistic(text), Constants.monogramStatistics);
    }

    /**
     * This method calculates the fitness of a given text using the tetragram fitness function.
     * @param text The text for which the fitness is to be calculated.
     * @return The fitness of the given text.
     */
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
