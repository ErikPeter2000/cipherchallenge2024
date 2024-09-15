package main.breakers;

import main.ciphers.MonoAlphabeticCipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;

import java.util.Arrays;
import java.util.Random;

public class MonoAlphabeticCipherBreaker {
    static Random rand = new Random();
    public static String swapRandomInKey(String key){
        int x = rand.nextInt(key.length());
        int y;
        do{
            y = rand.nextInt(key.length());
        }while(y==x);
        if(x > y){
            int temp = x;
            x = y;
            y = temp;
        }
        return key.substring(0, x) + key.charAt(y) + key.substring(x+1, y) + key.charAt(x) + key.substring(y+1);
    }
    public static String generateRandomKey(){
        String randomKey = Constants.alphabet;
        for(int i = 0; i < 1000;i++){
            randomKey = swapRandomInKey(randomKey);
        }
        return randomKey;
    }


    public static CipherBreakerOutput stochasticHillClimbingAttack(String cipherText){
        CipherBreakerOutput output = new CipherBreakerOutput("MonoAlphabeticCipher", cipherText);
        int limit = 10000;
        String parentKey = generateRandomKey();
        output.fitness = FitnessCalculator.TetragramFitness(MonoAlphabeticCipher.decipher(cipherText, parentKey));
        String childKey = parentKey;
        for(int i = 0 ; i < limit ; i++){
            childKey = swapRandomInKey(childKey);
            String text = MonoAlphabeticCipher.decipher(cipherText, childKey);
            double newFitness = FitnessCalculator.TetragramFitness(text);
            if(newFitness > output.fitness){output.fitness = newFitness;output.key = childKey;output.plainText = text;i=0;}
        }
        output.isSuccessfull = (output.plainText!=null);
        return output;
    }

    //Not as consistent
    public static CipherBreakerOutput evolutionaryHillClimbingAttack(String cipherText, int genLimit, int keysPerGen){
        CipherBreakerOutput output = new CipherBreakerOutput("MonoAlphabeticCipher", cipherText);
        String parentKey = generateRandomKey();
        output.fitness = FitnessCalculator.TetragramFitness(MonoAlphabeticCipher.decipher(cipherText, parentKey));
        for(int i = 0 ; i < genLimit; i++){
            for(int j = 0; j < keysPerGen; j++){
                String childKey = swapRandomInKey(parentKey);
                String text = MonoAlphabeticCipher.decipher(cipherText, childKey);
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if(newFitness > output.fitness){output.fitness = newFitness;output.key = childKey;output.plainText = text;}
            }
            parentKey = output.key;
        }
        output.isSuccessfull = (output.plainText!=null);
        return output;
    }

    public static CipherBreakerOutput evolutionaryAdvancedHillClimbingAttack(String cipherText, int genLimit, int keysPerGen){
        CipherBreakerOutput output = new CipherBreakerOutput("MonoAlphabeticCipher", cipherText);
        output.fitness = -9999999;
        String[] generation = new String[keysPerGen];
        for(int i = 0; i < keysPerGen/10; i++){
            generation[i] = generateRandomKey();
        }
        for(int i = 0 ; i < genLimit; i++){
            for(int j = keysPerGen/10; j < keysPerGen; j++){
                generation[j] = swapRandomInKey(generation[j/10 - 1]);
            }
            double[] topFitnesses = new double[keysPerGen/10];
            String[] topKeys = new String[keysPerGen/10];
            Arrays.fill(topFitnesses, -9999999);
            int lowestIndex = 0;
            for(int j = 0; j < keysPerGen; j++){
                String text = MonoAlphabeticCipher.decipher(cipherText, generation[j]);
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if(newFitness > output.fitness){output.fitness = newFitness;output.key = generation[j];output.plainText = text;}
                if(newFitness > topFitnesses[lowestIndex]){
                    topFitnesses[lowestIndex] = newFitness;
                    topKeys[lowestIndex] = generation[j];
                    double lowestFitness = 9999999;
                    for(int h = 0; h < topFitnesses.length; h++){
                        if(topFitnesses[h] == -9999999){
                            lowestIndex = h;
                            break;
                        }
                        if(topFitnesses[h] < lowestFitness){
                            lowestIndex = h;
                            lowestFitness = topFitnesses[h];
                        }
                    }
                }
            }
            System.arraycopy(topKeys, 0, generation, 0, topFitnesses.length);
        }
        output.isSuccessfull = (output.plainText!=null);
        return output;
    }
}
