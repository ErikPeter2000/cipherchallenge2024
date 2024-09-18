package main.breakers;

import main.ciphers.Quagmire3Cipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;

import java.util.ArrayList;
import java.util.Arrays;

public class Quagmire3CipherBreaker {
    public static CipherBreakerOutput<byte[]> dictionaryAttack(byte[] cipherText, int alphabetKeyLength, int shiftsKeyLength){
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("Quagmire3Cipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        byte[] bestAKey = null;
        byte[] bestSKey = null;
        byte[][] alphabetKeyWordlist = Constants.smallWordlistSplitted[alphabetKeyLength];
        byte[][] shiftsKeyWordlist = Constants.smallWordlistSplitted[shiftsKeyLength];
        int n = 0;
        for (byte[] alphabetKey : alphabetKeyWordlist) {
            for (byte[] shiftsKey : shiftsKeyWordlist) {
                byte[] text = Quagmire3Cipher.decipher(cipherText, alphabetKey, shiftsKey);
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if (newFitness > output.fitness) {
                    output.fitness = newFitness;
                    bestAKey = Arrays.copyOf(alphabetKey, alphabetKey.length);
                    bestSKey = Arrays.copyOf(shiftsKey, shiftsKey.length);
                    output.plainText = text;
                }
            }
            if(n%10 == 0)System.out.println(n*100./alphabetKeyWordlist.length + "% done.");
            n++;
        }
        output.isSuccessful = (output.plainText!=null);
        output.key = new ArrayList<>();
        output.key.add(bestAKey);
        output.key.add(bestSKey);
        return output;
    }
}
