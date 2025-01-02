package main.breakers.periodicpolyalphabetic;

import main.breakers.CipherBreakerOutput;
import main.ciphers.periodicpolyalphabetic.Quagmire4Cipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;
import main.utils.TextUtilities;

import java.util.ArrayList;
import java.util.Arrays;

public class Quagmire4CipherBreaker {
    public static CipherBreakerOutput<byte[]> dictionaryAttack(byte[] cipherText, byte[] alphabetKeyKnown, int alphabetCiphetextKeyLength, int shiftsKeyLength) {
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("Quagmire4Cipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        byte[] bestSKey = new byte[0];
        byte[] bestCKey = new byte[0];
        byte[][] alphabetCiphertextKeyWordlist = Constants.smallWordlistSplitted[alphabetCiphetextKeyLength];
        byte[][] shiftsKeyWordlist = Constants.smallWordlistSplitted[shiftsKeyLength];
        int n = 0;
        for (byte[] alphabetCiphertextKey : alphabetCiphertextKeyWordlist) {
            for (byte[] shiftsKey : shiftsKeyWordlist) {
                byte[] text = Quagmire4Cipher.decipher(cipherText, alphabetKeyKnown, alphabetCiphertextKey, shiftsKey);
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if (newFitness > output.fitness) {
                    output.fitness = newFitness;
                    bestCKey = Arrays.copyOf(alphabetCiphertextKey, alphabetCiphertextKey.length);
                    bestSKey = Arrays.copyOf(shiftsKey, shiftsKey.length);
                    output.plainText = text;
                }
            }
            if (n % 100 == 0)
                System.out.println(n * 100. / alphabetCiphertextKeyWordlist.length + "% done. " + TextUtilities.convertToString(output.plainText, Constants.alphabet) + " " + TextUtilities.convertToString(alphabetKeyKnown, Constants.alphabet) + " " + TextUtilities.convertToString(bestCKey, Constants.alphabet) + " " + TextUtilities.convertToString(bestSKey, Constants.alphabet));
            n++;
        }

        output.isSuccessful = (output.plainText != null);
        output.key = new ArrayList<>();
        output.key.add(alphabetKeyKnown);
        output.key.add(bestCKey);
        output.key.add(bestSKey);
        return output;
    }

    public static CipherBreakerOutput<byte[]> dictionaryAttack(byte[] cipherText, int alphabetKeyLength, int alphabetCiphetextKeyLength, int shiftsKeyLength) {
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("Quagmire4Cipher", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        byte[] bestAKey = new byte[0];
        byte[] bestSKey = new byte[0];
        byte[] bestCKey = new byte[0];
        byte[][] alphabetKeyWordlist = Constants.smallWordlistSplitted[alphabetKeyLength];
        byte[][] alphabetCiphertextKeyWordlist = Constants.smallWordlistSplitted[alphabetCiphetextKeyLength];
        byte[][] shiftsKeyWordlist = Constants.smallWordlistSplitted[shiftsKeyLength];
        int n = 0;
        for (byte[] alphabetCiphertextKey : alphabetCiphertextKeyWordlist) {
            for (byte[] alphabetKey : alphabetKeyWordlist) {
                for (byte[] shiftsKey : shiftsKeyWordlist) {
                    byte[] text = Quagmire4Cipher.decipher(cipherText, alphabetKey, alphabetCiphertextKey, shiftsKey);
                    double newFitness = FitnessCalculator.TetragramFitness(text);
                    if (newFitness > output.fitness) {
                        output.fitness = newFitness;
                        bestAKey = Arrays.copyOf(alphabetKey, alphabetKey.length);
                        bestCKey = Arrays.copyOf(alphabetCiphertextKey, alphabetCiphertextKey.length);
                        bestSKey = Arrays.copyOf(shiftsKey, shiftsKey.length);
                        output.plainText = text;
                    }
                }
            }
            System.out.println(n * 100. / alphabetCiphertextKeyWordlist.length + "% done. " + TextUtilities.convertToString(output.plainText, Constants.alphabet) + " " + TextUtilities.convertToString(bestAKey, Constants.alphabet) + " " + TextUtilities.convertToString(bestCKey, Constants.alphabet) + " " + TextUtilities.convertToString(bestSKey, Constants.alphabet));
            n++;
        }

        output.isSuccessful = (output.plainText != null);
        output.key = new ArrayList<>();
        output.key.add(bestAKey);
        output.key.add(bestCKey);
        output.key.add(bestSKey);
        return output;
    }
}
