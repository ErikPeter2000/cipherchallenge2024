package main.utils.maths;

import java.util.Arrays;

public class Permutations {
    public static byte[][] generateAllPossiblePermutations(int length){ //based on Heap's algorithm
        byte[] A = new byte[length];
        byte[][] result = new byte[FactoradicNumbers.factorial(length)][];
        for(int i = 0; i < length;i++){
            A[i] = (byte)i;
        }
        result[0] = Arrays.copyOf(A, length);
        int[] counters = new int[length];
        int i = 0;
        byte buffer;
        int pointer = 1;
        while(i < length) {
            if (counters[i] < i) {
                if (i % 2 == 0) {
                    buffer = A[0];
                    A[0] = A[i];
                    A[i] = buffer;
                } else {
                    buffer = A[counters[i]];
                    A[counters[i]] = A[i];
                    A[i] = buffer;
                }
                result[pointer] = Arrays.copyOf(A, length);
                pointer++;
                counters[i]++;
                i = 0;
            }
            if (counters[i] == i) {
                counters[i] = 0;
                i++;
            }
        }
        return result;
    }
    public static byte[] getBasePermutation(int length){
        byte[] basePermutation = new byte[length];
        for(int i = 0; i < length;i++){
            basePermutation[i] = (byte)i;
        }
        return basePermutation;
    }
    public static byte[] getPermutationFromFactoradic(byte[] factoradic){
        int length = factoradic.length;
        byte[] basePermutation = getBasePermutation(length);
        byte[] permutation = new byte[length];
        int counter = 0;
        for(int i = 0; i <length;i++){
            for(int j = 0; j < length;j++){
                if(basePermutation[j] == -1)continue;
                if(counter < factoradic[length-i-1]){counter++;continue;}
                permutation[i] = basePermutation[j];
                basePermutation[j] = -1;
                counter=0;
                break;
            }
        }
        return permutation;
    }
    public static byte[] getFactoradicFromPermutation(byte[] permutation){
        int length = permutation.length;
        byte[] factoradic = new byte[length];
        byte[] basePermutation = getBasePermutation(length);
        int counter = 0;
        for(int i = 0; i < length;i++){
            for(int j = 0; j < length;j++){
                if(basePermutation[j] == -1) continue;
                if(basePermutation[j] != permutation[i]){counter++;continue;}
                factoradic[length-i-1] = (byte)counter;
                basePermutation[j] = -1;
                counter=0;
                break;
            }
        }
        return factoradic;
    }

    public static byte[] permutationComposition(byte[] permutationA, byte[] permutationB){
        if(permutationA.length != permutationB.length) throw new IllegalArgumentException("Permutations must have equal length to be able to form a composition.");
        byte[] product = new byte[permutationA.length];
        for(int i = 0; i < permutationA.length;i++){
            product[i] = permutationB[permutationA[i]];
        }
        return product;
    }
    public static byte[] permutationInverse(byte[] permutation){
        byte[] inverse = new byte[permutation.length];
        for(int i = 0; i < inverse.length;i++){
            inverse[permutation[i]] = (byte)i;
        }
        return inverse;
    }

    public static byte[] getNthPermutationOfMObjects(int n, int m){
        return getPermutationFromFactoradic(FactoradicNumbers.toFactoradicForm(n, m));
    }
    public static int findNOfPermutation(byte[] permutation){
        return FactoradicNumbers.toInteger(getFactoradicFromPermutation(permutation));
    }
}
