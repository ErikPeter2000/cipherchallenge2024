package main.utils;

public class FitnessCalculator {
    public static double MonogramChiFitness(String text){
        return 1/Analyser.ChiSquaredStatistic(Analyser.getMonogramStatistic(text), Constants.monogramStatistics);
    }
    public static double MonogramABVFitness(String text){
        return Analyser.AngleBetweenVectors(Analyser.getMonogramStatistic(text), Constants.monogramStatistics);
    }
    /*
    public static double TetragramFitnessOld(String text){
        double fitness = 0;
        for(int i = 0; i < text.length()-3; i++){
            String sub = text.substring(i, i+4);
            if(!Constants.tetragramMap.containsKey(sub)){
                fitness -= 20;
                continue;
            }
            Double freq = Constants.tetragramMap.get(sub);
            fitness += freq;
        }
        return fitness/(text.length()-3);
    }
    */

    public static double TetragramFitness(String text){
        double fitness = 0.;
        int g = text.length()-3;
        for(int i = 0; i < g; i++) {
            fitness = fitness + Constants.getTetragramFrequency(text.substring(i, i+4));
        }
        return fitness/(text.length()-3);
    }
}
