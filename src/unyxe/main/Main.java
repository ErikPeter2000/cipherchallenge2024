package main;

import main.ciphers.*;
import main.utils.*;

public class Main {
    public static void main(String[] args) {
        Constants.initialize();

        String cipherText = "YBY JMTCJYAC YLB AFYPJCQ ZYZZYEC YPC RUM MD RFC KMQR GKNMPRYLR KCKZCPQ MD MSP AMKKSLGRW. RFCW DCYRSPCB GL RFC DGPQR LYRGMLYJ AGNFCP AFYJJCLEC, YLB YEYGL QMKC WCYPQ JYRCP UFCL UC CVNJMPCB RFCGP CYPJW KCCRGLE. GL RFGQ YBTCLRSPC UC YPC PCRSPLGLE RM RFC CLB MD FCP JGDC, YR JCYQR RFC NYPR PCAMPBCB GL RFC YPAFGTCQ YR ZMQQ FCYBOSYPRCPQ. G DMSLB RFGQ QRMPW ZW YAAGBCLR UFCL G UYQ PCQCYPAFGLE RFC PMJC MD RFC PMWYJ DYKGJW GL MSP LYRGMLYJ QCASPGRW. KYLW MD RFCK QCPTCB GL RFC KGJGRYPW YLB YPC DYKGJGYP UGRF AMBCQ YLB AGNFCPQ, YLB G UMLBCPCB FMU DYP ZYAI RFYR GLRCPCQR UCLR. RFYR UYQ FMU G QRSKZJCB YAPMQQ Y LMRC DPMK NPGLAC YJZCPR RM JMPB NYJKCPQRML YLB RFYR QRYPRCB KC BMUL RFC PYZZGR FMJC RFYR JCB SQ FCPC. G FMNC WMS CLHMW GR YQ KSAF YQ G BGB. UC UGJJ KCCR QMKC DYQAGLYRGLE LCU AFYPYARCPQ ML RFC UYW, YLB RFC QRMPW RYICQ Y RUGQR MP RUM. MD AMSPQC, G ILMU FMU GR CLBQ, WMS BML\\'R WCR, ZSR GD WMS QRGAI UGRF GR WMS UGJJ, YLB, JGIC KC, WMS UGJJ NPMZYZJW JCYPL QMKCRFGLE ML RFC UYW. G FYTC NMQRCB Y QICRAF MD Y ZSJJCR RFYR UYQ QCLR RM YBY ZYAI GL 1851 ML RFC AYQC DGJCQ NYEC. RFYR UYQ UFCPC RFC QRMPW ZCEYL YLB UFYR BPCU YBY GL. RYIC Y JMMI YLB QCC GD WMS AYL DGESPC MSR UFYR GR KGEFR FYTC KCYLR. GR GQ LMR Y ZYB NJYAC RM QRYPR.";
        cipherText = TextFormatter.formatText(cipherText);

        System.out.println(cipherText);
        System.out.println(MonoAlphabeticCipher.likeliness(cipherText));
        for(int i = 0; i < 26; i++) {
            //System.out.println(CaesarCipher.decode(cipherText, i));
            System.out.println(FitnessCalculator.MonogramChiFitness(CaesarCipher.decode(cipherText, i)) +" " +FitnessCalculator.MonogramABVFitness(CaesarCipher.decode(cipherText, i)) + " " + FitnessCalculator.TetragramFitness(CaesarCipher.decode(cipherText, i)) + " " + Analyser.getIndexOfCoincedence(CaesarCipher.decode(cipherText, i), true) + " " + Analyser.getEntropy(CaesarCipher.decode(cipherText, i)));
        }

    }
}
