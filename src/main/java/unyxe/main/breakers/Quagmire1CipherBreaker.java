package main.breakers;

import main.ciphers.CaesarCipher;
import main.ciphers.MonoAlphabeticCipher;
import main.ciphers.Quagmire1Cipher;
import main.ciphers.VigenereCipher;
import main.utils.Analyser;
import main.utils.Constants;
import main.utils.FitnessCalculator;
import main.utils.periodanalysers.IOCPeriodAnalyser;

public class Quagmire1CipherBreaker {

    public static boolean checkTheKeyword(String keyword){
        for(int i = 0; i < Constants.wordlist.length; i++){
            if(keyword.equals(Constants.wordlist[i])){return true;}
        }
        return false;
    }

    public static CipherBreakerOutput dictionaryAttack(String cipherText, int alphabetKeyLength, int shiftsKeyLength){
        CipherBreakerOutput output = new CipherBreakerOutput("Quagmire1Cipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        String[] alphabetKeyWordlist = Constants.smallWordlistSplitted[alphabetKeyLength];
        String[] shiftsKeyWordlist = Constants.smallWordlistSplitted[shiftsKeyLength];
        int n = 0;
        for (String alphabetKey : alphabetKeyWordlist) {
            for (String shiftsKey : shiftsKeyWordlist) {
                String text = Quagmire1Cipher.decipher(cipherText, alphabetKey, shiftsKey);
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

    static void shiftLeft(double[] array){
        double temp = array[0];
        for(int i = 1; i < array.length; i++){
            array[i-1] = array[i];
        }
        array[array.length-1] = temp;
    }

    public static CipherBreakerOutput twoStageAttack(String cipherText, int period){
        CipherBreakerOutput output = new CipherBreakerOutput("Quagmire1Cipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);

        String[] slices = IOCPeriodAnalyser.splitText(cipherText, period);
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
        StringBuilder vigenereKeySB = new StringBuilder();
        for (int shift : shifts) {
            vigenereKeySB.append((char) (shift + 65));
        }
        String vigenereKey = vigenereKeySB.toString();

        boolean found = false;
        for(int i = 1; i < 26;i++){
            String cKey = CaesarCipher.encipher(vigenereKey, i);
            if(checkTheKeyword(cKey)){
                vigenereKey = cKey;
                found = true;
                System.out.println("[Quagmire1CipherBreaker] Shifts keyword found: " + cKey);
                break;
            }
        }
        if(!found) System.out.println("[Quagmire1CipherBreaker] Shifts keyword not found.");

        String substitutionCipher = VigenereCipher.decipher(cipherText, vigenereKey);
        CipherBreakerOutput cbo = MonoAlphabeticCipherBreaker.evolutionaryAdvancedHillClimbingAttack(substitutionCipher, 100, 200);

        String alphabetKeyword = MonoAlphabeticCipher.inverseKey(cbo.key);

        output.fitness = cbo.fitness;
        output.key =vigenereKey + " " + alphabetKeyword;
        output.plainText = cbo.plainText;

        output.isSuccessfull = (output.plainText!=null);
        return output;
    }
}
