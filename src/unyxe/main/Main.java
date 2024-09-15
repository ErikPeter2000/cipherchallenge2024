package main;

import main.breakers.MonoAlphabeticCipherBreaker;
import main.utils.*;

public class Main {
    public static void main(String[] args) {
        Constants.initialize(false, true);

        String cipherText = """
                IDSIYUDHJZXIXTOQOXUSVOROMNSRMOREXOESGOMMSVOMNSRSUSDHJS
                YSTNIJOUQSTSMKSREMNUDJTNIISRJNIDOUKQLHMNGUXLUINIXINHRG
                NCDSTIDSUNQCUHRUZSOIXTSVOMNSRSUSNRHRSSCNUHVSIDSUSGTSIQ
                SUUOESIHMVYNSJSTUIHJOIGDZXIXTOQOIDXTUVOKUOIISR
                """;
        cipherText = TextFormatter.formatText(cipherText);


        long startTime = System.currentTimeMillis();
        MonoAlphabeticCipherBreaker.evolutionaryAdvancedHillClimbingAttack(cipherText, 200, 200).displayPlaintext();
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime));    }
}
