package main.utils;

public class TextUtilities {
    public static String formatText(String text){
        StringBuilder formatted = new StringBuilder();
        text = text.toUpperCase();
        for (int i = 0; i < text.length(); i++){
            char c = text.charAt(i);
            if(c <65 || c > 90)continue;
            formatted.append(c);
        }
        return formatted.toString();
    }
    public static byte[] convertToByteArray(String text, String alphabet){
         byte[] bytes = new byte[text.length()];
         for (int i = 0; i < text.length(); i++){
             bytes[i] = (byte) alphabet.indexOf(text.charAt(i));
         }
         return bytes;
    }
    public static String convertToString(byte[] bytes, String alphabet){
        StringBuilder formatted = new StringBuilder();
        for (byte aByte : bytes) {
            formatted.append(alphabet.charAt(aByte));
        }
        return formatted.toString();
    }
    public static boolean isEqual(byte[] text1, byte[] text2){
        if(text1.length != text2.length) return false;
        for(int i = 0; i < text1.length; i++){
            if(text1[i] != text2[i]) return false;
        }
        return true;
    }
    public static int indexOf(byte[] array, byte targetByte){
        for(int i = 0; i < array.length; i++){
            if(array[i] == targetByte) return i;
        }
        return -1;
    }
}
