package main.breakers;

import main.ciphers.Quagmire2Cipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;

public class Quagmire2CipherBreaker {
    public static CipherBreakerOutput dictionaryAttack(String cipherText, int alphabetKeyLength, int shiftsKeyLength){
        CipherBreakerOutput output = new CipherBreakerOutput("Quagmire2Cipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        String[] alphabetKeyWordlist = Constants.smallWordlistSplitted[alphabetKeyLength];
        String[] shiftsKeyWordlist = Constants.smallWordlistSplitted[shiftsKeyLength];
        int n = 0;
        for (String alphabetKey : alphabetKeyWordlist) {
            for (String shiftsKey : shiftsKeyWordlist) {
                String text = Quagmire2Cipher.decipher(cipherText, alphabetKey, shiftsKey);
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if (newFitness > output.fitness) {
                    output.fitness = newFitness;
                    output.key = alphabetKey + " " + shiftsKey;
                    output.plainText = text;
                }
            }
            if(n%10 == 0)System.out.println(n*100./alphabetKeyWordlist.length + "% done.");
            n++;
        }
        output.isSuccessfull = (output.plainText!=null);
        return output;
    }
}
