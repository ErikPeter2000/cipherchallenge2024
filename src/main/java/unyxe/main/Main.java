package main;


import main.breakers.*;
import main.ciphers.*;
import main.utils.periodanalysers.*;
import main.utils.*;

import java.util.Arrays;

import static main.utils.TextUtilities.*;

public class Main {
    public static void main(String[] args) {
        Constants.initialize();

        /*
        LTRELAITNSIIAOHIETETLATCERRYNUOEIHQUNAGSFIEAETPREITAIN
                DILTREELTNRSDWIO
         */ //Decipher later

        String cipherText = """
                
                """;
        String plainText = """
                THIS MESSAGE IS ENCRYPTED WITH A TRANSPOSITION CIPHER
                """;
        String key = "CSKTFVRMGQLEXDHPJIZANBOUWY";
        String[] periodicKeys = new String[]{"LBRUVCJAWZYSHXINOQEPFTGKDM","SLNAXDIGOBKCEYQHTMWJFUVPZR","IFWVBXNGKHZQYOELPCDTJRUSAM"};


        byte[] cipherTextBytes = formatAndConvertToBytes(cipherText);
        byte[] plainTextBytes = formatAndConvertToBytes(plainText);
        byte[] keyBytes = formatAndConvertToBytes(key);
        byte[][] keysBytes = convertToByteArrays(periodicKeys, Constants.alphabet);
        byte[][] cribsBytes = convertToByteArrays(new String[]{"VICTORY", "SPAIN", "DISCO", "EUROVISION"},Constants.alphabet);
        byte[] permutationKey = PermutationCipher.generatePermutationFromKeyword(formatAndConvertToBytes("REPETITION"), true);


        long startTime = System.currentTimeMillis();


        //System.out.println(Arrays.toString(KasiskiExamination.examine(cipherTextBytes)));
        //System.out.println(Arrays.toString(IOCPeriodAnalyser.guessPeriod(cipherTextBytes, 16)));
        //System.out.println(Arrays.deepToString(TwistMethodPeriodAnalyser.guessPeriod(cipherTextBytes, 5, 16)));

        //printBytes(Quagmire4Cipher.decipher(cipherTextBytes, TextUtilities.formatAndConvertToBytes("FOUR"), TextUtilities.formatAndConvertToBytes("PIGMENT"), TextUtilities.formatAndConvertToBytes("COLOR")));

        //CipherBreakerOutput<byte[][]> cbo = PeriodicPolyAlphabeticSubstitutionCipherBreaker.hillClimber(cipherTextBytes, 4, 1000000);
        //cbo.displayPlaintext();
        //printBytes(cbo.key.get(0));
        byte[][] pos = PermutationCipher.generateAllPossiblePermutations(4);
        printBytes(pos);


        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }
}
