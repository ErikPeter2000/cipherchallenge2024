package main.utils.periodanalysers;

import main.utils.ModularArithmetics;

import java.util.ArrayList;

public class KasiskiExamination {
    public static int[] examine(String cipherText){
        ArrayList<Integer> results = new ArrayList<>();
        for(int i = 5;i<10;i++){
            for(int j = 0; j < cipherText.length()-i+1; j++){
                String targetPattern = cipherText.substring(j,j+i);
                for(int k = j+i; k < cipherText.length()-i+1; k++){
                    String currentPattern = cipherText.substring(k,k+i);
                    if(targetPattern.equals(currentPattern)){
                        results.add(k-j);
                    }
                }
            }
        }
        int[] resultsArray = new int[results.size()];
        for(int i = 0; i < results.size(); i++){
            resultsArray[i] = results.get(i);
        }

        int[] gcds = new int[resultsArray.length*(resultsArray.length-1)/2];
        int pointer = 0;
        for(int i = 0; i < resultsArray.length; i++){
            for(int j = i+1; j < resultsArray.length; j++){
                gcds[pointer] = ModularArithmetics.gcd(resultsArray[i], resultsArray[j]);
                pointer++;
            }
        }

        ArrayList<Integer> distinctGCDs = new ArrayList<>();
        for (int gcd : gcds) {

            if (distinctGCDs.contains(gcd)) continue;
            distinctGCDs.add(gcd);
        }

        return distinctGCDs.stream().mapToInt(i -> i).toArray();
    }
}
