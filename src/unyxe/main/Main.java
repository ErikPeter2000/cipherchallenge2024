package main;

import main.utils.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Constants.initialize(true, true);

        String cipherText = """
                THZBAROLASYZFKHFNYCEYXOQMWHXLELXLAUHNPMIAZTLVDWNNHRDOW
                SIHUCCMGNTTTCWSIHUCCMHTEEDCBUGMHZBAROLTSONNSHUDWQFZXRP
                NABMHTZDPRYHUCMMNTWADUBUKAOCCMUKELRSDREHULXIAYPECDPNZR
                OFVTRTWOCCMUKLAWGILYHNLCBRGWYNYCEYXTLVSGUFIDDMEKW
                """;
        cipherText = TextFormatter.formatText(cipherText);



        System.out.println(Arrays.toString(KasiskiExamination.examine(cipherText)));
    }
}
