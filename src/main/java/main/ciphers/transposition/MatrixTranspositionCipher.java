package main.ciphers.transposition;

import java.util.Arrays;

public class MatrixTranspositionCipher {
    public static byte[][] fillTheMatrixByColumns(byte[] text, int width, int height) {
        byte[][] matrix = new byte[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(matrix[i], (byte) -1);
        }
        for (int i = 0; i < text.length; i++) {
            matrix[i / width][i % width] = (byte) -2;
        }
        int currentColumn = 0;
        int currentRow = 0;
        int pointer = 0;
        while (true) {
            matrix[currentRow][currentColumn] = text[pointer];
            currentRow++;
            pointer++;
            if (currentRow == height) {
                currentRow = 0;
                currentColumn++;
            }
            if (currentColumn == width) {
                break;
            }
            if (matrix[currentRow][currentColumn] == -1) {
                currentRow = 0;
                currentColumn++;
            }
            if (currentColumn == width) {
                break;
            }
            if (matrix[currentRow][currentColumn] == -1) {
                break;
            }
        }
        return matrix;
    }

    public static byte[][] fillTheMatrixByRows(byte[] text, int width, int height) {
        byte[][] matrix = new byte[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(matrix[i], (byte) -1);
        }
        for (int i = 0; i < text.length; i++) {
            matrix[i / width][i % width] = text[i];
        }
        return matrix;
    }

    public static byte[] encipher(byte[] plainText, int width, int height, boolean fillTheNulls) {
        if (fillTheNulls && plainText.length % (width * height) != 0)
            plainText = TranspositionCipher.appendToPlaintext(plainText, width * height);
        byte[][] matrix = fillTheMatrixByRows(plainText, width, height);
        byte[] cipherText = new byte[plainText.length];
        for (int i = 0; i < plainText.length; i++) {
            cipherText[i] = matrix[i % height][i / height];
        }
        return cipherText;
    }

    public static byte[] decipher(byte[] cipherText, int width, int height) {
        byte[][] matrix = fillTheMatrixByColumns(cipherText, width, height);
        byte[] plainText = new byte[cipherText.length];
        for (int i = 0; i < cipherText.length; i++) {
            plainText[i] = matrix[i / width][i % width];
        }
        return plainText;
    }
}
