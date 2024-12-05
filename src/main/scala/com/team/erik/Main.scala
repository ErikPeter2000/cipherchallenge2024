package com.team.erik

import scala.collection.mutable
import scala.io.Source
import scala.util.control.Breaks._
import scala.collection.parallel.CollectionConverters._
import java.nio.file.{Files, Paths, StandardOpenOption}

import com.core._
import com.core.alphabets._
import com.core.keys.KeyFactory
import com.core.languagedata.DataTable
import com.core.evolutionaryalgorithms._
import com.core.ciphers._
import com.core.extensions._
import com.core.analysers._
import com.core.cipherdata._
import com.core.extensions.BiMapExtensions._
import com.core.collections._
import com.core.extensions.StringExtensions.highlight
import com.core.progressbar.ProgressBar
import com.core.keys.KeyFactory.random
import scala.annotation.alpha
import com.core.extensions.IterableExtensions.pretty
import com.core.cipherbreakers._
import com.core.extensions.SeqExtensions.swapRandom
import spire.math.Algebraic.Expr.Sub
import scala.annotation.switch

object Main {
    def loadData(): (CipherDataBlock[Char], CipherFormatResult) = {
        val path = Paths.get(".\\resources\\text\\Orwell1984.txt")
        val text = Source.fromFile(path.toFile, "UTF-8").take(1000).mkString
        CipherDataBlock.formatAndCreate(text, UppercaseLettersNoJ)
    }

    def saveText(text: String, path: String): Unit = {
        if (Files.exists(Paths.get(path))) {
            throw new Exception(s"File already exists: $path")
        }
        Files.write(Paths.get(path), text.getBytes(), StandardOpenOption.CREATE)
    }

    def job() = {
        val ciphertext = "ISKCH DCONI YIGOJ CLAKH WYTWC IGQUT CDJOV MFBJI YXYHW BDGIS UMPWF HKUQT IIVHP AVVLX UTYUF CZIVS DOKYF GLLBH TSZNO VRCJJ WTPZL YLKTG AXVMU SKCYH YHMUL KRIGS KGZAF CZBYH ZNPPJ HFYTM JSNHP WVFAO XXISZ NLDRA GNZWV SFNPM RVLYJ GRFMM ZTVVL BABNQ SCLAK TGXYT FPWBA XUWNI YIUZM IDLIS LLHNH SKIOM DCJZA KFDHO ZFVVL MZXIR VUVMU SYUUT DSNUO XNHFY AQVHS BDHKC KZPWE OLWLK ICUMP LEFWW UHTFA YOMWC YHPWE OLMYX UBMLB HISZN LANVK CSURH KYVMI SVLVG ZGWCA BEIEG VVYYA MKGRI VHPAV VLZVL ISVUL EVVLB ABNHD OZGFQ GNZNI CXYZB NSTXS NFKLC RGZVL CKGRB GCATC IUYWL VFMJZ BJWZN MHCZS YSURI DUCRC VYCOX SRDOV PJHFY TBXSJ SUTGA GWHBU BANZT VSZNT KRCLN JTIHF IJXYH LUOMP OKSSW IOZXL XEWKH YXTBG WYBVV LAUBK OAPLE COGNF TNSEI ZHXMS GZGFD SYDKL CFCZX JOWLN WEOKF PHCOJ YUBDT GHVBK QMXVK KBALB HUBSN ZXIBM YTHJC LXLEJ OZQVE COLBA BNBGC ABEIE GHZEW LUJBI PMFMH VQANJ TIDFI TFFQW BAPFQ WBAFF FXXLM TOJNE XJHUO KHIDD UTBEO ZNPPJ SNFLL DSZNL MLZDI WHKHF UAVLZ WLALV PLULK RGJIP KIOOO KGZVL UOMEK GHREC SOMPM ZSKML ZIODN HAKAG LMMZT WHLUK CFXSN FVKSU TGAGW ZFIOD UPKVD ECLAK MZQUH JOWLV GVSKC KGRFW AUTUH SBAYW CYHPW RSZHV WVRFY WQVGA SLGFA VHHMI CXZLA TIEWP XVVLS IWVAJ UKGRR WHPTI HKLLB UZGMF UUSXZ HMJUF CZBID MXLFI OFUMH PFJID MEOLM UHTSZ NVLCO KCLKV VLMLL JODWY XGDMB ZBKWJ VLAKA GLMWV HAOYV VFKLL VZTXI YBVVL XUTKB WHPME CUVBL VVLGV KWGHI VKKSZ NUXVK LYIGF WKCCB UZSHY XKBAM RLZFL CYXYH GYOME CWFPA NSFIS TJFWC KEFGZ MPMZF TSIWV RAPVK GSTXS NFQFU OMISY LHEYQ MGFFI OYHPW EOLMH KFTKQ VECOL CKGRV WHVXY HFIFG RDEIJ TZRFC ALRSW BALES CULPU BSMYX KGDII AKCTM LBDFS SJGVR AMLKG SZNUB VQFYZ XIDJC LAKRF UZKFW JLHPV JANJX WTWYY TJMGJ LLUSD FHVFG WMLAK BGCAT CIHIW LLCFY NBUBA YOMXB GGHFF FXXLM ZIJWL KJDGI YMECH OFEZJ SYOLV WDYYR EOHGV VVVLN HAKGA HVBXS JYOME WDIYM ECUZV MTSHM HXKOU CSXUH KITXY H".replace(" ", "")
        val data = CipherDataBlock.formatAndCreate(ciphertext.reverse, UppercaseLetters)._1

        val broken = VigenereCipherBreaker.break(data)

        broken.notes = "Reversed Vigenere"
        saveText(broken.toXml, ".\\submissions\\Challenge8A.xml")
    }

    def main(args: Array[String]): Unit = {
        job()
    }
}
