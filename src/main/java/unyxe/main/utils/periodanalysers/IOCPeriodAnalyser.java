package main.utils.periodanalysers;

import main.utils.Analyser;

import java.util.ArrayList;

public class IOCPeriodAnalyser {
    public static byte[][] splitText(byte[] text, int n) {
        byte[][] split = new byte[n][];
        for(int i = 0; i < n-1;i++) {
            split[i] = new byte[(int) Math.ceil((double) text.length /n)];
        }
        int m = (text.length%n);
        if(m==0) m = n;
        split[n-1] = new byte[m];
        for(int i = 0; i < text.length; i++) {
            split[i%n][(int)Math.floor((double) i /n)] = text[i];
        }
        return split;
    }

    public static double averageIOCOfSlices(byte[][] slices){
        double averageIOC = 0;
        for (byte[] slice : slices) {
            averageIOC += Analyser.getIndexOfCoincedence(slice, true);
        }
        return averageIOC/slices.length;
    }

    public static int[] guessPeriod(byte[] cipherText, int periodLimit){
        ArrayList<Integer> periodList = new ArrayList<>();
        for(int i = 1; i < periodLimit; i++){
            double averageIOC = averageIOCOfSlices(splitText(cipherText, i));
            if(1.65<averageIOC){periodList.add(i);}
        }
        return periodList.stream().mapToInt(i -> i).toArray();
    }
}
