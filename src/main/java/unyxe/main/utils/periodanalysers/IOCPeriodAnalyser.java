package main.utils.periodanalysers;

import main.utils.Analyser;

import java.util.ArrayList;
import java.util.Arrays;

public class IOCPeriodAnalyser {
    public static String[] splitText(String text, int n) {
        String[] splitted = new String[n];
        Arrays.fill(splitted, "");
        for(int i = 0; i < text.length(); i++) {
            splitted[i%n] += text.charAt(i);
        }
        return splitted;
    }

    public static double averageIOCOfSlices(String[] slices){
        double averageIOC = 0;
        for (String slice : slices) {
            averageIOC += Analyser.getIndexOfCoincedence(slice, true);
        }
        return averageIOC/slices.length;
    }

    public static int[] guessPeriod(String cipherText, int periodLimit){
        ArrayList<Integer> periodList = new ArrayList<>();
        for(int i = 1; i < periodLimit; i++){
            double averageIOC = averageIOCOfSlices(splitText(cipherText, i));
            //System.out.println(i + ":" + averageIOC);
            if(1.65<averageIOC){periodList.add(i);}
        }
        return periodList.stream().mapToInt(i -> i).toArray();
    }
}
