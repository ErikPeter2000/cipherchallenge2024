package main;


import main.breakers.AffineCipherBreaker;
import main.breakers.CaesarCipherBreaker;
import main.breakers.KeywordSubstitutionCipherBreaker;
import main.breakers.MonoAlphabeticCipherBreaker;
import main.ciphers.CaesarCipher;
import main.ciphers.MonoAlphabeticCipher;
import main.utils.*;

import static main.utils.TextUtilities.*;

public class Main {
    public static void main(String[] args) {
        Constants.initialize();

        String cipherText = """
                IDSIYUDHJZXIXTOQOXUSVOROMNSRMOREXOESGOMMSVOMNSRSUSDHJS
                YSTNIJOUQSTSMKSREMNUDJTNIISRJNIDOUKQLHMNGUXLUINIXINHRG
                NCDSTIDSUNQCUHRUZSOIXTSVOMNSRSUSNRHRSSCNUHVSIDSUSGTSIQ
                SUUOESIHMVYNSJSTUIHJOIGDZXIXTOQOIDXTUVOKUOIISR
                """;
        String plainText = """
                """;
        String key = "CSKTFVRMGQLEXDHPJIZANBOUWY";

        byte[] cipherTextBytes = formatAndConvertToBytes(cipherText);
        byte[] plainTextBytes = formatAndConvertToBytes(plainText);
        byte[] keyBytes = formatAndConvertToBytes(key);
        byte[][] cribsBytes = convertToByteArrays(new String[]{"VICTORY", "SPAIN", "DISCO", "EUROVISION"},Constants.alphabet);


        long startTime = System.currentTimeMillis();

        /*
        System.out.println(Arrays.toString(KasiskiExamination.examine(cipherText)));
        System.out.println(Arrays.toString(IOCPeriodAnalyser.guessPeriod(cipherText, 16)));
        System.out.println(Arrays.deepToString(TwistMethodPeriodAnalyser.guessPeriod(cipherText, 5, 16)));

        CipherBreakerOutput cbo1 = Quagmire2CipherBreaker.dictionaryAttack(cipherText, 5,5);
        System.out.println(cbo1.key + " : " + cbo1.plainText);
         */

        MonoAlphabeticCipherBreaker.evolutionaryHillClimbingAttack(cipherTextBytes, 200, 200).displayPlaintext();


        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }
}
