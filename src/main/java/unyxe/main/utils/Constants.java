package main.utils;

import main.ciphers.PortaCipher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class Constants {
    public static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static Map<Integer, Character> alphabetMap = new HashMap<>();
    public static Map<Character, Integer> alphabetMapInverse = new HashMap<>();
    public static final int monogramCount = 26;
    //public static final int bigramCount = 676;
    //public static final int trigramCount = 17556;
    //public static final int tetragramCount = 274397;

    public static final String projectDir = System.getProperty("user.dir") + "/";

    public static HashMap<String, Double> monogramMap = new HashMap<>();
    public static HashMap<String, Double> bigramMap = new HashMap<>();
    public static HashMap<String, Double> trigramMap = new HashMap<>();
    public static HashMap<String, Double> tetragramMap = new HashMap<>();
    public static double[] tetragramMapFast = new double[475254];

    public static String[] wordlist;
    public static String[] smallWordlist;

    public static String[][] wordlistSplitted;
    public static String[][] smallWordlistSplitted;

    public static double[] monogramStatistics = new double[monogramCount];
    public static double[] monogramSignature = new double[monogramCount];

    public static void initialize(boolean skipPolygrams, boolean skipWordlist){
        for(int i = 0; i < alphabet.length(); i++){
            alphabetMap.put(i, alphabet.charAt(i));
            alphabetMapInverse.put(alphabet.charAt(i), i);
        }



        try {
            System.out.println("Initializing monograms...");
            initializePolygram(projectDir +"resources/polygrams/Unigram.csv", monogramMap, 1);
            System.arraycopy(monogramStatistics, 0, monogramSignature, 0, monogramCount);
            Arrays.sort(monogramSignature);
            if(!skipPolygrams)InitializePolygrams();
        }catch(IOException e){
            System.out.println("Polygram initialization failed: " + e.getMessage());
            return;
        }
        if(!skipWordlist){
            initializeWordlist();
            wordlistSplitted =  splitWordlistByLength(wordlist, 16);
        }
        initializeSmallerWordlist();
        smallWordlistSplitted = splitWordlistByLength(smallWordlist, 19);

        PortaCipher.generateTableu();
    }

    static void initializeSmallerWordlist(){
        System.out.println("Initializing small wordlist...");
        String filepath = (projectDir + "resources/englishwords/google-10000-english-no-swears.txt");
        ArrayList<String> stringList = new ArrayList<>();
        putWordsFromFile(filepath, stringList);
        smallWordlist = stringList.toArray(new String[0]);
    }

    static String[][] splitWordlistByLength(String[] wordlist, int maxLength){
        String[][] splitWordlist = new String[maxLength][];
        ArrayList<String>[] list = new ArrayList[maxLength];
        for(int i = 0; i < maxLength; i++){
            list[i] = new ArrayList<>();
        }
        for (String s : wordlist) {
            list[s.length()].add(s);
        }
        for(int i = 0; i < maxLength; i++){
            splitWordlist[i] = list[i].toArray(new String[0]);
        }
        return splitWordlist;
    }

    private static void putWordsFromFile(String filepath, ArrayList<String> stringList) {


        try (FileReader fr = new FileReader(filepath)) {
            BufferedReader br = new BufferedReader(fr);
            String r;
            while ((r = br.readLine()) != null) {
                stringList.add(r.toUpperCase());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void initializeWordlist(){
        ArrayList<String> stringList = new ArrayList<>();
        for(int i = 3; i < 16; i++){
            System.out.println("Initializing wordlist..." + i);
            String filepath = (projectDir + "resources/englishwords/Eng" + i + ".csv");
            putWordsFromFile(filepath, stringList);
        }
        wordlist = stringList.toArray(new String[0]);
    }

    static void InitializePolygrams() throws IOException {
        System.out.println("Initializing bigrams...");
        initializePolygram(projectDir +"resources/polygrams/Bigram.csv", bigramMap, 2);
        System.out.println("Initializing trigrams...");
        initializePolygram(projectDir +"resources/polygrams/Trigram.csv", trigramMap, 3);
        System.out.println("Initializing tetragrams...");
        initializePolygram(projectDir +"resources/polygrams/Tetragram.csv", tetragramMap, 4);
        for(int i = 0; i < tetragramMapFast.length;i++){
            if(tetragramMapFast[i] == 0){tetragramMapFast[i] = -20;}
        }
    }

    static double getTetragramFrequency(String tetragram){
        int i = getTetragramIndex(tetragram);
        return tetragramMapFast[i];
    }
    static int getTetragramIndex(String tetragram){
        char[] charArr = tetragram.toCharArray();
        return (charArr[0]-65)*26*26*26 + (charArr[1]-65)*26*26 + (charArr[2]-65)*26 + (charArr[3]-65);
    }

    static void initializePolygram(String path, HashMap<String, Double> map, int n) {
        {
            try (FileReader fr = new FileReader(path)) {
                BufferedReader br = new BufferedReader(fr);
                String r;
                int index = 0;
                while ((r = br.readLine()) != null) {
                    String polygram = r.split(",")[0].toUpperCase();
                    double freq = parseDouble(r.split(",")[1]);

                    if(n==4){
                        freq = Math.log(freq);
                        int ind = getTetragramIndex(polygram);
                        //map.put(tetragram, freq);
                        tetragramMapFast[ind] = freq;
                    }else{
                        map.put(polygram, freq);
                    }
                    if(n==1)monogramStatistics[index] = freq;
                    index++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
