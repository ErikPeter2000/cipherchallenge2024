package main.ciphers.transposition;

import main.utils.Constants;
import main.utils.TextUtilities;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * PermutationCipher class is used to generate permutation from keyword, guess keyword from permutation, encipher and decipher text using permutation.
 */
public class PermutationCipher {
    /**
     * generatePermutationFromKeyword method is used to generate permutation from keyword.
     *
     * @param keyword         byte[] keyword
     * @param excludeRepeated boolean excludeRepeated
     * @return byte[] permutation
     */
    public static byte[] generatePermutationFromKeyword(byte[] keyword, boolean excludeRepeated) {
        boolean[] usedLetters = new boolean[26];
        byte[] permutation;
        permutation = new byte[keyword.length];
        Arrays.fill(permutation, (byte) -1);
        byte[] keywordCopied = Arrays.copyOf(keyword, keyword.length);
        int c = permutation.length;
        for (int i = 0; i < permutation.length; i++) {
            byte smallestLetter = 100;
            byte smallestIndex = 0;
            for (int j = 0; j < keyword.length; j++) {
                if (keywordCopied[j] == -1) continue;
                if (excludeRepeated && usedLetters[keywordCopied[j]]) continue;
                if (keywordCopied[j] < smallestLetter) {
                    smallestLetter = keywordCopied[j];
                    smallestIndex = (byte) j;
                }
            }
            if (smallestLetter == 100) {
                c = i;
                break;
            }
            permutation[smallestIndex] = (byte) i;
            usedLetters[keywordCopied[smallestIndex]] = true;
            keywordCopied[smallestIndex] = -1;
        }
        byte[] actualPermutation = new byte[c];
        int pointer = 0;
        for (byte b : permutation) {
            if (b == -1) continue;
            actualPermutation[pointer] = b;
            pointer++;
        }
        return actualPermutation;
    }

    /**
     * guessKeyword method is used to guess keyword from permutation.
     *
     * @param permutation     byte[] permutation
     * @param excludeRepeated boolean excludeRepeated
     * @return byte[][] possibleKeyword
     */
    public static byte[][] guessKeyword(byte[] permutation, boolean excludeRepeated) {
        ArrayList<byte[]> possibleKeyword = new ArrayList<>();
        for (byte[] keyword : Constants.wordlist) {
            byte[] perm = generatePermutationFromKeyword(keyword, excludeRepeated);
            if (TextUtilities.isEqual(permutation, perm)) possibleKeyword.add(Arrays.copyOf(keyword, keyword.length));
        }
        return possibleKeyword.toArray(new byte[0][]);
    }

    /**
     * encipher method is used to encipher text using permutation.
     *
     * @param plainText   byte[] plainText
     * @param permutation byte[] permutation
     * @return byte[] cipherText
     */
    public static byte[] encipher(byte[] plainText, byte[] permutation) {
        plainText = TranspositionCipher.appendToPlaintext(plainText, permutation.length);
        byte[] cipherText = new byte[plainText.length];
        for (int i = 0; i < plainText.length; i++) {
            cipherText[permutation[i % permutation.length] + (i / permutation.length) * permutation.length] = plainText[i];
        }
        return cipherText;
    }

    /**
     * decipher method is used to decipher text using permutation.
     *
     * @param cipherText  byte[] cipherText
     * @param permutation byte[] permutation
     * @return byte[] plainText
     */
    public static byte[] decipher(byte[] cipherText, byte[] permutation) {
        if (cipherText.length % permutation.length != 0)
            cipherText = TranspositionCipher.appendToPlaintext(cipherText, permutation.length);
        byte[] plainText = new byte[cipherText.length];
        for (int i = 0; i < cipherText.length; i++) {
            plainText[i] = cipherText[permutation[i % permutation.length] + (i / permutation.length) * permutation.length];
        }
        return plainText;
    }
}
