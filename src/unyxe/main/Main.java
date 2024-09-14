package main;

import main.ciphers.*;
import main.utils.*;

public class Main {
    public static void main(String[] args) {
        //Constants.initialize(true);

        String cipherText = """
                AVLIDWDPDAVLIDWDPDXBLSBDPQBRGGLAJHVQSLDINVPDQJVSSDPGZRGJ
                IDSJNPRAHZGLTDJVSBRVISDNDWDPHJPDOZKRGDRUUVQLIAQKDUSPDQJT
                NDRNFILABSQLURIIJSGDRWDSBDDKLSZHZNDQKRLPAXLIDWDPD
                """;
        cipherText = TextFormatter.formatText(cipherText);

        System.out.println(KeywordSubstitutionCipher.decipher(cipherText, "ROUNDTABLE", KeywordSubstitutionCipher.KeyFiller.LAST_LETTER, false));
    }
}
