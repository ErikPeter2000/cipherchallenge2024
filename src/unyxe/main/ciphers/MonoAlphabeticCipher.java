package main.ciphers;

import main.utils.Analyser;
import main.utils.Constants;

public class MonoAlphabeticCipher {
    public static boolean likeliness(String text){
        double ioc = Analyser.getIndexOfCoincedence(text, true);
        return (ioc >= 0.85) || (ioc <= 0.93);
    }

    public static String inverseKey(String key){
        StringBuilder inverseKey = new StringBuilder();
        for(int i = 0; i < Constants.monogramCount; i++){
            inverseKey.append(Constants.alphabet.charAt(key.indexOf(Constants.alphabet.charAt(i))));
        }
        return inverseKey.toString();
    }
}
