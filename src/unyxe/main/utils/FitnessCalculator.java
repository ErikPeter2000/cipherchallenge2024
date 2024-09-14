package main.utils;

public class FitnessCalculator {
    public static double MonogramChiFitness(String text){
        return 1/Analyser.ChiSquaredStatistic(Analyser.getMonogramStatistic(text), Constants.monogramStatistics);
    }
    public static double MonogramABVFitness(String text){
        return Analyser.AngleBetweenVectors(Analyser.getMonogramStatistic(text), Constants.monogramStatistics);
    }
    public static double TetragramFitness(String text){
        double fitness = 0;
        for(int i = 0; i < text.length()-3; i++){
            Double freq = Constants.tetragramMap.get(text.substring(i, i+4));
            if(freq == null) return Double.MIN_VALUE;
            fitness += Math.log(freq);
        }
        return fitness/(text.length()-3);
    }
}
