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
                HTEWPQSVNSRMGRWXEATJNAVYCKGVYEJFIVXBFINOOSDWQUSGAHBEQQ
                MJVYSPQCCWFOIXYPBYEWBAJJNCKTGQEVADBOHNFYAWCJ
                """;
        String plainText = """
                ADALOVELACEANDCHARLESBABBAGEARETWOOFTHEMOSTIMPORTANTMEMBERSOFOURCOMMUNITYTHEYFEATUREDINTHEFIRSTNATIONALCIPHERCHALLENGEANDAGAINSOMEYEARSLATERWHENWEEXPLOREDTHEIREARLYMEETINGINTHISADVENTUREWEARERETURNINGTOTHEENDOFHERLIFEATLEASTTHEPARTRECORDEDINTHEARCHIVESATBOSSHEADQUARTERSIFOUNDTHISSTORYBYACCIDENTWHENIWASRESEARCHINGTHEROLEOFTHEROYALFAMILYINOURNATIONALSECURITYMANYOFTHEMSERVEDINTHEMILITARYANDAREFAMILIARWITHCODESANDCIPHERSANDIWONDEREDHOWFARBACKTHATINTERESTWENTTHATWASHOWISTUMBLEDACROSSANOTEFROMPRINCEALBERTTOLORDPALMERSTONANDTHATSTARTEDMEDOWNTHERABBITHOLETHATLEDUSHEREIHOPEYOUENJOYITASMUCHASIDIDWEWILLMEETSOMEFASCINATINGNEWCHARACTERSONTHEWAYANDTHESTORYTAKESATWISTORTWOOFCOURSEIKNOWHOWITENDSYOUDONTYETBUTIFYOUSTICKWITHITYOUWILLANDLIKEMEYOUWILLPROBABLYLEARNSOMETHINGONTHEWAYIHAVEPOSTEDASKETCHOFABULLETTHATWASSENTTOADABACKINONTHECASEFILESPAGETHATWASWHERETHESTORYBEGANANDWHATDREWADAINTAKEALOOKANDSEEIFYOUCANFIGUREOUTWHATITMIGHTHAVEMEANTITISNOTABADPLACETOSTART
                """;
        cipherText = TextFormatter.formatText(cipherText);
        plainText = TextFormatter.formatText(plainText);


        long startTime = System.currentTimeMillis();

//        System.out.println(Arrays.toString(KasiskiExamination.examine(cipherText)));
//        System.out.println(Arrays.toString(IOCPeriodAnalyser.guessPeriod(cipherText, 16)));
//        System.out.println(Arrays.deepToString(TwistMethodPeriodAnalyser.guessPeriod(cipherText, 5, 16)));

        //CipherBreakerOutput cbo1 = PortaCipherBreaker.hillClimberAttackBellaso1552(cipherText, 7);
        //System.out.println(cbo1.key + " : " + cbo1.plainText);
        System.out.println(PeriodicAffineCipher.decipher(cipherText, new int[][]{new int[]{11, 9}, new int[]{9, 8}, new int[]{7,6}, new int[]{5,4}, new int[]{3,2}}));


        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + "ms");
    }
}
