package main.ciphers;

import main.utils.Analyser;

public class MonoAlphabeticCipher {
    public static boolean likeliness(String text){
        double ioc = Analyser.getIndexOfCoincedence(text, true);
        return (ioc >= 0.85) || (ioc <= 0.93);
    }
}
