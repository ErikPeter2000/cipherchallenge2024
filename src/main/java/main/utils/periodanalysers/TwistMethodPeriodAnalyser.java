package main.utils.periodanalysers;

import main.utils.Analyser;
import main.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is used to perform a twist method period analysis on a given cipher text.
 */
public class TwistMethodPeriodAnalyser {
    /**
     * This method is used to get the signature of a given text.
     * @param text The text to get the signature of.
     * @return An array of doubles, each representing the frequency of a letter in the text.
     */
    public static double[] getSignature(byte[] text) {
        double[] freq = Analyser.getMonogramStatistic(text);
        Arrays.sort(freq);
        return freq;
    }

    /**
     * This method is used to calculate the twist between two signatures.
     * @param sig1 The first signature.
     * @param sig2 The second signature.
     * @return A double representing the twist between the two signatures.
     */
    public static double twist(double[] sig1, double[] sig2) {
        double result = 0;
        for (int i = 0; i < Constants.monogramCount / 2; i++) {
            result += sig1[i] - sig2[i];
        }
        for (int i = Constants.monogramCount / 2; i < Constants.monogramCount; i++) {
            result += sig2[i] - sig1[i];
        }
        return result;
    }

    /**
     * This method is used to calculate the average signature of a given set of slices.
     * @param slices The slices to calculate the average signature of.
     * @return An array of doubles, each representing the average signature of a slice.
     */
    public static double[] averageSignature(byte[][] slices) {
        double[] average = new double[Constants.monogramCount];
        for (byte[] slice : slices) {
            double[] signature = getSignature(slice);
            for (int j = 0; j < signature.length; j++) {
                average[j] += signature[j] / slices.length;
            }
        }
        return average;
    }

    /**
     * This method is used to guess the period of a given text.
     * @param text The text to guess the period of.
     * @param outputLimit The maximum number of periods to output.
     * @param periodLimit The maximum period to consider.
     * @return A 2D array of doubles, each representing a period and its twist.
     */
    public static double[][] guessPeriod(byte[] text, int outputLimit, int periodLimit) {
        ArrayList<double[]> periodList = new ArrayList<>();


        for (int n = 1; n < periodLimit; n++) {
            byte[][] slices = IOCPeriodAnalyser.splitText(text, n);
            double[] averageSig = averageSignature(slices);
            double twist = twist(Constants.monogramSignature, averageSig);
            periodList.add(new double[]{n, twist});
        }
        double[][] res = periodList.toArray(new double[periodList.size()][]);
        Arrays.sort(res, (o1, o2) -> {
            if (o1[1] == o2[1]) return 0;
            return (o1[1] - o2[1]) > 0 ? -1 : 1;
        });
        if (outputLimit > res.length) {
            outputLimit = res.length;
        }
        double[][] limited = new double[outputLimit][2];
        System.arraycopy(res, 0, limited, 0, outputLimit);
        return limited;
    }
}
