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
}
