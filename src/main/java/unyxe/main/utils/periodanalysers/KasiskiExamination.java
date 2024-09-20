package main.utils.periodanalysers;

import main.utils.maths.ModularArithmetics;
import main.utils.TextUtilities;

import java.util.ArrayList;
import java.util.Arrays;

public class KasiskiExamination {
    public static int[] examine(byte[] cipherText){
        ArrayList<Integer> results = getGaps(cipherText);
        int[] resultsArray = new int[results.size()];
        for(int i = 0; i < results.size(); i++){
            resultsArray[i] = results.get(i);
        }

        int[] GCDs = new int[resultsArray.length*(resultsArray.length-1)/2];
        int pointer = 0;
        for(int i = 0; i < resultsArray.length; i++){
            for(int j = i+1; j < resultsArray.length; j++){
                GCDs[pointer] = ModularArithmetics.gcd(resultsArray[i], resultsArray[j]);
                pointer++;
            }
        }

        ArrayList<Integer> distinctGCDs = new ArrayList<>();
        for (int gcd : GCDs) {

            if (distinctGCDs.contains(gcd)) continue;
            distinctGCDs.add(gcd);
        }

        return distinctGCDs.stream().mapToInt(i -> i).toArray();
    }

    private static ArrayList<Integer> getGaps(byte[] cipherText) {
        ArrayList<Integer> results = new ArrayList<>();
        for(int i = 5;i<10;i++){
            for(int j = 0; j < cipherText.length-i+1; j++){
                byte[] targetPattern = Arrays.copyOfRange(cipherText, j, j+i);
                for(int k = j+i; k < cipherText.length-i+1; k++){
                    byte[] currentPattern = Arrays.copyOfRange(cipherText, k, k+i);
                    if(TextUtilities.isEqual(targetPattern, currentPattern)){
                        results.add(k-j);
                    }
                }
            }
        }
        return results;
    }
}
