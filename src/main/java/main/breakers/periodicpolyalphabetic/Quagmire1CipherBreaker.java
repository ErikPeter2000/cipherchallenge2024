package main.breakers.periodicpolyalphabetic;

import main.breakers.CipherBreakerOutput;
import main.breakers.monoalphabetic.MonoAlphabeticCipherBreaker;
import main.ciphers.monoalphabetic.CaesarCipher;
import main.ciphers.monoalphabetic.MonoAlphabeticCipher;
import main.ciphers.periodicpolyalphabetic.Quagmire1Cipher;
import main.ciphers.periodicpolyalphabetic.VigenereCipher;
import main.utils.Analyser;
import main.utils.Constants;
import main.utils.FitnessCalculator;
import main.utils.TextUtilities;
import main.utils.periodanalysers.IOCPeriodAnalyser;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Quagmire1CipherBreaker class is used to break Quagmire1Cipher.
 */
public class Quagmire1CipherBreaker {

    /**
     * This method is used to check if the keyword is in the wordlist.
     *
     * @param keyword the keyword to be checked.
     * @return true if the keyword is in the wordlist, false otherwise.
     */
    public static boolean checkTheKeyword(byte[] keyword) {
        for (int i = 0; i < Constants.wordlist.length; i++) {
            if (TextUtilities.isEqual(keyword, Constants.wordlist[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to break Quagmire1Cipher using dictionary attack.
     *
     * @param cipherText the cipher text to be broken.
     * @param alphabetKeyLength the length of the alphabet key.
     * @param shiftsKeyLength the length of the shifts key.
     * @return the output of the breaking process.
     */
    public static CipherBreakerOutput<byte[]> dictionaryAttack(byte[] cipherText, int alphabetKeyLength, int shiftsKeyLength) {
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("Quagmire1Cipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        byte[] bestAKey = null;
        byte[] bestSKey = null;
        byte[][] alphabetKeyWordlist = Constants.smallWordlistSplit[alphabetKeyLength];
        byte[][] shiftsKeyWordlist = Constants.smallWordlistSplit[shiftsKeyLength];
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
            if (n % 100 == 0) System.out.println(n * 100. / alphabetKeyWordlist.length + "% done.");
            n++;
        }
        output.isSuccessful = (output.plainText != null);
        output.key = new ArrayList<>();
        output.key.add(bestAKey);
        output.key.add(bestSKey);
        return output;
    }

    /**
     * Shifts the elements of the array to the left.
     * @param array the array to be shifted.
     */
    static void shiftLeft(double[] array) {
        double temp = array[0];
        for (int i = 1; i < array.length; i++) {
            array[i - 1] = array[i];
        }
        array[array.length - 1] = temp;
    }

    /**
     * This method is used to break Quagmire1Cipher using two-stage attack.
     *
     * @param cipherText the cipher text to be broken.
     * @param period the period of the cipher.
     * @return the output of the breaking process.
     */
    public static CipherBreakerOutput<byte[]> twoStageAttack(byte[] cipherText, int period) {
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("Quagmire1Cipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);

        byte[][] slices = IOCPeriodAnalyser.splitText(cipherText, period);
        double[] pivotMonogramFreq = Analyser.getMonogramStatistic(slices[0]);
        int[] shifts = new int[slices.length];
        for (int i = 1; i < slices.length; i++) {
            double[] monogramFreq = Analyser.getMonogramStatistic(slices[i]);
            int bestShift = 0;
            double bestMatch = Analyser.AngleBetweenVectors(pivotMonogramFreq, monogramFreq);
            for (int j = 1; j < 26; j++) {
                shiftLeft(monogramFreq);
                double match = Analyser.AngleBetweenVectors(pivotMonogramFreq, monogramFreq);
                if (match > bestMatch) {
                    bestShift = j;
                    bestMatch = match;
                }
            }
            shifts[i] = bestShift;
        }
        shifts[0] = 0;
        byte[] vigenereKey = new byte[shifts.length];
        for (int i = 0; i < shifts.length; i++) {
            vigenereKey[i] = (byte) shifts[i];
        }

        boolean found = false;
        for (int i = 1; i < 26; i++) {
            byte[] cKey = CaesarCipher.encipher(vigenereKey, i);
            if (checkTheKeyword(cKey)) {
                vigenereKey = cKey;
                found = true;
                //System.out.println("[Quagmire1CipherBreaker] Shifts keyword found: " + cKey);
                break;
            }
        }
        if (!found) System.out.println("[Quagmire1CipherBreaker] Shifts keyword not found.");

        byte[] substitutionCipher = VigenereCipher.decipher(cipherText, vigenereKey);
        CipherBreakerOutput<byte[]> cbo = MonoAlphabeticCipherBreaker.evolutionaryHillClimbingAttack(substitutionCipher, 100, 400);

        byte[] alphabetKeyword = MonoAlphabeticCipher.inverseKey(cbo.key.get(0));

        output.fitness = cbo.fitness;
        output.key = new ArrayList<>();
        output.key.add(alphabetKeyword);
        output.key.add(vigenereKey);
        output.plainText = cbo.plainText;

        output.isSuccessful = (output.plainText != null);
        return output;
    }
}
