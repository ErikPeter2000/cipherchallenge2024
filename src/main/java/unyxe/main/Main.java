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
                UXDAPTKWHERHJUJPNESLUUHKUCGFKOKNQFDBZFKNXSFQGUAUGVDAUO
                MBIPBBLUYGVMSEJOSHUVCJQVBNICDFLBWSYQRWUSKCRRBKQSWKJCDX
                UTSLGEJIZFASRMKGOBPSGEJIKUDGYCJARLRHTFOWVBNGMMEKEOOVNA
                KHXVPVQEUXGFHAZSRXCUDGJCUALBLLTZSHUSYDTGPJEFHUJPDAUGOA
                XSIKKJJCSMSEGBZGTNTDCTWBTBKHCXSLGEDAUJYCTFDAUZPCQIJIEL
                LKUESXGBLQT
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

        printBytes(Quagmire3Cipher.decipher(cipherTextBytes, TextUtilities.formatAndConvertToBytes("DOLPHINS"), TextUtilities.formatAndConvertToBytes("FISHBOWL")));

        //CipherBreakerOutput<byte[]> cbo = Quagmire2CipherBreaker.dictionaryAttack(cipherTextBytes, 6, 5);
        //cbo.displayPlaintext();
        //printBytes(cbo.key.get(0));
        //printBytes(cbo.key.get(1));


        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }
}
