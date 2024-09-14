package main.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class Constants {
    public static final String alphabet = "ABCDEFGHIJKLMONPQRSTUVWXYZ";
    public static Map<Integer, Character> alphabetMap = new HashMap<>();
    public static Map<Character, Integer> alphabetMapInverse = new HashMap<>();
    public static final int monogramCount = 26;
    //public static final int bigramCount = 676;
    //public static final int trigramCount = 17556;
    //public static final int tetragramCount = 274397;

    public static Map<String, Double> monogramMap = new HashMap<>();
    public static Map<String, Double> bigramMap = new HashMap<>();
    public static Map<String, Double> trigramMap = new HashMap<>();
    public static Map<String, Double> tetragramMap = new HashMap<>();

    public static double[] monogramStatistics = new double[monogramCount];

    public static void initialize(){
        for(int i = 0; i < alphabet.length(); i++){
            alphabetMap.put(i, alphabet.charAt(i));
            alphabetMapInverse.put(alphabet.charAt(i), i);
        }

        try {
            InitializePolygrams();
        }catch(IOException e){
            System.out.println("Polygram initialization failed: " + e.getMessage());
        }
    }

    static void InitializePolygrams() throws IOException {
        System.out.println("Initializing monograms...");
        {
            File file = new File("resources/polygrams/Unigram.csv");
            try (FileInputStream fis = new FileInputStream(file)) {
                StringBuilder polygram = new StringBuilder();
                StringBuilder floatString = new StringBuilder();
                int r;
                int index = 0;
                boolean isPolygram=true;
                while ((r = fis.read()) != -1) {
                    if(r == 44){
                        isPolygram = false;
                        floatString = new StringBuilder();
                        continue;
                    }else if(r == 10){
                        isPolygram = true;
                        monogramMap.put(polygram.toString(), parseDouble(floatString.toString()));
                        monogramStatistics[index] = parseDouble(floatString.toString());
                        index++;
                        polygram = new StringBuilder();
                        continue;
                    }else if(r==13) continue;
                    if(isPolygram){
                        polygram.append((char) r);
                    }else{
                        floatString.append((char) r);
                    }
                }
                monogramMap.put(polygram.toString(), parseDouble(floatString.toString()));
                monogramStatistics[index] = parseDouble(floatString.toString());
            }
        }
        System.out.println("Initializing bigrams...");
        {
            File file = new File("resources/polygrams/Bigram.csv");
            try (FileInputStream fis = new FileInputStream(file)) {
                StringBuilder polygram = new StringBuilder();
                StringBuilder floatString = new StringBuilder();
                int r;
                boolean isPolygram=true;
                while ((r = fis.read()) != -1) {
                    if(r == 44){
                        isPolygram = false;
                        floatString = new StringBuilder();
                        continue;
                    }else if(r == 10){
                        isPolygram = true;
                        bigramMap.put(polygram.toString(), parseDouble(floatString.toString()));
                        polygram = new StringBuilder();
                        continue;
                    }else if(r==13) continue;
                    if(isPolygram){
                        polygram.append((char) r);
                    }else{
                        floatString.append((char) r);
                    }
                }
                bigramMap.put(polygram.toString(), parseDouble(floatString.toString()));
            }
        }
        System.out.println("Initializing trigrams...");
        {
            File file = new File("resources/polygrams/Trigram.csv");
            try (FileInputStream fis = new FileInputStream(file)) {
                StringBuilder polygram = new StringBuilder();
                StringBuilder floatString = new StringBuilder();
                int r;
                boolean isPolygram=true;
                while ((r = fis.read()) != -1) {
                    if(r == 44){
                        isPolygram = false;
                        floatString = new StringBuilder();
                        continue;
                    }else if(r == 10){
                        isPolygram = true;
                        trigramMap.put(polygram.toString(), parseDouble(floatString.toString()));
                        polygram = new StringBuilder();
                        continue;
                    }else if(r==13) continue;
                    if(isPolygram){
                        polygram.append((char) r);
                    }else{
                        floatString.append((char) r);
                    }
                }
                trigramMap.put(polygram.toString(), parseDouble(floatString.toString()));
            }
        }
        System.out.println("Initializing tetragrams...");
        {
            File file = new File("resources/polygrams/Quadgram.csv");
            try (FileInputStream fis = new FileInputStream(file)) {
                StringBuilder polygram = new StringBuilder();
                StringBuilder floatString = new StringBuilder();
                int r;
                boolean isPolygram=true;
                while ((r = fis.read()) != -1) {
                    if(r == 44){
                        isPolygram = false;
                        floatString = new StringBuilder();
                        continue;
                    }else if(r == 10){
                        isPolygram = true;
                        tetragramMap.put(polygram.toString(), parseDouble(floatString.toString()));
                        polygram = new StringBuilder();
                        continue;
                    }else if(r==13) continue;
                    if(isPolygram){
                        polygram.append((char) r);
                    }else{
                        floatString.append((char) r);
                    }
                }
                tetragramMap.put(polygram.toString(), parseDouble(floatString.toString()));
            }
        }
    }
}
