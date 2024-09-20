package main.utils.maths;

import java.util.ArrayList;

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
    public static int[][] findAllFactorPairs(int n){
        ArrayList<int[]> factorPairsList = new ArrayList<>();
        for(int i = 2; i < Math.sqrt(n)+1;i++){
            if(n%i == 0){
                factorPairsList.add(new int[]{i, n/i});
                if(n/i != i)factorPairsList.add(new int[]{n/i, i});
            }
        }
        return factorPairsList.toArray(new int[0][]);
    }
}
