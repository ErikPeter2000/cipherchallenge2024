package main;


import main.utils.*;

public class Main {
    public static void main(String[] args) {
        Constants.initialize();

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
        cipherText = TextUtilities.formatText(cipherText);
        plainText = TextUtilities.formatText(plainText);
        byte[] cipherTextBytes = TextUtilities.convertToByteArray(cipherText, Constants.alphabet);


        long startTime = System.currentTimeMillis();

        /*
        System.out.println(Arrays.toString(KasiskiExamination.examine(cipherText)));
        System.out.println(Arrays.toString(IOCPeriodAnalyser.guessPeriod(cipherText, 16)));
        System.out.println(Arrays.deepToString(TwistMethodPeriodAnalyser.guessPeriod(cipherText, 5, 16)));

        CipherBreakerOutput cbo1 = Quagmire2CipherBreaker.dictionaryAttack(cipherText, 5,5);
        System.out.println(cbo1.key + " : " + cbo1.plainText);
         */


        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }
}
