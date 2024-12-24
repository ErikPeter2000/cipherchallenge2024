package main.core.text;

public class Alphabet {
    public static final Alphabet UPPER_CASE = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    public static final Alphabet UPPER_CASE_WITH_SPACE = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ ");
    public static final Alphabet LOWER_CASE = new Alphabet("abcdefghijklmnopqrstuvwxyz");
    public static final Alphabet LOWER_CASE_WITH_SPACE = new Alphabet("abcdefghijklmnopqrstuvwxyz ");
    public static final Alphabet NUMBERS = new Alphabet("0123456789");
    public static final Alphabet ALPHANUMERIC = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
    public static final Alphabet ALPHANUMERIC_WITH_SPACE = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ");
    public static final Alphabet MORSE_CODE = new Alphabet(" -.");

    final String alphabet;
    public Alphabet(String alphabet){
        this.alphabet = alphabet;
    }
    public String getAlphabet() {
        return alphabet;
    }
    public boolean contains(char c){
        return alphabet.indexOf(c) != -1;
    }
    public int indexOf(char c){
        return alphabet.indexOf(c);
    }
    public char charAt(int i){
        return alphabet.charAt(i);
    }
}
