package main.utils.maths;

import java.util.ArrayList;

/**
 * ModularArithmetics class contains methods for modular arithmetic operations.
 * <p>
 * gcd(int a, int b) - returns the greatest common divisor of two numbers.
 * lcm(int a, int b) - returns the least common multiple of two numbers.
 * isCoprime(int a, int b) - returns true if two numbers are coprime.
 * inverse(int x, int m) - returns the modular multiplicative inverse of x modulo m.
 * findAllFactorPairs(int n) - returns all factor pairs of a number.
 */
public class ModularArithmetics {
    /**
     * Returns the greatest common divisor of two numbers.
     *
     * @param a the first number
     * @param b the second number
     * @return the greatest common divisor of two numbers
     */
    public static int gcd(int a, int b) {
        while (b != 0) {
            int t = a;
            a = b;
            b = t % b;
        }
        return a;
    }

    /**
     * Returns the least common multiple of two numbers.
     *
     * @param a the first number
     * @param b the second number
     * @return the least common multiple of two numbers
     */
    public static int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    /**
     * Returns true if two numbers are coprime.
     *
     * @param a the first number
     * @param b the second number
     * @return true if two numbers are coprime
     */
    public static boolean isCoprime(int a, int b) {
        return gcd(a, b) == 1;
    }

    /**
     * Returns the modular multiplicative inverse of x modulo m.
     *
     * @param x the number
     * @param m the modulo
     * @return the modular multiplicative inverse of x modulo m
     */
    public static int inverse(int x, int m) {
        int t = 0;
        int t_ = 1;
        int r = m;
        int r_ = x;
        while (r_ != 0) {
            int q = r / r_;

            int buff = t_;
            t_ = t - q * t_;
            t = buff;

            buff = r_;
            r_ = r - q * r_;
            r = buff;
        }
        if (r != 1) return -1;
        if (t < 0) t += m;
        return t;
    }

    /**
     * Returns all factor pairs of a number.
     *
     * @param n the number
     * @return all factor pairs of a number
     */
    public static int[][] findAllFactorPairs(int n) {
        ArrayList<int[]> factorPairsList = new ArrayList<>();
        for (int i = 2; i < Math.sqrt(n) + 1; i++) {
            if (n % i == 0) {
                factorPairsList.add(new int[]{i, n / i});
                if (n / i != i) factorPairsList.add(new int[]{n / i, i});
            }
        }
        return factorPairsList.toArray(new int[0][]);
    }
}
