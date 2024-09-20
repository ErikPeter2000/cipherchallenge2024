package main.ciphers;

import main.utils.Analyser;
import main.utils.TextUtilities;

public class MonoAlphabeticCipher {
    public static boolean isLikely(byte[] text){
        double ioc = Analyser.getIndexOfCoincedence(text, true);
        return (ioc >= 0.85) || (ioc <= 0.93);
    }

    public static byte[] inverseKey(byte[] key){
        byte[] inverseKey = new byte[key.length];
        for(int i = 0; i < key.length; i++){
            inverseKey[i] = (byte)TextUtilities.indexOf(key, (byte)i);
        }
        return inverseKey;
    }

    public static byte[] encipher(byte[] plainText, byte[] key){
        byte[] cipherText = new byte[plainText.length];
        for(int i = 0; i < plainText.length; i++){
            cipherText[i] = key[plainText[i]];
        }
        return cipherText;
    }

    public static byte[] decipher(byte[] cipherText, byte[] key){
        return encipher(cipherText, inverseKey(key));
    }
}
