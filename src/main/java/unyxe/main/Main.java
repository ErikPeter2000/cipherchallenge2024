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
                XZPRVLJINCQXKYWVKXJJQXPRVCARJTCQSJDQCRECDGGIXTKKYWDUWJ
                XBKTZCAKJAHRKMZFNMNZLBLRXVAIRZGQXGIXTKPTFNCQJUCIPCDQCI
                ZSLJINCQXSJDQCRECDGRHKWMPAANXKBTCQHRQLUMHCAOVCWXZOAANI
                HJVOSNWCVTPLJNCQJTQYIOHBMVWHRIIPMNAGLGOUXLGJUXJJDUKWJN
                QIPVAOIOJQYIAANJYHKFXKAVCQOJGNAKWJNXKACGUPFCLCQLKQOMJD
                NAKACCZRHCALRHKXZOCCLTKMDNQSZFVAKECDG
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

        //printBytes(Quagmire4Cipher.decipher(cipherTextBytes, TextUtilities.formatAndConvertToBytes("FOUR"), TextUtilities.formatAndConvertToBytes("PIGMENT"), TextUtilities.formatAndConvertToBytes("COLOR")));

        //CipherBreakerOutput<byte[]> cbo = Quagmire4CipherBreaker.dictionaryAttack(cipherTextBytes, 5, 5, 5);
        CipherBreakerOutput<byte[]> cbo = Quagmire4CipherBreaker.dictionaryAttack(cipherTextBytes, formatAndConvertToBytes("SPEED"), 5, 5);
        cbo.displayPlaintext();
        printBytes(cbo.key.get(0));
        printBytes(cbo.key.get(1));
        printBytes(cbo.key.get(2));


        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }
}
