package main.utils.maths;

/**
 * Factoradic numbers are a mixed radix numeral system. In factoradic, the number n is represented as a sum of factorials of increasing numbers.
 * For example, the number 463 is represented as 463 = 3! * 3 + 2! * 2 + 1! * 1.
 * This class provides methods to convert integers to factoradic form and vice versa.
 */
public class FactoradicNumbers {
    /**
     * Returns the factorial of n.
     *
     * @param n the number to calculate the factorial of
     * @return the factorial of n
     */
    public static int factorial(int n) {
        int f = 1;
        for (int i = 2; i <= n; i++) {
            f *= i;
        }
        return f;
    }

    /**
     * Returns the factoradic form of n.
     *
     * @param n the number to convert to factoradic form
     * @return the factoradic form of n
     */
    public static byte[] toFactoradicForm(int n) {
        int l = 13;
        for (int i = 0; i < 13; i++) {
            if (n < factorial(i)) {
                l = i;
                break;
            }
        }
        return toFactoradicForm(n, l);
    }

    /**
     * Returns the factoradic form of n with a specified length.
     *
     * @param n      the number to convert to factoradic form
     * @param length the length of the factoradic form
     * @return the factoradic form of n with a specified length
     */
    public static byte[] toFactoradicForm(int n, int length) {
        byte[] result = new byte[length];
        int c = 1;
        while (n > 0) {
            result[c - 1] = (byte) (n % c);
            n /= c;
            c++;
        }
        return result;
    }

    /**
     * Returns the integer value of a factoradic form.
     *
     * @param factoradicForm the factoradic form to convert to an integer
     * @return the integer value of the factoradic form
     */
    public static int toInteger(byte[] factoradicForm) {
        int result = 0;
        for (int i = 0; i < factoradicForm.length; i++) {
            result += factoradicForm[i] * factorial(i);
        }
        return result;
    }
}
