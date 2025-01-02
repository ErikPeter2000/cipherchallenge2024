package main.utils;

public class Analyser {
    public static double[] getMonogramStatistic(byte[] text) {
        double[] monogramStatistic = new double[Constants.monogramCount];
        int N = text.length;
        for (byte b : text) {
            monogramStatistic[b] += 1.0 / N;
        }
        return monogramStatistic;
    }

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

    public static double getEntropy(byte[] text) {
        double[] monogramStatistic = getMonogramStatistic(text);
        double entropy = 0;
        for (int i = 0; i < Constants.monogramCount; i++) {
            if (monogramStatistic[i] == 0) continue;
            entropy -= monogramStatistic[i] * Math.log(monogramStatistic[i]) / Math.log(Constants.monogramCount);
        }
        return entropy;
    }


    public static double ChiSquaredStatistic(double[] measured, double[] expected) {
        double chiSquared = 0;
        for (int i = 0; i < measured.length; i++) {
            chiSquared += Math.pow(measured[i] - expected[i], 2) / expected[i];
        }
        return chiSquared;
    }

    public static double AngleBetweenVectors(double[] vector1, double[] vector2) {
        return DotProduct(vector1, vector2) / Math.sqrt(DotProduct(vector1, vector1) * DotProduct(vector2, vector2));
    }

    static double DotProduct(double[] vector1, double[] vector2) {
        double dotProduct = 0;
        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
        }
        return dotProduct;
    }
}
