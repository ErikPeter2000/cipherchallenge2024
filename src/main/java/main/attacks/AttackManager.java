package main.attacks;

import main.breakers.CipherBreakerOutput;
import main.ciphers.monoalphabetic.*;
import main.ciphers.transposition.*;
import main.breakers.monoalphabetic.*;
import main.breakers.transposition.*;
import main.utils.periodanalysers.*;
import main.utils.*;

import java.util.Arrays;

/**
 * AttackManager class is responsible for managing the attacks on the given ciphertext.
 * It provides methods to perform period analysis, guess the type of the cipher, and try different breakers.
 */
public class AttackManager {
    /**
     * The current ciphertext that is being attacked.
     */
    byte[] currentCipherText;

    /**
     * Constructor for the AttackManager class.
     * @param cipherText The ciphertext that is being attacked.
     */
    public AttackManager(String cipherText) {
        currentCipherText = TextUtilities.formatAndConvertToBytes(cipherText);
    }

    /**
     * Prints the current ciphertext.
     */
    public void printCurrentCipherText() {
        TextUtilities.printBytes(currentCipherText);
    }

    /**
     * Performs period analysis on the current ciphertext.
     * @param maxPeriod The maximum period to be tested.
     */
    public void performPeriodTesting(int maxPeriod) {
        System.out.println("[Period Analyser] Kasiski examination results: " + Arrays.toString(KasiskiExamination.examine(currentCipherText)));
        System.out.println("[Period Analyser] IOC analyser results: " + Arrays.toString(IOCPeriodAnalyser.guessPeriod(currentCipherText, maxPeriod)));
        System.out.println("[Period Analyser] Twist method results: " + Arrays.deepToString(TwistMethodPeriodAnalyser.guessPeriod(currentCipherText, 5, maxPeriod)));
    }

    /**
     * Guesses the type of the cipher used to encrypt the current ciphertext.
     */
    public void guessCipherType() {
        boolean isMono = MonoAlphabeticCipher.isLikely(currentCipherText);
        boolean isTransposition = TranspositionCipher.isLikely(currentCipherText);
        System.out.println("[CipherText Type Checker] Mono-Alphabetic: " + isMono);
        System.out.println("[CipherText Type Checker] Transposition: " + isTransposition);
    }

    /**
     * Tries different monoalphabetic breakers on the current ciphertext.
     */
    public void tryMonoalphabeticBreakers() {
        CipherBreakerOutput<Integer> cboCaesar = CaesarCipherBreaker.bruteforceTF(currentCipherText);
        System.out.println("[Caesar Breaker] Key: " + cboCaesar.key.get(0) + " Fitness: " + cboCaesar.fitness + " Plaintext: " + cboCaesar.getStringPlaintext());
        CipherBreakerOutput<Integer> cboAffine = AffineCipherBreaker.bruteforceTF(currentCipherText);
        System.out.println("[Affine Breaker] Key: " + cboAffine.key.get(0) + " " + cboAffine.key.get(1) + " Fitness: " + cboAffine.fitness + " Plaintext: " + cboAffine.getStringPlaintext());
        CipherBreakerOutput<byte[]> cboKeyword = KeywordSubstitutionCipherBreaker.wordlistBruteforce(currentCipherText);
        System.out.println("[Keyword Substitution Breaker] Key: " + TextUtilities.convertToString(cboKeyword.key.get(0), Constants.alphabet) + " Filler type: " + cboKeyword.key.get(1)[0] + " Fitness: " + cboKeyword.fitness + " Plaintext: " + cboKeyword.getStringPlaintext());
        CipherBreakerOutput<byte[]> cboMono = MonoAlphabeticCipherBreaker.evolutionaryHillClimbingAttack(currentCipherText, 200, 200);
        System.out.println("[Monoalphabetic Breaker] Key: " + TextUtilities.convertToString(cboMono.key.get(0), Constants.alphabet) + " Fitness: " + cboMono.fitness + " Plaintext: " + cboMono.getStringPlaintext());
    }

    /**
     * Tries different transposition breakers on the current ciphertext.
     */
    public void tryTranspositionBreakers() {
        CipherBreakerOutput<byte[]> permutationCbo = PermutationCipherBreaker.bruteforceBlockSizeUsingHillClimb(currentCipherText, 16);
        System.out.println("[Permutation breaker] Permutation: " + Arrays.toString(permutationCbo.key.get(0)) + " Fitness: " + permutationCbo.fitness + " Plaintext: " + permutationCbo.getStringPlaintext());
        CipherBreakerOutput<int[]> matrixCbo = MatrixTranspositionCipherBreaker.bruteforce(currentCipherText);
        System.out.println("[Matrix Transposition breaker] Matrix dimensions: " + matrixCbo.key.get(0)[0] + "x" + matrixCbo.key.get(0)[1] + " Fitness: " + matrixCbo.fitness + " Plaintext: " + matrixCbo.getStringPlaintext());
    }
}
