package main;


import main.breakers.*;
import main.utils.periodanalysers.*;
import main.ciphers.*;
import main.utils.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Constants.initialize(false, false);

        String cipherText = """
                FAFSTXGTYRDWTCRESHFKTAUMCEVWWNRCHIMYMXTNHGBRHTJWFYCOXY
                RDYWXEDSTXPISROHVKBGEHBYTWEZOOSDURXXEJNZYVBYTACBVNMMJB
                LNYMBWIIVPRQXFPXHXAVJNXATCMRCFSTGFNNYIMWYFXYBQSMJPSYOG
                NFNIURRTXVWWWAGTXSGMPGSBATYWIVHOMTJIFQVBWRPMATFEPFBXME
                QWDMWEWRKDTNJODCBWXWAFFNRAETGIMYOFSSPQSXGJFEHAHYRDPGYS
                MJHISQIVJQIVROCEVUIMWAFFHSSWYEAMWTEITWT
                """;
        String plainText = """
                """;
        cipherText = TextFormatter.formatText(cipherText);
        plainText = TextFormatter.formatText(plainText);


        long startTime = System.currentTimeMillis();

        System.out.println(Arrays.toString(KasiskiExamination.examine(cipherText)));
        System.out.println(Arrays.toString(IOCPeriodAnalyser.guessPeriod(cipherText, 16)));
        System.out.println(Arrays.deepToString(TwistMethodPeriodAnalyser.guessPeriod(cipherText, 5, 16)));

        //CipherBreakerOutput cbo1 = Quagmire1CipherBreaker.twoStageAttack(cipherText, 10);
        //System.out.println(cbo1.key + " : " + cbo1.plainText);
        System.out.println(Quagmire2Cipher.decipher(cipherText, "PLANET", "EARTH"));

        //System.out.println(Arrays.toString(Quagmire1Cipher.getMonoSubstitutionAndVigenereKeys("QUAGMIRE", "CIPHER")));
        //System.out.println(Quagmire1CipherBreaker.checkTheKeyword("RABBITHOLE"));


        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }
}
