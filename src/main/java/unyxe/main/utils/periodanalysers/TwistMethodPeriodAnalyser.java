package main.utils.periodanalysers;

import main.utils.Analyser;
import main.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;

public class TwistMethodPeriodAnalyser {
    public static double[] getSignature(String text){
        double[] freq = Analyser.getMonogramStatistic(text);
        Arrays.sort(freq);
        return freq;
    }

    public static double twist(double[] sig1, double[] sig2){
        double result = 0;
        for(int i = 0; i < Constants.monogramCount/2;i++){
            result += sig1[i] - sig2[i];
        }
        for(int i = Constants.monogramCount/2; i < Constants.monogramCount;i++){
            result += sig2[i] - sig1[i];
        }
        return result;
    }
    public static double[] averageSignature(String[] slices){
        double[] average = new double[Constants.monogramCount];
        for (String slice : slices) {
            double[] signature = getSignature(slice);
            for (int j = 0; j < signature.length; j++) {
                average[j] += signature[j] / slices.length;
            }
        }
        return average;
    }

    public static double[][] guessPeriod(String text, int outputLimit){
        ArrayList<double[]> periodList = new ArrayList<>();


        for(int n = 1; n < 16;n++){
            String[] slices = IOCPeriodAnalyser.splitText(text, n);
            double[] averageSig = averageSignature(slices);
            double twist = twist(Constants.monogramSignature, averageSig);
            periodList.add(new double[]{n, twist});
        }
        double[][] res = periodList.toArray(new double[periodList.size()][]);
        Arrays.sort(res, (o1, o2) -> {
            if(o1[1]==o2[1])return 0;
            return (o1[1]-o2[1])>0?-1:1;
        });
        if(outputLimit > res.length){outputLimit = res.length;}
        double[][] limited = new double[outputLimit][2];
        System.arraycopy(res, 0, limited, 0, outputLimit);
        return limited;
    }
}
