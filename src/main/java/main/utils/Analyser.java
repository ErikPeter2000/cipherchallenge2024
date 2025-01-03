package main.utils;

/**
 * Analyser class is used to calculate the monogram statistic, index of coincidence, entropy, chi-squared statistic and angle between vectors.
 */
public class Analyser {
    /**
     * getMonogramStatistic method is used to calculate the monogram statistic of the given text.
     *
     * @param text byte array of the text.
     * @return double array of the monogram statistic.
     */
    public static double[] getMonogramStatistic(byte[] text) {
        double[] monogramStatistic = new double[Constants.monogramCount];
        int N = text.length;
        for (byte b : text) {
            monogramStatistic[b] += 1.0 / N;
        }
        return monogramStatistic;
    }

    /**
     * getIndexOfCoincedence method is used to calculate the index of coincidence of the given text.
     *
     * @param text       byte array of the text.
     * @param normalized boolean value to normalize the index of coincidence.
     * @return double value of the index of coincidence.
     */
    public static double getIndexOfCoincedence(byte[] text, boolean normalized) {
        double ioc = 0;
        double[] monogramStatistic = getMonogramStatistic(text);
        int N = text.length;
        for (int i = 0; i < Constants.monogramCount; i++) {
            ioc += monogramStatistic[i] * (monogramStatistic[i] * N - 1) / (N - 1);
        }
        if (normalized) {
            ioc *= Constants.monogramCount;
        }
        return ioc;
    }

    /**
     * getEntropy method is used to calculate the entropy of the given text.
     *
     * @param text byte array of the text.
     * @return double value of the entropy.
     */
    public static double getEntropy(byte[] text) {
        double[] monogramStatistic = getMonogramStatistic(text);
        double entropy = 0;
        for (int i = 0; i < Constants.monogramCount; i++) {
            if (monogramStatistic[i] == 0) continue;
            entropy -= monogramStatistic[i] * Math.log(monogramStatistic[i]) / Math.log(Constants.monogramCount);
        }
        return entropy;
    }

    /**
     * ChiSquaredStatistic method is used to calculate the chi-squared statistic of the given measured and expected values.
     *
     * @param measured double array of the measured values.
     * @param expected double array of the expected values.
     * @return double value of the chi-squared statistic.
     */
    public static double ChiSquaredStatistic(double[] measured, double[] expected) {
        double chiSquared = 0;
        for (int i = 0; i < measured.length; i++) {
            chiSquared += Math.pow(measured[i] - expected[i], 2) / expected[i];
        }
        return chiSquared;
    }

    /**
     * AngleBetweenVectors method is used to calculate the angle between two vectors.
     *
     * @param vector1 double array of the first vector.
     * @param vector2 double array of the second vector.
     * @return double value of the angle between the vectors.
     */
    public static double AngleBetweenVectors(double[] vector1, double[] vector2) {
        return DotProduct(vector1, vector2) / Math.sqrt(DotProduct(vector1, vector1) * DotProduct(vector2, vector2));
    }

    /**
     * DotProduct method is used to calculate the dot product of two vectors.
     *
     * @param vector1 double array of the first vector.
     * @param vector2 double array of the second vector.
     * @return double value of the dot product.
     */
    static double DotProduct(double[] vector1, double[] vector2) {
        double dotProduct = 0;
        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
        }
        return dotProduct;
    }
}
