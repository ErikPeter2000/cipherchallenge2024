package main.utils.periodanalysers;

import main.utils.Analyser;

import java.util.ArrayList;

/**
 * IOCPeriodAnalyser class is used to guess the period of a periodic poly alphabetic cipher text using the Index of Coincidence method.
 */
public class IOCPeriodAnalyser {
    /**
     * splitText method is used to split the cipher text into n slices.
     *
     * @param text The cipher text to be split.
     * @param n    The number of slices to split the cipher text into.
     * @return byte[][] The cipher text split into n slices.
     */
    public static byte[][] splitText(byte[] text, int n) {
        byte[][] split = new byte[n][];
        int c = (int) Math.floor((double) text.length / n);
        int d = text.length - c * n;
        for (int i = 0; i < d; i++) {
            split[i] = new byte[c + 1];
        }
        for (int i = d; i < n; i++) {
            split[i] = new byte[c];
        }
        for (int i = 0; i < text.length; i++) {
            split[i % n][(int) Math.floor((double) i / n)] = text[i];
        }
        return split;
    }

    /**
     * averageIOCOfSlices method is used to calculate the average Index of Coincidence of the slices.
     *
     * @param slices The slices of the cipher text.
     * @return double The average Index of Coincidence of the slices.
     */
    public static double averageIOCOfSlices(byte[][] slices) {
        double averageIOC = 0;
        for (byte[] slice : slices) {
            averageIOC += Analyser.getIndexOfCoincedence(slice, true);
        }
        return averageIOC / slices.length;
    }

    /**
     * guessPeriod method is used to guess the period of the cipher text.
     *
     * @param cipherText  The cipher text to guess the period of.
     * @param periodLimit The maximum period to guess.
     * @return int[] The guessed period of the cipher text.
     */
    public static int[] guessPeriod(byte[] cipherText, int periodLimit) {
        ArrayList<Integer> periodList = new ArrayList<>();
        for (int i = 1; i < periodLimit; i++) {
            double averageIOC = averageIOCOfSlices(splitText(cipherText, i));
            if (1.65 < averageIOC) { // 1.65 is the lower bound of IOC for it to be considered valid English text.
                periodList.add(i);
            }
        }
        return periodList.stream().mapToInt(i -> i).toArray();
    }
}
