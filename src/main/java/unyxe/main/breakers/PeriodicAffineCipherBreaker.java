package main.breakers;

import main.ciphers.PeriodicAffineCipher;
import main.utils.FitnessCalculator;
import main.utils.periodanalysers.IOCPeriodAnalyser;

import java.util.ArrayList;

public class PeriodicAffineCipherBreaker {
    public static CipherBreakerOutput<int[][]> monogramFreqAttack(byte[] cipherText, int period){
        CipherBreakerOutput<int[][]> output = new CipherBreakerOutput<>("PeriodicAffineCipher", cipherText);

        int[][] keys = new int[period][2];
        byte[][] slices = IOCPeriodAnalyser.splitText(cipherText, period);
        for(int i = 0; i < period; i++){
            CipherBreakerOutput<Integer> cbo = AffineCipherBreaker.bruteforceMFA(slices[i]);
            keys[i][0] = cbo.key.get(0);
            keys[i][1] = cbo.key.get(1);
        }

        output.key = new ArrayList<>();
        output.key.add(keys);
        output.plainText = PeriodicAffineCipher.decipher(cipherText, keys);

        output.fitness = FitnessCalculator.MonogramABVFitness(output.plainText);
        output.isSuccessful = true;
        return output;
    }
}
