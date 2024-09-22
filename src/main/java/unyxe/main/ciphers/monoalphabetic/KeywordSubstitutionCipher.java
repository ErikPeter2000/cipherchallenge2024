package main.ciphers.monoalphabetic;

import main.utils.Constants;

public class KeywordSubstitutionCipher {
    public enum KeyFiller{
        NORMAL,
        LAST_LETTER,
        ALPHABETICALLY_LAST_LETTER
    }

    public static byte[] generateKey(byte[] keyword, KeyFiller filler, boolean inverseKey) {
        boolean[] used_letters = new boolean[Constants.monogramCount];
        byte[] key = new byte[Constants.monogramCount];
        byte most_letter = keyword[0];
        byte pointer = 0;
        for (byte index : keyword) {
            if (used_letters[index]) continue;
            used_letters[index] = true;
            key[pointer] = index;
            pointer++;
            if (index > most_letter) most_letter = index;
        }
        if(filler == KeyFiller.NORMAL) {
            for(int i = 0; i < Constants.monogramCount; i++) {
                if(used_letters[i]) continue;
                used_letters[i] = true;
                key[pointer] = (byte)i;
                pointer++;
            }
        }else{
            int offset = keyword[keyword.length-1] +1;
            if(filler == KeyFiller.ALPHABETICALLY_LAST_LETTER) {
                offset = most_letter+1;
            }
            for(int i = 0; i < Constants.monogramCount; i++) {
                byte index = (byte) ((i + offset) % Constants.monogramCount);
                if(used_letters[index]) continue;
                used_letters[index] = true;
                key[pointer] = index;
                pointer++;
            }
        }
        if(inverseKey) return MonoAlphabeticCipher.inverseKey(key);
        return key;
    }

    public static byte[] encipher(byte[] plainText, byte[] keyword, KeyFiller filler, boolean inverseKey) {
        byte[] key = generateKey(keyword, filler, inverseKey);
        return MonoAlphabeticCipher.encipher(plainText, key);
    }

    public static byte[] decipher(byte[] cipherText, byte[] keyword, KeyFiller filler, boolean inverseKey) {
        byte[] key = generateKey(keyword, filler, inverseKey);
        return MonoAlphabeticCipher.decipher(cipherText, key);
    }
}
