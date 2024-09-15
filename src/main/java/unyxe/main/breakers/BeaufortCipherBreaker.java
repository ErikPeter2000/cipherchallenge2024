package main.breakers;

import main.ciphers.BeaufortCipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;

public class BeaufortCipherBreaker {
    public static CipherBreakerOutput bruteforce(String cipherText, double maxLength){
        CipherBreakerOutput output = new CipherBreakerOutput("BeaufortCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        for(int n = 1; n <= maxLength; n++){
            String[] possibleKeys = VigenereCipherBreaker.generateKeys(n);
            for (String possibleKey : possibleKeys) {
                String text = BeaufortCipher.decipher(cipherText, possibleKey);
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if (newFitness > output.fitness) {
                    output.fitness = newFitness;
                    output.key = possibleKey;
                    output.plainText = text;
                }
            }
            System.out.println("Key-length " + n + " finished. Best: " + output.plainText);
        }
        output.isSuccessfull = (output.plainText!=null);
        return output;
    }

    public static CipherBreakerOutput bruteforceWithWordlist(String cipherText){
        CipherBreakerOutput output = new CipherBreakerOutput("BeaufortCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        for(int n = 0; n < Constants.wordlist.length; n++){
            String key = Constants.wordlist[n];
            String text = BeaufortCipher.decipher(cipherText, key);
            double newFitness = FitnessCalculator.TetragramFitness(text);
            if (newFitness > output.fitness) {
                output.fitness = newFitness;
                output.key = key;
                output.plainText = text;
            }
        }
        output.isSuccessfull = (output.plainText!=null);
        return output;
    }
    public static CipherBreakerOutput hillClimberAttack(String cipherText, int period){
        CipherBreakerOutput output = new CipherBreakerOutput("BeaufortCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);


        char[] key = "A".repeat(period).toCharArray();
        while(true){
            double oldFitness = output.fitness;
            for(int i = 0; i < period;i++){
                double maxFitness = -999999;
                char bestLetter = key[i];
                for(int j = 0; j < Constants.monogramCount; j++){
                    key[i] = (char)(65+j);
                    String keyString = new String(key);
                    String text = BeaufortCipher.decipher(cipherText, keyString);
                    double newFitness = FitnessCalculator.TetragramFitness(text);
                    if (newFitness > maxFitness) {
                        maxFitness = newFitness;
                        bestLetter = (char)(65+j);
                    }
                }
                key[i] = bestLetter;
            }
            output.key = new String(key);
            output.plainText = BeaufortCipher.decipher(cipherText, output.key);
            output.fitness = FitnessCalculator.TetragramFitness(output.plainText);
            if(output.fitness == oldFitness){break;}
        }
        output.isSuccessfull = true;
        return output;
    }
}
