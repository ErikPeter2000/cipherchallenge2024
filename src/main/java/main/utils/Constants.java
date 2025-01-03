package main.utils;

import main.ciphers.periodicpolyalphabetic.PortaCipher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Double.parseDouble;

/**
 * Constants class contains all the constants used in the project.
 * It also initializes the monograms and polygrams.
 */
public class Constants {
    /**
     * The alphabet used in the project.
     */
    public static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * The number of monograms in the alphabet.
     */
    public static final int monogramCount = 26;
    //public static final int bigramCount = 676;
    //public static final int trigramCount = 17556;
    //public static final int tetragramCount = 274397;

    /**
     * The project directory.
     */
    public static final String projectDir = System.getProperty("user.dir") + "/";

    /**
     * The tetragram map.
     */
    public static double[] tetragramMapFast = new double[475254];

    /**
     * The full wordlist.
     */
    public static byte[][] wordlist;

    /**
     * The small wordlist.
     */
    public static byte[][] smallWordlist;

    /**
     * The wordlist split by length.
     */
    public static byte[][][] wordlistSplit;
    /**
     * The small wordlist split by length.
     */
    public static byte[][][] smallWordlistSplit;

    /**
     * The monogram statistics.
     */
    public static double[] monogramStatistics = new double[monogramCount];
    /**
     * The monogram signature.
     */
    public static double[] monogramSignature = new double[monogramCount];

    /**
     * Initializes the monograms and polygrams.
     */
    public static void initialize() {
        try {
            System.out.print("Initializing monograms...");
            initializePolygram(projectDir + "resources/polygrams/Unigram.csv", 1);
            System.arraycopy(monogramStatistics, 0, monogramSignature, 0, monogramCount);
            Arrays.sort(monogramSignature);
            InitializePolygrams();
            System.out.println("Done.");
        } catch (IOException e) {
            System.out.println("Polygram initialization failed: " + e.getMessage());
            return;
        }
        initializeWordlist();
        wordlistSplit = splitWordlistByLength(wordlist, 16);
        initializeSmallerWordlist();
        smallWordlistSplit = splitWordlistByLength(smallWordlist, 19);

        PortaCipher.generateTableU();
    }

    /**
     * Initializes the smaller wordlist.
     */
    static void initializeSmallerWordlist() {
        System.out.print("Initializing small wordlist...");
        String filepath = (projectDir + "resources/englishwords/google-10000-english-no-swears.txt");
        ArrayList<String> stringList = new ArrayList<>();
        putWordsFromFile(filepath, stringList);
        smallWordlist = new byte[stringList.size()][];
        for (int i = 0; i < smallWordlist.length; i++) {
            smallWordlist[i] = TextUtilities.formatAndConvertToBytes(stringList.get(i));
        }
        System.out.println("Done.");
    }

    /**
     * Splits the wordlist by length.
     *
     * @param wordlist  The wordlist to split.
     * @param maxLength The maximum length of the wordlist.
     * @return The split wordlist.
     */
    static byte[][][] splitWordlistByLength(byte[][] wordlist, int maxLength) {
        byte[][][] splitWordlist = new byte[maxLength][][];
        ArrayList<byte[]>[] list = new ArrayList[maxLength];
        for (int i = 0; i < maxLength; i++) {
            list[i] = new ArrayList<>();
        }
        for (byte[] s : wordlist) {
            list[s.length].add(s);
        }
        for (int i = 0; i < maxLength; i++) {
            splitWordlist[i] = list[i].toArray(new byte[0][]);
        }
        return splitWordlist;
    }

    /**
     * Puts words from a file into a list.
     *
     * @param filepath   The path of the file.
     * @param stringList The list to put the words into.
     */
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

    /**
     * Initializes the wordlist.
     */
    static void initializeWordlist() {
        ArrayList<String> stringList = new ArrayList<>();
        for (int i = 3; i < 16; i++) {
            String filepath = (projectDir + "resources/englishwords/Eng" + i + ".csv");
            putWordsFromFile(filepath, stringList);
        }
        wordlist = new byte[stringList.size()][];
        for (int i = 0; i < wordlist.length; i++) {
            wordlist[i] = TextUtilities.formatAndConvertToBytes(stringList.get(i));
        }
    }

    /**
     * Initializes the polygrams.
     *
     * @throws IOException If the file is not found.
     */
    static void InitializePolygrams() throws IOException {
        initializePolygram(projectDir + "resources/polygrams/Tetragram.csv", 4);
        for (int i = 0; i < tetragramMapFast.length; i++) {
            if (tetragramMapFast[i] == 0) {
                tetragramMapFast[i] = -20;
            }
        }
    }

    /**
     * Gets the tetragram frequency.
     *
     * @param tetragramCA The tetragram to get the frequency of.
     * @return The frequency of the tetragram.
     */
    static double getTetragramFrequency(byte[] tetragramCA) {
        int i = getTetragramIndex(tetragramCA);
        return tetragramMapFast[i];
    }

    /**
     * Gets the index of a tetragram.
     *
     * @param tetragramCA The tetragram to get the index of.
     * @return The index of the tetragram.
     */
    static int getTetragramIndex(byte[] tetragramCA) {
        return tetragramCA[0] * 26 * 26 * 26 + tetragramCA[1] * 26 * 26 + tetragramCA[2] * 26 + tetragramCA[3];
    }

    /**
     * Initializes the polygram.
     *
     * @param path The path of the polygram.
     * @param n    The number of polygrams.
     */
    static void initializePolygram(String path, int n) {
        {
            try (FileReader fr = new FileReader(path)) {
                BufferedReader br = new BufferedReader(fr);
                String r;
                int index = 0;
                while ((r = br.readLine()) != null) {
                    byte[] polygram = TextUtilities.convertToByteArray(r.split(",")[0].toUpperCase(), alphabet);
                    double freq = parseDouble(r.split(",")[1]);
                    if (n == 4) {
                        freq = Math.log(freq);
                        int ind = getTetragramIndex(polygram);
                        tetragramMapFast[ind] = freq;
                    } else if (n == 1) monogramStatistics[index] = freq;
                    index++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
