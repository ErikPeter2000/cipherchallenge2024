package main;

import main.breakers.KeywordSubstitutionCipherBreaker;
import main.utils.*;

public class Main {
    public static void main(String[] args) {
        Constants.initialize();

        String cipherText = """
                UHENTWVVENLCMCAONTAIOWTNETTYBTLIGHUEVWPFSOMYIUHINBNVIT
                BYINBXIXIVYBCHOYUHEYHOLEWNIXESTEYBTMBVEWPOFPBSUIALETOF
                MBUESIBLYHIAHNOMBUUESHOYVWLLBNVLIFELETTUHECMIGHUTEEMYE
                SENEXESUHELETTFILLEVYIUHUHITINUENTEBNVXIUBLREBWUC
                """;
        cipherText = TextFormatter.formatText(cipherText);

        KeywordSubstitutionCipherBreaker.wordlistBruteforce(cipherText).displayPlaintext();
    }
}
