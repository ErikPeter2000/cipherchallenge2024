package main;


import main.ciphers.*;
import main.breakers.*;
import main.utils.periodanalysers.*;
import main.utils.*;

import java.util.Arrays;

import static main.utils.TextUtilities.*;

public class Main {
    public static void main(String[] args) {
        Constants.initialize();

        String cipherText = """
                THZBAROLASYZFKHFNYCEYXOQMWHXLELXLAUHNPMIAZTLVDWNNHRDOW
                SIHUCCMGNTTTCWSIHUCCMHTEEDCBUGMHZBAROLTSONNSHUDWQFZXRP
                NABMHTZDPRYHUCMMNTWADUBUKAOCCMUKELRSDREHULXIAYPECDPNZR
                OFVTRTWOCCMUKLAWGILYHNLCBRGWYNYCEYXTLVSGUFIDDMEKW
                """;
        String plainText = """
                """;
        String key = "CSKTFVRMGQLEXDHPJIZANBOUWY";
        String[] periodicKeys = new String[]{"LBRUVCJAWZYSHXINOQEPFTGKDM","SLNAXDIGOBKCEYQHTMWJFUVPZR","IFWVBXNGKHZQYOELPCDTJRUSAM"};

        byte[] cipherTextBytes = formatAndConvertToBytes(cipherText);
        byte[] plainTextBytes = formatAndConvertToBytes(plainText);
        byte[] keyBytes = formatAndConvertToBytes(key);
        byte[][] keysBytes = convertToByteArrays(periodicKeys, Constants.alphabet);
        byte[][] cribsBytes = convertToByteArrays(new String[]{"VICTORY", "SPAIN", "DISCO", "EUROVISION"},Constants.alphabet);


        long startTime = System.currentTimeMillis();


        System.out.println(Arrays.toString(KasiskiExamination.examine(cipherTextBytes)));
        System.out.println(Arrays.toString(IOCPeriodAnalyser.guessPeriod(cipherTextBytes, 16)));
        System.out.println(Arrays.deepToString(TwistMethodPeriodAnalyser.guessPeriod(cipherTextBytes, 5, 16)));
        /*
        CipherBreakerOutput cbo1 = Quagmire2CipherBreaker.dictionaryAttack(cipherText, 5,5);
        System.out.println(cbo1.key + " : " + cbo1.plainText);
         */

        //printBytes(PeriodicPolyAlphabeticSubstitution.decipher(cipherTextBytes, keysBytes));

        //MonoAlphabeticCipherBreaker.evolutionaryHillClimbingAttack(cipherTextBytes, 200, 200).displayPlaintext();


        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }
}
