package main.utils;

import main.ciphers.PortaCipher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Double.parseDouble;

public class Constants {
    public static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final int monogramCount = 26;
    //public static final int bigramCount = 676;
    //public static final int trigramCount = 17556;
    //public static final int tetragramCount = 274397;

    public static final String projectDir = System.getProperty("user.dir") + "/";

    public static double[] tetragramMapFast = new double[475254];

    public static byte[][] wordlist;
    public static byte[][] smallWordlist;

    public static byte[][][] wordlistSplitted;
    public static byte[][][] smallWordlistSplitted;

    public static double[] monogramStatistics = new double[monogramCount];
    public static double[] monogramSignature = new double[monogramCount];

    public static void initialize(){
        try {
            System.out.print("Initializing monograms...");
            initializePolygram(projectDir +"resources/polygrams/Unigram.csv", 1);
            System.arraycopy(monogramStatistics, 0, monogramSignature, 0, monogramCount);
            Arrays.sort(monogramSignature);
            InitializePolygrams();
            System.out.println("Done.");
        }catch(IOException e){
            System.out.println("Polygram initialization failed: " + e.getMessage());
            return;
        }
        initializeWordlist();
        wordlistSplitted =  splitWordlistByLength(wordlist, 16);
        initializeSmallerWordlist();
        smallWordlistSplitted = splitWordlistByLength(smallWordlist, 19);

        PortaCipher.generateTableU();
    }

    static void initializeSmallerWordlist(){
        System.out.print("Initializing small wordlist...");
        String filepath = (projectDir + "resources/englishwords/google-10000-english-no-swears.txt");
        ArrayList<String> stringList = new ArrayList<>();
        putWordsFromFile(filepath, stringList);
        smallWordlist = new byte[stringList.size()][];
        for(int i = 0; i < smallWordlist.length; i++){
            smallWordlist[i] = TextUtilities.formatAndConvertToBytes(stringList.get(i));
        }
        System.out.println("Done.");
    }

    static byte[][][] splitWordlistByLength(byte[][] wordlist, int maxLength){
        byte[][][] splitWordlist = new byte[maxLength][][];
        ArrayList<byte[]>[] list = new ArrayList[maxLength];
        for(int i = 0; i < maxLength; i++){
            list[i] = new ArrayList<>();
        }
        for (byte[] s : wordlist) {
            list[s.length].add(s);
        }
        for(int i = 0; i < maxLength; i++){
            splitWordlist[i] = list[i].toArray(new byte[0][]);
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
            String filepath = (projectDir + "resources/englishwords/Eng" + i + ".csv");
            putWordsFromFile(filepath, stringList);
        }
        wordlist = new byte[stringList.size()][];
        for(int i = 0; i < wordlist.length; i++){
            wordlist[i] = TextUtilities.formatAndConvertToBytes(stringList.get(i));
        }
    }

    static void InitializePolygrams() throws IOException {
        initializePolygram(projectDir +"resources/polygrams/Tetragram.csv", 4);
        for(int i = 0; i < tetragramMapFast.length;i++){
            if(tetragramMapFast[i] == 0){tetragramMapFast[i] = -20;}
        }
    }

    static double getTetragramFrequency(byte[] tetragramCA){
        int i = getTetragramIndex(tetragramCA);
        return tetragramMapFast[i];
    }

    static int getTetragramIndex(byte[] tetragramCA){
        return tetragramCA[0]*26*26*26 + tetragramCA[1]*26*26 + tetragramCA[2]*26 + tetragramCA[3];
    }

    static void initializePolygram(String path, int n) {
        {
            try (FileReader fr = new FileReader(path)) {
                BufferedReader br = new BufferedReader(fr);
                String r;
                int index = 0;
                while ((r = br.readLine()) != null) {
                    byte[] polygram = TextUtilities.convertToByteArray(r.split(",")[0].toUpperCase(), alphabet);
                    double freq = parseDouble(r.split(",")[1]);
                    if(n==4){
                        freq = Math.log(freq);
                        int ind = getTetragramIndex(polygram);
                        tetragramMapFast[ind] = freq;
                    }else if(n==1)monogramStatistics[index] = freq;
                    index++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
