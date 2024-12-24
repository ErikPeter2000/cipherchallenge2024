package main.utils;

import main.core.text.Alphabet;

public class TextUtilities {
    public static String filterText(String text, Alphabet alphabet){
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < text.length(); i++){
            char c = text.charAt(i);
            if(!alphabet.contains(c))continue;
            formatted.append(c);
        }
        return formatted.toString();
    }
    public static byte[] filterAndConvertToBytes(String text, Alphabet alphabet){
        return convertToByteArray(filterText(text, alphabet), alphabet);
    }
    public static byte[] convertToByteArray(String text, Alphabet alphabet){
         byte[] bytes = new byte[text.length()];
         for (int i = 0; i < text.length(); i++){
             bytes[i] = (byte) alphabet.indexOf(text.charAt(i));
         }
         return bytes;
    }
    public static byte[][] convertToByteArrays(String[] texts, Alphabet alphabet){
        byte[][] bytes = new byte[texts.length][];
        for (int i = 0; i < texts.length; i++){
            bytes[i] = convertToByteArray(texts[i], alphabet);
        }
        return bytes;
    }
    public static String convertToString(byte[] bytes, Alphabet alphabet){
        StringBuilder formatted = new StringBuilder();
        for (byte aByte : bytes) {
            formatted.append(alphabet.charAt(aByte));
        }
        return formatted.toString();
    }
    public static void printBytes(byte[] bytes){
        System.out.println(convertToString(bytes, Alphabet.UPPER_CASE));
    }
    public static void printBytes(byte[][] bytes){
        for (byte[] aByte : bytes) {
            System.out.println(convertToString(aByte, Alphabet.UPPER_CASE));
        }
    }
    public static void printIntegers(int[] integers){
        for (int anInt : integers) {
            System.out.print(anInt + " ");
        }
        System.out.println();
    }
    public static void printIntegers(int[][] integers){
        for (int[] integer : integers) {
            printIntegers(integer);
        }
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

    public static Alphabet getAlphabet(String text){
        StringBuilder alphabet = new StringBuilder();
        for (int i = 0; i < text.length(); i++){
            char c = text.charAt(i);
            if(alphabet.indexOf(String.valueOf(c)) == -1){
                alphabet.append(c);
            }
        }
        return new Alphabet(alphabet.toString());
    }
}
