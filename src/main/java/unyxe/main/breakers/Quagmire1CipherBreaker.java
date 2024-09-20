package main.breakers;

import main.ciphers.CaesarCipher;
import main.ciphers.MonoAlphabeticCipher;
import main.ciphers.Quagmire1Cipher;
import main.ciphers.VigenereCipher;
import main.utils.Analyser;
import main.utils.Constants;
import main.utils.FitnessCalculator;
import main.utils.TextUtilities;
import main.utils.periodanalysers.IOCPeriodAnalyser;

import java.util.ArrayList;
import java.util.Arrays;

public class Quagmire1CipherBreaker {

    public static boolean checkTheKeyword(byte[] keyword){
        for(int i = 0; i < Constants.wordlist.length; i++){
            if(TextUtilities.isEqual(keyword, Constants.wordlist[i])){return true;}
        }
        return false;
    }

    public static CipherBreakerOutput<byte[]> dictionaryAttack(byte[] cipherText, int alphabetKeyLength, int shiftsKeyLength){
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("Quagmire1Cipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        byte[] bestAKey = null;
        byte[] bestSKey = null;
        byte[][] alphabetKeyWordlist = Constants.smallWordlistSplitted[alphabetKeyLength];
        byte[][] shiftsKeyWordlist = Constants.smallWordlistSplitted[shiftsKeyLength];
        int n = 0;
        for (byte[] alphabetKey : alphabetKeyWordlist) {
            for (byte[] shiftsKey : shiftsKeyWordlist) {
                byte[] text = Quagmire1Cipher.decipher(cipherText, alphabetKey, shiftsKey);
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if (newFitness > output.fitness) {
                    output.fitness = newFitness;
                    bestAKey = Arrays.copyOf(alphabetKey, alphabetKey.length);
                    bestSKey = Arrays.copyOf(shiftsKey, shiftsKey.length);
                    output.plainText = text;
                }
            }
            if(n%100 == 0)System.out.println(n*100./alphabetKeyWordlist.length + "% done.");
            n++;
        }
        output.isSuccessful = (output.plainText!=null);
        output.key = new ArrayList<>();
        output.key.add(bestAKey);
        output.key.add(bestSKey);
        return output;
    }

    static void shiftLeft(double[] array){
        double temp = array[0];
        for(int i = 1; i < array.length; i++){
            array[i-1] = array[i];
        }
        array[array.length-1] = temp;
    }

    public static CipherBreakerOutput<byte[]> twoStageAttack(byte[] cipherText, int period){
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("Quagmire1Cipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);

        byte[][] slices = IOCPeriodAnalyser.splitText(cipherText, period);
        double[] pivotMonogramFreq = Analyser.getMonogramStatistic(slices[0]);
        int[] shifts = new int[slices.length];
        for(int i = 1; i < slices.length; i++){
            double[] monogramFreq = Analyser.getMonogramStatistic(slices[i]);
            int bestShift = 0;
            double bestMatch = Analyser.AngleBetweenVectors(pivotMonogramFreq, monogramFreq);
            for(int j = 1; j < 26;j++){
                shiftLeft(monogramFreq);
                double match = Analyser.AngleBetweenVectors(pivotMonogramFreq, monogramFreq);
                if(match > bestMatch){
                    bestShift = j;
                    bestMatch = match;
                }
            }
            shifts[i] = bestShift;
        }
        shifts[0] = 0;
        byte[] vigenereKey = new byte[shifts.length];
        for (int i = 0; i < shifts.length; i++) {
            vigenereKey[i] = (byte)shifts[i];
        }

        boolean found = false;
        for(int i = 1; i < 26;i++){
            byte[] cKey = CaesarCipher.encipher(vigenereKey, i);
            if(checkTheKeyword(cKey)){
                vigenereKey = cKey;
                found = true;
                //System.out.println("[Quagmire1CipherBreaker] Shifts keyword found: " + cKey);
                break;
            }
        }
        if(!found) System.out.println("[Quagmire1CipherBreaker] Shifts keyword not found.");

        byte[] substitutionCipher = VigenereCipher.decipher(cipherText, vigenereKey);
        CipherBreakerOutput<byte[]> cbo = MonoAlphabeticCipherBreaker.evolutionaryHillClimbingAttack(substitutionCipher, 100, 400);

        byte[] alphabetKeyword = MonoAlphabeticCipher.inverseKey(cbo.key.get(0));

        output.fitness = cbo.fitness;
        output.key = new ArrayList<>();
        output.key.add(alphabetKeyword);
        output.key.add(vigenereKey);
        output.plainText = cbo.plainText;

        output.isSuccessful = (output.plainText!=null);
        return output;
    }
}
