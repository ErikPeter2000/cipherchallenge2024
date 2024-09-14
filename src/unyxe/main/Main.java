package main;

import main.ciphers.*;
import main.utils.*;

public class Main {
    public static void main(String[] args) {
        //Constants.initialize(true);

        String cipherText = """
                TSLCZFRCEFRPECCETNUMDTQCLKCVFRMWTQGFMGLNFBLBDCELBDCTHDMQFQNLQ
                NLCEDCQFFQLRQWLYNCDQWNTQNCLDWELYLTNDIFXLVEZWTWCELHETHXLQHYFNN
                CELBFLSTRNNCYTUCFPLCCFCELNDBLNTWL
                """;
        cipherText = TextFormatter.formatText(cipherText);

        System.out.println(AffineCipher.decipher(cipherText, 15, 3));
    }
}
