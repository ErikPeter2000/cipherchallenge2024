package main.breakers;

import main.ciphers.PeriodicAffineCipher;
import main.utils.FitnessCalculator;
import main.utils.periodanalysers.IOCPeriodAnalyser;

import java.util.Arrays;

public class PeriodicAffineCipherBreaker {
    public static CipherBreakerOutput monogramFreqAttack(String cipherText, int period){
        CipherBreakerOutput output = new CipherBreakerOutput("PeriodicAffineCipher", cipherText);

        int[][] keys = new int[period][2];
        String[] slices = IOCPeriodAnalyser.splitText(cipherText, period);
        for(int i = 0; i < period; i++){
            CipherBreakerOutput cbo = AffineCipherBreaker.bruteforceMFA(slices[i]);
            keys[i][0] = Integer.parseInt(cbo.key.split(" ")[0]);
            keys[i][1] = Integer.parseInt(cbo.key.split(" ")[1]);
        }

        output.key = Arrays.deepToString(keys);
        output.plainText = PeriodicAffineCipher.decipher(cipherText, keys);

        output.fitness = FitnessCalculator.MonogramABVFitness(output.plainText);
        output.isSuccessfull = true;
        return output;
    }

    public static String[] getVigenereKeys(int[][] keys, boolean checkAgainstWordlist){
        for(int i = 1; i < keys.length; i++){
            if(keys[i][0] != keys[0][0]){
                throw new IllegalArgumentException("Periodic affine key must contain the same multiplier for the vigenere key extractor to work properly.");
            }
        }

        String[] vigenereKeys = new String[keys.length];
        for(int i = 0; i < keys.length; i++){
            StringBuilder key = new StringBuilder();
            for (int[] ints : keys) {
                key.append((char) ((ints[1] - keys[i][1] + 26) % 26 + 65));
            }
            vigenereKeys[i] = key.toString();
        }
        return vigenereKeys;
    }
}
