package main.utils;

/**
 * TextUtilities class contains utility methods for text manipulation.
 * It provides methods for formatting text, filtering text, converting text to byte array and vice versa.
 */
public class TextUtilities {
    /**
     * formatText method formats the text by removing all non-alphabetic characters and converting the text to uppercase.
     * @param text The text to be formatted.
     * @return The formatted text.
     */
    public static String formatText(String text) {
        StringBuilder formatted = new StringBuilder();
        text = text.toUpperCase();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 65 || c > 90) continue;
            formatted.append(c);
        }
        return formatted.toString();
    }

    /**
     * filterText method filters the text by removing all characters that are not present in the given alphabet.
     * @param text The text to be filtered.
     * @param alphabet The alphabet to be used for filtering.
     * @return The filtered text.
     */
    public static String filterText(String text, String alphabet) {
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (alphabet.indexOf(c) == -1) continue;
            formatted.append(c);
        }
        return formatted.toString();
    }

    /**
     * filterAndConvertToBytes method filters the text by removing all characters that are not present in the given alphabet
     * and converts the filtered text to byte array.
     * @param text The text to be filtered and converted.
     * @param alphabet The alphabet to be used for filtering.
     * @return The byte array of the filtered text.
     */
    public static byte[] filterAndConvertToBytes(String text, String alphabet) {
        return convertToByteArray(filterText(text, alphabet), alphabet);
    }

    /**
     * formatAndConvertToBytes method formats the text by removing all non-alphabetic characters and converting the text to uppercase
     * and converts the formatted text to byte array.
     * @param text The text to be formatted and converted.
     * @return The byte array of the formatted text.
     */
    public static byte[] formatAndConvertToBytes(String text) {
        return convertToByteArray(formatText(text), Constants.alphabet);
    }

    /**
     * convertToByteArray method converts the text to byte array using the given alphabet.
     * @param text The text to be converted.
     * @param alphabet The alphabet to be used for conversion.
     * @return The byte array of the text.
     */
    public static byte[] convertToByteArray(String text, String alphabet) {
        byte[] bytes = new byte[text.length()];
        for (int i = 0; i < text.length(); i++) {
            bytes[i] = (byte) alphabet.indexOf(text.charAt(i));
        }
        return bytes;
    }

    /**
     * convertToByteArrays method converts the array of texts to array of byte arrays using the given alphabet.
     * @param texts The array of texts to be converted.
     * @param alphabet The alphabet to be used for conversion.
     * @return The array of byte arrays of the texts.
     */
    public static byte[][] convertToByteArrays(String[] texts, String alphabet) {
        byte[][] bytes = new byte[texts.length][];
        for (int i = 0; i < texts.length; i++) {
            bytes[i] = convertToByteArray(texts[i], alphabet);
        }
        return bytes;
    }

    /**
     * convertToString method converts the byte array to string using the given alphabet.
     * @param bytes The byte array to be converted.
     * @param alphabet The alphabet to be used for conversion.
     * @return The string of the byte array.
     */
    public static String convertToString(byte[] bytes, String alphabet) {
        StringBuilder formatted = new StringBuilder();
        for (byte aByte : bytes) {
            formatted.append(alphabet.charAt(aByte));
        }
        return formatted.toString();
    }

    /**
     * printBytes method prints the byte array using the given alphabet.
     * @param bytes The byte array to be printed.
     */
    public static void printBytes(byte[] bytes) {
        System.out.println(convertToString(bytes, Constants.alphabet));
    }

    /**
     * printBytes method prints the array of byte arrays using the given alphabet.
     * @param bytes The array of byte arrays to be printed.
     */
    public static void printBytes(byte[][] bytes) {
        for (byte[] aByte : bytes) {
            System.out.println(convertToString(aByte, Constants.alphabet));
        }
    }

    /**
     * printIntegers method prints the array of integers.
     * @param integers The array of integers to be printed.
     */
    public static void printIntegers(int[] integers) {
        for (int anInt : integers) {
            System.out.print(anInt + " ");
        }
        System.out.println();
    }

    /**
     * printIntegers method prints the two-dimensional array of integers.
     * @param integers The two-dimensional array of integers to be printed.
     */
    public static void printIntegers(int[][] integers) {
        for (int[] integer : integers) {
            printIntegers(integer);
        }
    }

    /**
     * isEqual method checks if the two byte arrays are equal.
     * @param text1 The first byte array.
     * @param text2 The second byte array.
     * @return True if the two byte arrays are equal, false otherwise.
     */
    public static boolean isEqual(byte[] text1, byte[] text2) {
        if (text1.length != text2.length) return false;
        for (int i = 0; i < text1.length; i++) {
            if (text1[i] != text2[i]) return false;
        }
        return true;
    }

    /**
     * indexOf method returns the index of the target byte in the byte array.
     * @param array The byte array.
     * @param targetByte The target byte.
     * @return The index of the target byte in the byte array.
     */
    public static int indexOf(byte[] array, byte targetByte) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == targetByte) return i;
        }
        return -1;
    }
}
