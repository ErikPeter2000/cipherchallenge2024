package main.ciphers;

public class PortaCipher {
    public static int[][] tableu = new int[26][26];
    public static void generateTableu(){
        for(int i = 0; i < 13;i++){
            for(int j = 0; j < 13;j++){
                tableu[i][j] = 13 + (j - i + 26)%13;
            }
            for(int j = 13; j < 26;j++){
                tableu[i][j] = (j + i + 26)%13;
            }
        }
        for(int i = 13; i < 26;i++){
            for(int j = 0; j < 13;j++){
                tableu[i][j] = (i - j - 1)%13;
            }
            for(int j = 13; j < 26;j++){
                tableu[i][j] = 13 + (12 - j - i + 26 + 26)%13;
            }
        }
    }

    public static String encipher(String plainText, String key, int version){
        StringBuilder cipherText = new StringBuilder();
        for(int i = 0; i < plainText.length(); i++){
            if(version == 2) {
                cipherText.append((char) (tableu[(key.charAt(i % key.length()) - 65) / 2][plainText.charAt(i) - 65] + 65));
            }else if(version == 1) {
                int u1 = (13 - (key.charAt(i % key.length()) - 65) / 2)%13;
                int u2 = plainText.charAt(i) - 65;
                cipherText.append((char) (tableu[u1][u2] + 65));
            }
        }
        return cipherText.toString();
    }
    public static String decipher(String cipherText, String key, int version){
        return encipher(cipherText, key, version);
    }

    public static String encipherBellaso1552(String plainText, String key){
        StringBuilder cipherText = new StringBuilder();
        for(int i = 0; i < plainText.length(); i++){
            cipherText.append((char) (tableu[key.charAt(i % key.length()) - 65][plainText.charAt(i) - 65] + 65));
        }
        return cipherText.toString();
    }
    public static String decipherBellaco1552(String cipherText, String key){
        return encipherBellaso1552(cipherText, key);
    }
}
