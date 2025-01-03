package main.breakers.periodicpolyalphabetic;

import main.breakers.CipherBreakerOutput;
import main.ciphers.periodicpolyalphabetic.PortaCipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that contains methods to break the Porta Cipher.
 */
public class PortaCipherBreaker {

    /**
     * Method that tries to guess the keyword used to encrypt the text.
     *
     * @param keyword The keyword used to encrypt the text.
     * @return An array of possible keywords.
     */
    public static byte[][] guessTheKeyword(byte[] keyword) {
        ArrayList<byte[]> potentialKeywords = new ArrayList<>();
        for (int i = 0; i < Constants.wordlist.length; i++) {
            if (keyword.length != Constants.wordlist[i].length) continue;
            byte[] word = Arrays.copyOf(Constants.wordlist[i], Constants.wordlist[i].length);
            boolean found = true;
            for (int j = 0; j < word.length; j++) {
                int u1 = word[j];
                int u2 = keyword[j];
                if (u1 == u2) continue;
                if (u1 % 2 == 0 && u2 != u1 + 1) {
                    found = false;
                    break;
                } else if (u1 % 2 == 1 && u1 != u2 + 1) {
                    found = false;
                    break;
                }
            }
            if (found) {
                potentialKeywords.add(word);
            }
        }
        return potentialKeywords.toArray(new byte[0][]);
    }

    /**
     * Method that generates all possible keys for the Porta Cipher.
     *
     * @param length The length of the key.
     * @return An array of possible keys.
     */
    public static byte[][] generateKeysPorta(int length) {
        byte[][] keys = new byte[(int) Math.pow(13, length)][length];
        for (int i = 0; i < keys.length; i++) {
            int num = i;
            for (int k = 0; k < length; k++) {
                int num1 = (int) (num / Math.pow(13, length - k - 1));
                if (num1 > 0) {
                    num -= (int) (Math.pow(13, length - k - 1) * num1);
                }
                keys[i][k] = (byte) (num1 * 2);
            }
        }
        return keys;
    }

    /**
     * Method that tries to break the Porta Cipher Bellaso 1552 version using a brute force attack.
     *
     * @param cipherText The text to be decrypted.
     * @param maxLength  The maximum length of the key.
     * @return A CipherBreakerOutput object containing the results of the attack.
     */
    public static CipherBreakerOutput<byte[]> bruteforceBellaso1552(byte[] cipherText, double maxLength) {
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("PortaCipherBellaso1552", cipherText);
        byte[] bestKey = null;
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        for (int n = 1; n <= maxLength; n++) {
            byte[][] possibleKeys = generateKeysPorta(n);
            for (byte[] possibleKey : possibleKeys) {
                bestKey = updateBestKey(cipherText, output, bestKey, possibleKey);
            }
            //System.out.println("Key-length " + n + " finished. Best: " + output.plainText);
        }
        output.isSuccessful = (output.plainText != null);
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }

    /**
     * Method that updates the best key found so far.
     *
     * @param cipherText  The text to be decrypted.
     * @param output      The output object containing the results of the attack.
     * @param bestKey     The best key found so far.
     * @param possibleKey The possible key to be tested.
     * @return The best key found so far.
     */
    private static byte[] updateBestKey(byte[] cipherText, CipherBreakerOutput<byte[]> output, byte[] bestKey, byte[] possibleKey) {
        byte[] text = PortaCipher.decipherBellaso1552(cipherText, possibleKey);
        double newFitness = FitnessCalculator.TetragramFitness(text);
        if (newFitness > output.fitness) {
            output.fitness = newFitness;
            bestKey = Arrays.copyOf(possibleKey, possibleKey.length);
            output.plainText = text;
        }
        return bestKey;
    }

    /**
     * Method that tries to break the Porta Cipher Bellaso 1552 version using a brute force attack with a wordlist.
     *
     * @param cipherText The text to be decrypted.
     * @return A CipherBreakerOutput object containing the results of the attack.
     */
    public static CipherBreakerOutput<byte[]> bruteforceWithWordlistBellaso1552(byte[] cipherText) {
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("PortaCipherBellaso1552", cipherText);
        byte[] bestKey = null;
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        for (int n = 0; n < Constants.wordlist.length; n++) {
            byte[] key = Constants.wordlist[n];
            bestKey = updateBestKey(cipherText, output, bestKey, key);
        }
        output.isSuccessful = (output.plainText != null);
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }

