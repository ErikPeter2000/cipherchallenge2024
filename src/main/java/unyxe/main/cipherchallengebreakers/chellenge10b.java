package main.cipherchallengebreakers;

import main.breakers.CipherBreakerOutput;
import main.breakers.monoalphabetic.MonoAlphabeticCipherBreaker;
import main.ciphers.transposition.PermutationCipher;
import main.utils.Constants;
import main.utils.TextUtilities;
import main.utils.maths.Permutations;

import java.util.Arrays;

public class chellenge10b {
    public static String decipher(String input) {
        byte[] cipherText = TextUtilities.convertToByteArray(TextUtilities.filterText(input,"\\|/"), "\\|/");

        byte[][][][] foundPermutations = bruteForcePermutations(cipherText, (byte)2, (byte)8, (byte)5);
        for(byte[][][] foundPermutation : foundPermutations){
            byte[] key = foundPermutation[0][0];
            byte[][] lengths = foundPermutation[1];
            System.out.println("Permutation Key: " + Arrays.toString(key));
            for(byte[] length : lengths){
                System.out.println("\tLength: " + length[0] + " Unique Blocks: " + length[1]);
            }
        }

        byte[] candidateKey = foundPermutations[0][0][0];
        byte candidateLength = foundPermutations[0][1][0][0];
        byte[] candidateCipherText = PermutationCipher.decipher(cipherText, candidateKey);
        byte[][] distinctBlocks = getDistinctBlocks(candidateCipherText, candidateLength);
        byte[] substitutionCipherText = convertToSubstitutionCipherText(candidateCipherText, distinctBlocks, candidateLength);
        CipherBreakerOutput<byte[]> cbo = MonoAlphabeticCipherBreaker.evolutionaryHillClimbingAttack(substitutionCipherText, 400, 400);
        return TextUtilities.convertToString(cbo.plainText, Constants.alphabet);
    }

    static byte[] convertToSubstitutionCipherText(byte[] cipherText, byte[][] distinctBlocks, byte blockLength){
        byte[] substitutionCipherText = new byte[cipherText.length/ blockLength];
        for(int i = 0; i < cipherText.length;i+=blockLength){
            for(int j = 0; j < distinctBlocks.length;j++){
                if(Arrays.equals(Arrays.copyOfRange(cipherText, i, i+blockLength), distinctBlocks[j])){
                    substitutionCipherText[i/blockLength] = (byte)(j);
                    break;
                }
            }
        }
        return substitutionCipherText;
    }

    static byte[][][][] bruteForcePermutations(byte[] cipherText, byte lowerBound, byte upperBound, byte outputLimit){
        byte[][][][] foundPermutations = new byte[outputLimit][][][];
        byte pointer = 0;
        for(int i = lowerBound; i < upperBound;i++){
            if(cipherText.length % i != 0)continue;
            byte[][] permutations = Permutations.generateAllPossiblePermutations(i);
            for(byte[] permutation : permutations){
                byte[][] testResult = testAllLengths(PermutationCipher.decipher(cipherText, permutation), (byte)4, (byte)12);
                if(testResult.length > 0){
                    foundPermutations[pointer] = new byte[][][]{new byte[][]{permutation}, testResult};
                    pointer++;
                    if(pointer == outputLimit)return Arrays.copyOf(foundPermutations, pointer);
                }
            }
        }
        return Arrays.copyOf(foundPermutations, pointer);
    }

    static byte[][] testAllLengths(byte[] cipherText, byte start, byte end){
        byte[][] possibleLengths = new byte[end - start][];
        byte pointer = 0;
        for(byte i = start; i < end;i++){
            byte[] testResult = testAmountOfUniqueBlocksOfLength(cipherText, i, (byte)23, (byte)28);
            if(testResult[0] == 1) {
                possibleLengths[pointer] = new byte[]{i, testResult[1]};
                pointer++;
            }
        }
        return Arrays.copyOf(possibleLengths, pointer);
    }
    static byte[] testAmountOfUniqueBlocksOfLength(byte[] cipherText, byte length, byte lowerBound, byte upperBound){
        byte[][] distinctBlocks = new byte[upperBound][];
        byte pointer = 0;
        for(int i = 0; i < cipherText.length;i+=length){
            byte[] block = Arrays.copyOfRange(cipherText, i, i+length);
            if(isBlockUnique(block, distinctBlocks)){
                distinctBlocks[pointer] = block;
                pointer++;
                if(pointer == upperBound)return new byte[]{0, pointer};
            }
        }
        if(pointer < lowerBound)return new byte[]{0, pointer};
        return new byte[]{1, pointer};
    }
    static byte[][] getDistinctBlocks(byte[] cipherText, byte length){
        byte[][] distinctBlocks = new byte[cipherText.length/length][];
        byte pointer = 0;
        for(int i = 0; i < cipherText.length;i+=length){
            byte[] block = Arrays.copyOfRange(cipherText, i, i+length);
            if(isBlockUnique(block, distinctBlocks)){
                distinctBlocks[pointer] = block;
                pointer++;
            }
        }
        return Arrays.copyOf(distinctBlocks, pointer);
    }

    static boolean isBlockUnique(byte[] block, byte[][] distinctBlocks){
        for(byte[] distinctBlock : distinctBlocks){
            if(Arrays.equals(block, distinctBlock))return false;
        }
        return true;
    }
}
