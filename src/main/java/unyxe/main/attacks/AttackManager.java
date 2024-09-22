package main.attacks;

import main.ciphers.monoalphabetic.MonoAlphabeticCipher;
import main.ciphers.transposition.TranspositionCipher;
import main.utils.periodanalysers.*;
import main.utils.*;

import java.util.Arrays;

public class AttackManager {
    byte[] currentCipherText = null;
    public AttackManager(String cipherText){
        currentCipherText = TextUtilities.formatAndConvertToBytes(cipherText);
    }
    public void printCurrentCipherText(){
        TextUtilities.printBytes(currentCipherText);
    }
    public void performPeriodTesting(int maxPeriod){
        System.out.println("[Period Analyser] Kasiski examination results: "+Arrays.toString(KasiskiExamination.examine(currentCipherText)));
        System.out.println("[Period Analyser] IOC analyser results: "+Arrays.toString(IOCPeriodAnalyser.guessPeriod(currentCipherText, maxPeriod)));
        System.out.println("[Period Analyser] Twist method results: "+Arrays.deepToString(TwistMethodPeriodAnalyser.guessPeriod(currentCipherText, 5, maxPeriod)));
    }
    public void guessCipherType(){
        boolean isMono = MonoAlphabeticCipher.isLikely(currentCipherText);
        boolean isTransposition = TranspositionCipher.isLikely(currentCipherText);
        System.out.println("[CipherText Type Checker] Mono-Alphabetic: " + isMono);
        System.out.println("[CipherText Type Checker] Transposition: " + isTransposition);
    }
    public void tryAllCipherTypes(int[] guessedPeriods){

    }
}
