package main.breakers;

import main.ciphers.CaesarCipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;

public class CaesarCipherBreaker {
    public static CipherBreakerOutput bruteforceTF(String cipherText){
        CipherBreakerOutput output = new CipherBreakerOutput("CeaserCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        for(int i = 1; i < Constants.monogramCount;i++){
            String text = CaesarCipher.decipher(cipherText, i);
            double newFitness = FitnessCalculator.TetragramFitness(text);
            if(newFitness > output.fitness){output.fitness = newFitness;output.key = "" + i;output.plainText = text;}
        }
        output.isSuccessfull = (output.plainText!=null);
        return output;
    }

    public static CipherBreakerOutput bruteforceWithCrib(String cipherText, String crib){
        CipherBreakerOutput output = new CipherBreakerOutput("CeaserCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);

        for(int i = 0; i < cipherText.length()-crib.length()+1; i++){
            String testedSegment = cipherText.substring(i, i+crib.length());
            int targetValue = cipherText.charAt(i) - crib.charAt(0);
            int key = Math.floorMod(targetValue, Constants.monogramCount);
            for(int j = 1; j < testedSegment.length(); j++){
                if(Math.floorMod(cipherText.charAt(i+j) - crib.charAt(j), Constants.monogramCount) != key){
                    key = -1;
                    break;
                }
            }
            if(key == -1) continue;
            String text = CaesarCipher.decipher(cipherText, key);
            double newFitness = FitnessCalculator.TetragramFitness(text);
            if(newFitness > output.fitness){output.fitness = newFitness;output.key = "" + key;output.plainText = text;}
        }
        output.isSuccessfull = (output.plainText!=null);
        return output;
    }

    public static CipherBreakerOutput bruteforceWithCribs(String cipherText, String[] cribs){
        CipherBreakerOutput output = new CipherBreakerOutput("CeaserCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);

        for (String crib : cribs) {
            CipherBreakerOutput outputI = bruteforceWithCrib(cipherText, crib);
            if (outputI.fitness > output.fitness) {
                output.fitness = outputI.fitness;
                output.key = outputI.key;
                output.plainText = outputI.plainText;
            }
        }
        output.isSuccessfull = (output.plainText!=null);
        return output;
    }

    public static CipherBreakerOutput bruteforceMFC(String cipherText){
        CipherBreakerOutput output = new CipherBreakerOutput("CeaserCipher", cipherText);
        output.fitness = FitnessCalculator.MonogramChiFitness(cipherText);
        for(int i = 1; i < Constants.monogramCount;i++){
            String text = CaesarCipher.decipher(cipherText, i);
            double newFitness = FitnessCalculator.MonogramChiFitness(text);
            if(newFitness > output.fitness){output.fitness = newFitness;output.key = "" + i;output.plainText = text;}
        }
        output.isSuccessfull = (output.plainText!=null);
        return output;
    }

    public static CipherBreakerOutput bruteforceMFA(String cipherText){
        CipherBreakerOutput output = new CipherBreakerOutput("CeaserCipher", cipherText);
        output.fitness = FitnessCalculator.MonogramABVFitness(cipherText);
        for(int i = 1; i < Constants.monogramCount;i++){
            String text = CaesarCipher.decipher(cipherText, i);
            double newFitness = FitnessCalculator.MonogramABVFitness(text);
            if(newFitness > output.fitness){output.fitness = newFitness;output.key = "" + i;output.plainText = text;}
        }
        output.isSuccessfull = (output.plainText!=null);
        return output;
    }
}
