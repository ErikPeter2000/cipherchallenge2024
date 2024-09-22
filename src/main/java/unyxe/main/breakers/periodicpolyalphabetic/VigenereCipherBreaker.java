package main.breakers.periodicpolyalphabetic;

import main.breakers.CipherBreakerOutput;
import main.breakers.monoalphabetic.CaesarCipherBreaker;
import main.ciphers.periodicpolyalphabetic.VigenereCipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;
import main.utils.periodanalysers.IOCPeriodAnalyser;

import java.util.ArrayList;
import java.util.Arrays;

public class VigenereCipherBreaker {

    public static byte[][] generateKeys(int length){
        byte[][] keys = new byte[(int)Math.pow(Constants.monogramCount, length)][length];
        for(int i = 0; i < keys.length; i++){
            int num = i;
            for(int k = 0; k < length; k++){
                int num1 = (int) (num/Math.pow(Constants.monogramCount, length - k -1));
                if(num1 > 0){
                    num -= (int) (Math.pow(Constants.monogramCount, length - k-1)*num1);
                }
                keys[i][k] = (byte)num1;
            }
        }
        return keys;
    }
    public static CipherBreakerOutput<byte[]> bruteforce(byte[] cipherText, double maxLength){
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("VigenereCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        byte[] bestKey = null;
        for(int n = 1; n <= maxLength; n++){
            byte[][] possibleKeys = generateKeys(n);
            for (byte[] possibleKey : possibleKeys) {
                byte[] text = VigenereCipher.decipher(cipherText, possibleKey);
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if (newFitness > output.fitness) {
                    output.fitness = newFitness;
                    bestKey = Arrays.copyOf(possibleKey, possibleKey.length);
                    output.plainText = text;
                }
            }
            //System.out.println("Key-length " + n + " finished. Best: " + output.plainText);
        }
        output.isSuccessful = (output.plainText!=null);
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }

    //TODO: Attack with cribs

    public static CipherBreakerOutput<byte[]> bruteforceWithWordlist(byte[] cipherText){
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("VigenereCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        byte[] bestKey = null;
        for(int n = 0; n < Constants.wordlist.length; n++){
            byte[] key = Constants.wordlist[n];
            byte[] text = VigenereCipher.decipher(cipherText, key);
            double newFitness = FitnessCalculator.TetragramFitness(text);
            if (newFitness > output.fitness) {
                output.fitness = newFitness;
                bestKey = Arrays.copyOf(key, key.length);
                output.plainText = text;
            }
        }
        output.isSuccessful = (output.plainText!=null);
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }
    public static CipherBreakerOutput<byte[]> hillClimberAttack(byte[] cipherText, int period){
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("VigenereCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);

        byte[] bestKey;

        byte[] key = new byte[period];
        while(true){
            double oldFitness = output.fitness;
            for(int i = 0; i < period;i++){
                double maxFitness = -9999999;
                byte bestLetter = key[i];
                for(int j = 0; j < Constants.monogramCount; j++){
                    key[i] = (byte)(j);
                    byte[] text = VigenereCipher.decipher(cipherText, key);
                    double newFitness = FitnessCalculator.TetragramFitness(text);
                    if (newFitness > maxFitness) {
                        maxFitness = newFitness;
                        bestLetter = (byte)(j);
                    }
                }
                key[i] = bestLetter;
            }
            bestKey = Arrays.copyOf(key, key.length);
            output.plainText = VigenereCipher.decipher(cipherText, key);
            output.fitness = FitnessCalculator.TetragramFitness(output.plainText);
            if(output.fitness == oldFitness){break;}
        }
        output.isSuccessful = true;
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }

    public static CipherBreakerOutput<byte[]> monogramFreqAttack(byte[] cipherText, int period){
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("VigenereCipher", cipherText);
        output.fitness = FitnessCalculator.MonogramABVFitness(cipherText);
        byte[][] slices = IOCPeriodAnalyser.splitText(cipherText, period);

        byte[] key = new byte[period];
        for(int i = 0; i < period; i++){
            CipherBreakerOutput<Integer> cbo = CaesarCipherBreaker.bruteforceMFA(slices[i]);
            key[i] = (byte) (int)(cbo.key.get(0));
        }

        output.key = new ArrayList<>();
        output.key.add(key);
        output.plainText = VigenereCipher.decipher(cipherText, key);
        output.fitness = FitnessCalculator.MonogramABVFitness(output.plainText);
        output.isSuccessful = true;
        return output;
    }

}
