package main.breakers;

import main.ciphers.MonoAlphabeticCipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;

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
            System.out.println("Gen " + i + " finished. " + output.plainText);
            parentKey = output.key;
        }
        output.isSuccessfull = (output.plainText!=null);
        return output;
    }
}
