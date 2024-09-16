package main;


import main.breakers.*;
import main.utils.periodanalysers.*;
import main.ciphers.*;
import main.utils.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Constants.initialize(true, true);

        String cipherText = """
                EMNMGYUQNIXTEMNMGYUQNVGUKOYJUZKRKCINNCEKZLGLGXMEUJXKFE
                UKYJUNJEXKRETSGEMKQNLUUPGQXCVOAXYCHDKJXHUPGQXMAKPNAXAL
                QSUHFVWJYVSKWDSFGEUETDQNQKEMXAUTYXSKWRYGLECDSMOCWDAMQL
                CRXPUMDLAMQLCLJPUMIDAIZTGEJLGKHENCDPVUXXGYDLAOSCGLFITK
                WJWCIQQMOSQNOETIMEVZAPUCVSGUSKWKGVTYVCOSQNDQEMXTVUXFUO
                QDDKHLCXSLVEASYVZUCLELVEYEWMAMPJXCMKNQXOUCGQUHFAKPALVE
                OETKAVCCNCDXQUXEUJGDWWVRKGXOKCVZAUOYUJKCTQVZAWKRTCAXQL
                GJPCIAJJGVLLQSFSXZACXHFCGQOEUZFCTYPRGGAVZCVUYVUCQSTRUE
                VETDVKYUVDZLGNUXUIVCYVQUKLJHGKM
                
                """;
        String plainText = """
                ADALOVELACEANDCHARLESBABBAGEARETWOOFTHEMOSTIMPORTANTMEMBERSOFOURCOMMUNITYTHEYFEATUREDINTHEFIRSTNATIONALCIPHERCHALLENGEANDAGAINSOMEYEARSLATERWHENWEEXPLOREDTHEIREARLYMEETINGINTHISADVENTUREWEARERETURNINGTOTHEENDOFHERLIFEATLEASTTHEPARTRECORDEDINTHEARCHIVESATBOSSHEADQUARTERSIFOUNDTHISSTORYBYACCIDENTWHENIWASRESEARCHINGTHEROLEOFTHEROYALFAMILYINOURNATIONALSECURITYMANYOFTHEMSERVEDINTHEMILITARYANDAREFAMILIARWITHCODESANDCIPHERSANDIWONDEREDHOWFARBACKTHATINTERESTWENTTHATWASHOWISTUMBLEDACROSSANOTEFROMPRINCEALBERTTOLORDPALMERSTONANDTHATSTARTEDMEDOWNTHERABBITHOLETHATLEDUSHEREIHOPEYOUENJOYITASMUCHASIDIDWEWILLMEETSOMEFASCINATINGNEWCHARACTERSONTHEWAYANDTHESTORYTAKESATWISTORTWOOFCOURSEIKNOWHOWITENDSYOUDONTYETBUTIFYOUSTICKWITHITYOUWILLANDLIKEMEYOUWILLPROBABLYLEARNSOMETHINGONTHEWAYIHAVEPOSTEDASKETCHOFABULLETTHATWASSENTTOADABACKINONTHECASEFILESPAGETHATWASWHERETHESTORYBEGANANDWHATDREWADAINTAKEALOOKANDSEEIFYOUCANFIGUREOUTWHATITMIGHTHAVEMEANTITISNOTABADPLACETOSTART
                """;
        cipherText = TextFormatter.formatText(cipherText);
        plainText = TextFormatter.formatText(plainText);


        long startTime = System.currentTimeMillis();

        System.out.println(Arrays.toString(KasiskiExamination.examine(cipherText)));
        System.out.println(Arrays.toString(IOCPeriodAnalyser.guessPeriod(cipherText, 16)));
        System.out.println(Arrays.deepToString(TwistMethodPeriodAnalyser.guessPeriod(cipherText, 5, 16)));

        CipherBreakerOutput cbo1 = PeriodicAffineCipherBreaker.monogramFreqAttack(cipherText, 6);
        System.out.println(cbo1.key + " : " + cbo1.plainText);


        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }
}
