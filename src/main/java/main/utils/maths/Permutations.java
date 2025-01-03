package main.utils.maths;

import java.util.Arrays;

/**
 * This class provides functions for operations or generation of permutations.
 */
public class Permutations {

    /**
     * Generates all possible permutations of a given length.
     * @param length the length of the permutations
     * @return an array of all possible permutations
     */
    public static byte[][] generateAllPossiblePermutations(int length) { //based on Heap's algorithm
        byte[] A = new byte[length];
        byte[][] result = new byte[FactoradicNumbers.factorial(length)][];
        for (int i = 0; i < length; i++) {
            A[i] = (byte) i;
        }
        result[0] = Arrays.copyOf(A, length);
        int[] counters = new int[length];
        int i = 0;
        byte buffer;
        int pointer = 1;
        while (i < length) {
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

    /**
     * Generates all possible permutations of a given length.
     * @param length the length of the permutations
     * @return an array of all possible permutations
     */
    public static byte[] getBasePermutation(int length) {
        byte[] basePermutation = new byte[length];
        for (int i = 0; i < length; i++) {
            basePermutation[i] = (byte) i;
        }
        return basePermutation;
    }

    /**
     * Generates a permutation from a factoradic number.
     * @param factoradic the factoradic number
     * @return the permutation
     */
    public static byte[] getPermutationFromFactoradic(byte[] factoradic) {
        int length = factoradic.length;
        byte[] basePermutation = getBasePermutation(length);
        byte[] permutation = new byte[length];
        int counter = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (basePermutation[j] == -1) continue;
                if (counter < factoradic[length - i - 1]) {
                    counter++;
                    continue;
                }
                permutation[i] = basePermutation[j];
                basePermutation[j] = -1;
                counter = 0;
                break;
            }
        }
        return permutation;
    }

    /**
     * Generates a factoradic number from a permutation.
     * @param permutation the permutation
     * @return the factoradic number
     */
    public static byte[] getFactoradicFromPermutation(byte[] permutation) {
        int length = permutation.length;
        byte[] factoradic = new byte[length];
        byte[] basePermutation = getBasePermutation(length);
        int counter = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (basePermutation[j] == -1) continue;
                if (basePermutation[j] != permutation[i]) {
                    counter++;
                    continue;
                }
                factoradic[length - i - 1] = (byte) counter;
                basePermutation[j] = -1;
                counter = 0;
                break;
            }
        }
        return factoradic;
    }

    /**
     * Composes two permutations.
     * @param permutationA the first permutation
     * @param permutationB the second permutation
     * @return the composition of the two permutations
     */
    public static byte[] permutationComposition(byte[] permutationA, byte[] permutationB) {
        if (permutationA.length != permutationB.length)
            throw new IllegalArgumentException("Permutations must have equal length to be able to form a composition.");
        byte[] product = new byte[permutationA.length];
        for (int i = 0; i < permutationA.length; i++) {
            product[i] = permutationB[permutationA[i]];
        }
        return product;
    }

    /**
     * Inverts a permutation.
     * @param permutation the permutation
     * @return the inverse of the permutation
     */
    public static byte[] permutationInverse(byte[] permutation) {
        byte[] inverse = new byte[permutation.length];
        for (int i = 0; i < inverse.length; i++) {
            inverse[permutation[i]] = (byte) i;
        }
        return inverse;
    }

    /**
     * Generates the nth permutation of m objects.
     * @param n the index of the permutation
     * @param m the number of objects
     * @return the nth permutation of m objects
     */
    public static byte[] getNthPermutationOfMObjects(int n, int m) {
        return getPermutationFromFactoradic(FactoradicNumbers.toFactoradicForm(n, m));
    }

    /**
     * Finds the index of a permutation.
     * @param permutation the permutation
     * @return the index of the permutation
     */
    public static int findNOfPermutation(byte[] permutation) {
        return FactoradicNumbers.toInteger(getFactoradicFromPermutation(permutation));
    }

    /**
     * Generates a random permutation of m objects.
     * @param m the number of objects
     * @return a random permutation of m objects
     */
    public static byte[] getRandomPermutation(int m) {
        return getNthPermutationOfMObjects(Random.random.nextInt(FactoradicNumbers.factorial(m)), m);
    }

    /**
     * Swaps two random digits of a permutation.
     * @param permutation the permutation
     * @return the permutation with two random digits swapped
     */
    public static byte[] swapTwoRandomDigits(byte[] permutation) {
        byte[] newPermutation = Arrays.copyOf(permutation, permutation.length);
        int x = Random.random.nextInt(permutation.length);
        int y;
        do {
            y = Random.random.nextInt(permutation.length);
        } while (y == x);
        newPermutation[x] = permutation[y];
        newPermutation[y] = permutation[x];
        return newPermutation;
    }

    /**
     * Rolls a permutation by a given number of steps.
     * @param permutation the permutation
     * @param numberOfSteps the number of steps
     * @return the rolled permutation
     */
    public static byte[] rollPermutation(byte[] permutation, int numberOfSteps) {
        if (numberOfSteps >= permutation.length) numberOfSteps %= permutation.length;
        byte[] newPermutation = new byte[permutation.length];
        if (numberOfSteps == 0) return Arrays.copyOf(permutation, permutation.length);
        System.arraycopy(permutation, numberOfSteps, newPermutation, 0, permutation.length - numberOfSteps);
        System.arraycopy(permutation, 0, newPermutation, permutation.length - numberOfSteps, numberOfSteps);
        return newPermutation;
    }

    /**
     * Rolls a permutation randomly.
     * @param permutation the permutation
     * @return the rolled permutation
     */
    public static byte[] rollPermutationRandomly(byte[] permutation) {
        return rollPermutation(permutation, Random.random.nextInt(permutation.length));
    }
}
