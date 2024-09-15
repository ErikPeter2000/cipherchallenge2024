package main.ciphers;

import main.utils.Constants;

public class KeywordSubstitutionCipher {
    public enum KeyFiller{
        NORMAL,
        LAST_LETTER,
        ALPHABETICALLY_LAST_LETTER
    }

    public static String generateKey(String keyword, KeyFiller filler, boolean inverseKey) {
        boolean[] used_letters = new boolean[Constants.monogramCount];
        StringBuilder key = new StringBuilder();
        int most_letter = keyword.charAt(0) - 65;
        for(int i = 0; i < keyword.length(); i++) {
            int index = keyword.charAt(i) - 65;
            if(used_letters[index]) continue;
            used_letters[index] = true;
            key.append((char) (index + 65));
            if(index > most_letter) most_letter = index;
        }
        switch(filler) {
            case NORMAL:
            {
                for(int i = 0; i < Constants.monogramCount; i++) {
                    if(used_letters[i]) continue;
                    used_letters[i] = true;
                    key.append((char) (i + 65));
                }
                break;
            }
            case LAST_LETTER:
            {
                int offset = keyword.charAt(keyword.length()-1) - 65 +1;
                for(int i = 0; i < Constants.monogramCount; i++) {
                    int index = (i + offset) % Constants.monogramCount;
                    if(used_letters[index]) continue;
                    used_letters[index] = true;
                    key.append((char) (index + 65));
                }
                break;
            }
            case ALPHABETICALLY_LAST_LETTER:
            {
                int offset = most_letter+1;
                for(int i = 0; i < Constants.monogramCount; i++) {
                    int index = (i + offset) % Constants.monogramCount;
                    if(used_letters[index]) continue;
                    used_letters[index] = true;
                    key.append((char) (index + 65));
                }
                break;
            }
        }
        if(inverseKey)return MonoAlphabeticCipher.inverseKey(key.toString());
        return key.toString();
    }

    public static String encipher(String plainText, String keyword, KeyFiller filler, boolean inverseKey) {
        String key = generateKey(keyword, filler, inverseKey);
        return MonoAlphabeticCipher.encipher(plainText, key);
    }

    public static String decipher(String cipherText, String keyword, KeyFiller filler, boolean inverseKey) {
        String key = generateKey(keyword, filler, inverseKey);
        return MonoAlphabeticCipher.decipher(cipherText, key);
    }
}