    /**
     * Method that tries to break the Porta Cipher Bellaso 1552 version using a hill climber attack.
     *
     * @param cipherText The text to be decrypted.
     * @param period     The period of the key.
     * @return A CipherBreakerOutput object containing the results of the attack.
     */
    public static CipherBreakerOutput<byte[]> hillClimberAttackBellaso1552(byte[] cipherText, int period) {
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("PortaCipherBellaso1552", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);

        byte[] bestKey;

        byte[] key = new byte[period];
        while (true) {
            double oldFitness = output.fitness;
            for (int i = 0; i < period; i++) {
                double maxFitness = -999999;
                byte bestLetter = key[i];
                for (int j = 0; j < 26; j++) {
                    key[i] = (byte) (j);
                    byte[] text = PortaCipher.decipherBellaso1552(cipherText, key);
                    double newFitness = FitnessCalculator.TetragramFitness(text);
                    if (newFitness > maxFitness) {
                        maxFitness = newFitness;
                        bestLetter = (byte) (j);
                    }
                }
                key[i] = bestLetter;
            }
            bestKey = Arrays.copyOf(key, key.length);
            output.plainText = PortaCipher.decipherBellaso1552(cipherText, key);
            output.fitness = FitnessCalculator.TetragramFitness(output.plainText);
            if (output.fitness == oldFitness) {
                break;
            }
        }
        output.isSuccessful = true;
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }

    /**
     * Method that tries to break the Porta Cipher using a brute force attack.
     *
     * @param cipherText The text to be decrypted.
     * @param maxLength  The maximum length of the key.
     * @param version    The version of the Porta Cipher.
     * @return A CipherBreakerOutput object containing the results of the attack.
     */
    public static CipherBreakerOutput<byte[]> bruteforce(byte[] cipherText, double maxLength, int version) {
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("PortaCipher" + version, cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        byte[] bestKey = null;
        for (int n = 1; n <= maxLength; n++) {
            byte[][] possibleKeys = generateKeysPorta(n);
            for (byte[] possibleKey : possibleKeys) {
                bestKey = updateBestBruteforceKey(cipherText, version, output, bestKey, possibleKey);
            }
            //System.out.println("Key-length " + n + " finished. Best: " + output.plainText);
        }
        output.isSuccessful = (output.plainText != null);
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }

    /**
     * Method that updates the best key found so far.
     *
     * @param cipherText  The text to be decrypted.
     * @param version     The version of the Porta Cipher.
     * @param output      The output object containing the results of the attack.
     * @param bestKey     The best key found so far.
     * @param possibleKey The possible key to be tested.
     * @return The best key found so far.
     */
    private static byte[] updateBestBruteforceKey(byte[] cipherText, int version, CipherBreakerOutput<byte[]> output, byte[] bestKey, byte[] possibleKey) {
        byte[] text = PortaCipher.decipher(cipherText, possibleKey, version);
        double newFitness = FitnessCalculator.TetragramFitness(text);
        if (newFitness > output.fitness) {
            output.fitness = newFitness;
            bestKey = Arrays.copyOf(possibleKey, possibleKey.length);
            output.plainText = text;
        }
        return bestKey;
    }

    /**
     * Method that tries to break the Porta Cipher using a brute force attack with a wordlist.
     *
     * @param cipherText The text to be decrypted.
     * @param version    The version of the Porta Cipher.
     * @return A CipherBreakerOutput object containing the results of the attack.
     */
    public static CipherBreakerOutput<byte[]> bruteforceWithWordlist(byte[] cipherText, int version) {
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("PortaCipher" + version, cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        byte[] bestKey = null;
        for (int n = 0; n < Constants.wordlist.length; n++) {
            byte[] key = Constants.wordlist[n];
            bestKey = updateBestBruteforceKey(cipherText, version, output, bestKey, key);
        }
        output.isSuccessful = (output.plainText != null);
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }

    /**
     * Method that tries to break the Porta Cipher using a hill climber attack.
     *
     * @param cipherText The text to be decrypted.
     * @param period     The period of the key.
     * @param version    The version of the Porta Cipher.
     * @return A CipherBreakerOutput object containing the results of the attack.
     */
    public static CipherBreakerOutput<byte[]> hillClimberAttack(byte[] cipherText, int period, int version) {
        CipherBreakerOutput<byte[]> output = new CipherBreakerOutput<>("PortaCipher" + version, cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);

        byte[] bestKey;

        byte[] key = new byte[period];
        while (true) {
            double oldFitness = output.fitness;
            for (int i = 0; i < period; i++) {
                double maxFitness = -999999;
                byte bestLetter = key[i];
                for (int j = 0; j < 13; j++) {
                    key[i] = (byte) (j * 2);
                    byte[] text = PortaCipher.decipher(cipherText, key, version);
                    double newFitness = FitnessCalculator.TetragramFitness(text);
                    if (newFitness > maxFitness) {
                        maxFitness = newFitness;
                        bestLetter = (byte) (j * 2);
                    }
                }
                key[i] = bestLetter;
            }
            bestKey = Arrays.copyOf(key, key.length);
            output.plainText = PortaCipher.decipher(cipherText, key, version);
            output.fitness = FitnessCalculator.TetragramFitness(output.plainText);
            if (output.fitness == oldFitness) {
                break;
            }
        }
        output.isSuccessful = true;
        output.key = new ArrayList<>();
        output.key.add(bestKey);
        return output;
    }

    //TODO: attack using monograms both for version 1/2 and for modernized 1552
}
