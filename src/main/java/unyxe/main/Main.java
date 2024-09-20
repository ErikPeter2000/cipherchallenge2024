package main;


import main.breakers.*;
import main.ciphers.*;
import main.utils.periodanalysers.*;
import main.utils.maths.*;
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
                rlboy itdvs tennc rmaid toafl ubhle cneda nmoam nrdie
                ficeh shoif sjmea hstsy nsiap sedsv mseel eopyl tndda
                meoeb eopyl hndti ieedm artre eanne cettc oceyt hruea
                yeebr iqrue ndrae eidrd tecdt eaokt hetsh asinp ndmie
                aetmh uirng yenrd orocu damnm rdaan moeet frbka etohr
                tdmie nrear nneaa eodst toeuk edadn yrsot hetsh fsiop
                retfh fcehn aelte lotut etohn rproe gftao atemn tirnt
                ihoet dorrl sisph etohn rpiom entca tionf pceer nntgi
                eoalp hsocn eeisn teang tshsa drier tegar encco tirnn
                rwhoe todfs rfhie rlsot ahdet repap ocaen ibfra ssthi
                raqdu tionn dmhee reirt neaan oaics iintd woonn tchhi
                tfhae ufeeo mpreo taaty oshmi dnmte deenp ruyao toest
                eirna nrvye aveer ymnad rciun incso brdae zhlae ndair
                cfeef intgi eetrh rnoof ouyno onrya mofuy ifaay oslya
                liulw esawn hortt toenc arrya rotuy lrpie odafn oords
                hgitn asihs yblel rroou irdge nnvoe dabro ietvh yocrt
                rstio trhao lnieo onsco oomdm etrhe unvga ydabr amcmo
                tonfd mahde lailr tdosr envci xxnxt
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

        CipherBreakerOutput<byte[]> cbo = PermutationCipherBreaker.bruteforce(cipherTextBytes, 6);
        cbo.displayPlaintext();




        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }
}
