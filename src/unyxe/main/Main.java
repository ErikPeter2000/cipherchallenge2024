package main;

import main.breakers.MonoAlphabeticCipherBreaker;
import main.utils.*;

public class Main {
    public static void main(String[] args) {
        Constants.initialize();

        String cipherText = """
                IDSIYUDHJZXIXTOQOXUSVOROMNSRMOREXOESGOMMSVOMNSRSUSDHJS
                YSTNIJOUQSTSMKSREMNUDJTNIISRJNIDOUKQLHMNGUXLUINIXINHRG
                NCDSTIDSUNQCUHRUZSOIXTSVOMNSRSUSNRHRSSCNUHVSIDSUSGTSIQ
                SUUOESIHMVYNSJSTUIHJOIGDZXIXTOQOIDXTUVOKUOIISR
                """;
        cipherText = TextFormatter.formatText(cipherText);

        MonoAlphabeticCipherBreaker.evolutionaryHillClimbingAttack(cipherText, 100, 100).displayPlaintext();
    }
}
