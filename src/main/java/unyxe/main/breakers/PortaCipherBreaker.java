package main.breakers;

import main.ciphers.PortaCipher;
import main.utils.Constants;
import main.utils.FitnessCalculator;

import java.util.ArrayList;

public class PortaCipherBreaker {

    public static String[] guessTheKeyword(String keyword){
        ArrayList<String> potentialKeywords = new ArrayList<>();
        for(int i = 0; i < Constants.wordlist.length; i++){
            if(keyword.length() != Constants.wordlist[i].length())continue;
            String word = Constants.wordlist[i];
            boolean found = true;
            for(int j = 0; j < word.length(); j++){
                int u1 = word.charAt(j) - 65;
                int u2 = keyword.charAt(j) - 65;
                if(u1 == u2) continue;
                if(u1 % 2 == 0 && u2 != u1+1){
                    found = false;
                    break;
                }else if(u1 % 2 == 1 && u1 != u2+1){
                    found = false;
                    break;
                }
            }
            if(found){potentialKeywords.add(word);}
        }
        return potentialKeywords.toArray(new String[0]);
    }
    public static String[] generateKeysPorta(int length){
        String[] keys = new String[(int)Math.pow(13, length)];
        for(int i = 0; i < keys.length; i++){
            int num = i;
            StringBuilder key = new StringBuilder();
            for(int k = 0; k < length; k++){
                int num1 = (int) (num/Math.pow(13, length - k -1));
                if(num1 > 0){
                    num -= (int) (Math.pow(13, length - k-1)*num1);
                }
                key.append((char) (num1*2 + 65));
            }
            keys[i] = key.toString();
        }
        return keys;
    }
    public static CipherBreakerOutput bruteforceBellaso1552(String cipherText, double maxLength){
        CipherBreakerOutput output = new CipherBreakerOutput("PortaCipherBellaso1552", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        for(int n = 1; n <= maxLength; n++){
            String[] possibleKeys = generateKeysPorta(n);
            for (String possibleKey : possibleKeys) {
                String text = PortaCipher.decipherBellaco1552(cipherText, possibleKey);
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if (newFitness > output.fitness) {
                    output.fitness = newFitness;
                    output.key = possibleKey;
                    output.plainText = text;
                }
            }
            System.out.println("Key-length " + n + " finished. Best: " + output.plainText);
        }
        output.isSuccessfull = (output.plainText!=null);
        return output;
    }

    public static CipherBreakerOutput bruteforceWithWordlistBellaso1552(String cipherText){
        CipherBreakerOutput output = new CipherBreakerOutput("PortaCipherBellaso1552", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        for(int n = 0; n < Constants.wordlist.length; n++){
            String key = Constants.wordlist[n];
            String text = PortaCipher.decipherBellaco1552(cipherText, key);
            double newFitness = FitnessCalculator.TetragramFitness(text);
            if (newFitness > output.fitness) {
                output.fitness = newFitness;
                output.key = key;
                output.plainText = text;
            }
        }
        output.isSuccessfull = (output.plainText!=null);
        return output;
    }

    public static CipherBreakerOutput hillClimberAttackBellaso1552(String cipherText, int period){
        CipherBreakerOutput output = new CipherBreakerOutput("PortaCipherBellaso1552", cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);


        char[] key = "A".repeat(period).toCharArray();
        while(true){
            double oldFitness = output.fitness;
            for(int i = 0; i < period;i++){
                double maxFitness = -999999;
                char bestLetter = key[i];
                for(int j = 0; j < 26; j++){
                    key[i] = (char)(65+j);
                    String keyString = new String(key);
                    String text = PortaCipher.decipherBellaco1552(cipherText, keyString);
                    double newFitness = FitnessCalculator.TetragramFitness(text);
                    if (newFitness > maxFitness) {
                        maxFitness = newFitness;
                        bestLetter = (char)(65+j);
                    }
                }
                key[i] = bestLetter;
            }
            output.key = new String(key);
            output.plainText = PortaCipher.decipherBellaco1552(cipherText, output.key);
            output.fitness = FitnessCalculator.TetragramFitness(output.plainText);
            if(output.fitness == oldFitness){break;}
        }
        output.isSuccessfull = true;
        return output;
    }

    public static CipherBreakerOutput bruteforce(String cipherText, double maxLength, int version){
        CipherBreakerOutput output = new CipherBreakerOutput("PortaCipher" + version, cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        for(int n = 1; n <= maxLength; n++){
            String[] possibleKeys = generateKeysPorta(n);
            for (String possibleKey : possibleKeys) {
                String text = PortaCipher.decipher(cipherText, possibleKey, version);
                double newFitness = FitnessCalculator.TetragramFitness(text);
                if (newFitness > output.fitness) {
                    output.fitness = newFitness;
                    output.key = possibleKey;
                    output.plainText = text;
                }
            }
            System.out.println("Key-length " + n + " finished. Best: " + output.plainText);
        }
        output.isSuccessfull = (output.plainText!=null);
        return output;
    }

    public static CipherBreakerOutput bruteforceWithWordlist(String cipherText, int version){
        CipherBreakerOutput output = new CipherBreakerOutput("PortaCipher"+ version, cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);
        for(int n = 0; n < Constants.wordlist.length; n++){
            String key = Constants.wordlist[n];
            String text = PortaCipher.decipher(cipherText, key, version);
            double newFitness = FitnessCalculator.TetragramFitness(text);
            if (newFitness > output.fitness) {
                output.fitness = newFitness;
                output.key = key;
                output.plainText = text;
            }
        }
        output.isSuccessfull = (output.plainText!=null);
        return output;
    }

    public static CipherBreakerOutput hillClimberAttack(String cipherText, int period, int version){
        CipherBreakerOutput output = new CipherBreakerOutput("PortaCipher"+ version, cipherText);
        output.fitness = FitnessCalculator.TetragramFitness(cipherText);


        char[] key = "A".repeat(period).toCharArray();
        while(true){
            double oldFitness = output.fitness;
            for(int i = 0; i < period;i++){
                double maxFitness = -999999;
                char bestLetter = key[i];
                for(int j = 0; j < 13; j++){
                    key[i] = (char)(65+j*2);
                    String keyString = new String(key);
                    String text = PortaCipher.decipher(cipherText, keyString, version);
                    double newFitness = FitnessCalculator.TetragramFitness(text);
                    if (newFitness > maxFitness) {
                        maxFitness = newFitness;
                        bestLetter = (char)(65+j*2);
                    }
                }
                key[i] = bestLetter;
            }
            output.key = new String(key);
            output.plainText = PortaCipher.decipher(cipherText, output.key, version);
            output.fitness = FitnessCalculator.TetragramFitness(output.plainText);
            if(output.fitness == oldFitness){break;}
        }
        output.isSuccessfull = true;
        return output;
    }

    //TODO: attack using monograms both for version 1/2 and for modernized 1552
}
