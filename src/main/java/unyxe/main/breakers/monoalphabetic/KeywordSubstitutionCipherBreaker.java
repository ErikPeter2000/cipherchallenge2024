package main.breakers.monoalphabetic;

import main.breakers.CipherBreakerOutput;
import main.ciphers.monoalphabetic.KeywordSubstitutionCipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;

import java.util.ArrayList;
import java.util.Arrays;

public class KeywordSubstitutionCipherBreaker {
    public static CipherBreakerOutput<byte[]> wordlistBruteforce(byte[] cipherText) {
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("KeywordSubstitutionCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);

        byte[] bestKeyword = null;
        int bestJ = 0;

        for(int i = 0; i < Constants.wordlist.length; i++){
            byte[] keyword = Constants.wordlist[i];
            for(int j = 0; j < 3; j++){
                byte[] text;
                if(j == 0) {text = KeywordSubstitutionCipher.decipher(cipherText, keyword, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);}
                else if(j == 1) {text = KeywordSubstitutionCipher.decipher(cipherText, keyword, KeywordSubstitutionCipher.KeyFiller.LAST_LETTER, false);}
                else {text = KeywordSubstitutionCipher.decipher(cipherText, keyword, KeywordSubstitutionCipher.KeyFiller.ALPHABETICALLY_LAST_LETTER, false);}
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if(newFitness > output.fitness){
                    output.fitness = newFitness;bestKeyword = Arrays.copyOf(keyword, keyword.length);bestJ = j;output.plainText = text;
                }
            }
        }
        output.isSuccessful = (output.plainText!=null);
        output.key = new ArrayList<>();
        output.key.add(bestKeyword);
        output.key.add(new byte[]{(byte)bestJ});
        return output;
    }
}
