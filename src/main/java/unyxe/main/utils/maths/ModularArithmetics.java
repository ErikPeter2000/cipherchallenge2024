package main.utils.maths;

public class ModularArithmetics {
    public static int gcd(int a, int b) {
        while (b != 0) {
            int t = a;
            a = b;
            b = t % b;
        }
        return a;
    }
    public static int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }
    public static boolean isCoprime(int a, int b){
        return gcd(a,b)==1;
    }
    public static int inverse(int x, int m){
        int t = 0;
        int t_ = 1;
        int r = m;
        int r_ = x;
        while(r_ != 0){
            int q = r/r_;

            int buff = t_;
            t_ = t - q * t_;
            t = buff;

            buff = r_;
            r_ = r - q * r_;
            r = buff;
        }
        if(r != 1) return -1;
        if(t < 0) t += m;
        return t;
    }
}
