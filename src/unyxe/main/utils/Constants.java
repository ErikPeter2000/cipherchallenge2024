package main.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    public static Map<Integer, Character> alphabetMap = new HashMap<>();

    public static void initialize(){

        for(int i = 0; i < alphabet.length(); i++){
            alphabetMap.put(i, alphabet.charAt(i));
        }

    }
}
