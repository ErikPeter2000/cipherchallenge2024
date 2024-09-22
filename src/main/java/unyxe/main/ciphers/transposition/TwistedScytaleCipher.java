package main.ciphers.transposition;

import main.utils.maths.Permutations;

public class TwistedScytaleCipher {
    public static byte[] encipher(byte[] plainText, int width, int twist){
        int height = plainText.length/width;
        if(plainText.length%width!=0){
            plainText = TranspositionCipher.appendToPlaintext(plainText, width*height);
        }
        byte[] cipherText = new byte[plainText.length];
        byte[][] matrix = MatrixTranspositionCipher.fillTheMatrixByRows(plainText, width, height);
        for(int i = 0; i < matrix.length;i++){
            matrix[i]=Permutations.rollPermutation(matrix[i], i*twist);
        }
        for(int i = 0; i < cipherText.length;i++){
            cipherText[i] = matrix[i%height][i/height];
        }
        return cipherText;
    }
    public static byte[] decipher(byte[] cipherText, int width, int twist){
        if(cipherText.length%width!=0) throw new IllegalArgumentException("Cipher text length must be divisible by the width of the matrix.");
        byte[] plainText = new byte[cipherText.length];
        int height = cipherText.length/width;
        byte[][] matrix = MatrixTranspositionCipher.fillTheMatrixByColumns(cipherText, width, height);
        for(int i = 0; i < matrix.length;i++){
            System.arraycopy(Permutations.rollPermutation(matrix[i], i*(width-twist)), 0, plainText, width*i, width);
        }
        return plainText;
    }
}
