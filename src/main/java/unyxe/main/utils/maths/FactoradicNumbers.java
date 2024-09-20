package main.utils.maths;

public class FactoradicNumbers {
    public static int factorial(int n){
        int f = 1;
        for(int i = 2; i <= n;i++){
            f*=i;
        }
        return f;
    }

    public static byte[] toFactoradicForm(int n){
        int l = 13;
        for(int i = 0; i < 13;i++){
            if(n < factorial(i)){
                l = i;
                break;
            }
        }
        return toFactoradicForm(n, l);
    }
    public static byte[] toFactoradicForm(int n, int length){
        byte[] result = new byte[length];
        int c = 1;
        while(n>0){
            result[c-1] = (byte) (n%c);
            n /= c;
            c++;
        }
        return result;
    }
    public static int toInteger(byte[] factoradicForm){
        int result = 0;
        for(int i = 0; i < factoradicForm.length;i++){
            result += factoradicForm[i]*factorial(i);
        }
        return result;
    }
}
