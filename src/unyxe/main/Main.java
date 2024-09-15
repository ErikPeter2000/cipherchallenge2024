package main;

import main.utils.*;
import main.utils.periodanalysers.KasiskiExamination;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Constants.initialize(true, true);

        String cipherText = """
                IYBIDTAXYIWTNQLFCIQHESZISHGLLBPOWROLAXCGSDPGIPQXBCIWWB
                UXRWIBXXCVOTGSDCOCEJLFLQWWGVAKXDKCJBVYBWIGPZXWUBBFTGSD
                RQOEOVVMBUFOBMBLKIBCBFYTWCBWIGPXBXWKKJAPGCVX
                """;
        cipherText = TextFormatter.formatText(cipherText);



        System.out.println(Arrays.toString(KasiskiExamination.examine(cipherText)));
    }
}
