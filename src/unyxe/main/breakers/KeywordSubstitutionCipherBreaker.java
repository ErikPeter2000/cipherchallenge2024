package main.breakers;

import main.ciphers.KeywordSubstitutionCipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;

import java.util.Objects;

public class KeywordSubstitutionCipherBreaker {
    public static CipherBreakerOutput wordlistBruteforce(String cipherText) {
        CipherBreakerOutput output = new CipherBreakerOutput("KeywordSubstitutionCipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        System.out.println(Constants.wordlist.length);
        for(int i = 0; i < Constants.wordlist.length; i++){
            String keyword = Constants.wordlist[i];
            if(Objects.equals(keyword, "AAHS")){
                System.out.println("AAHS");
            }
            for(int j = 0; j < 3; j++){
                String text;
                if(j == 0) {text = KeywordSubstitutionCipher.decipher(cipherText, keyword, KeywordSubstitutionCipher.KeyFiller.NORMAL, false);}
                else if(j == 1) {text = KeywordSubstitutionCipher.decipher(cipherText, keyword, KeywordSubstitutionCipher.KeyFiller.LAST_LETTER, false);}
                else {text = KeywordSubstitutionCipher.decipher(cipherText, keyword, KeywordSubstitutionCipher.KeyFiller.ALPHABETICALLY_LAST_LETTER, false);}
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if(newFitness > output.fitness){
                    output.fitness = newFitness;output.key = keyword + " " + j;output.plainText = text;
                }
            }
        }
        output.isSuccessfull = (output.plainText!=null);

        return output;
    }
}
