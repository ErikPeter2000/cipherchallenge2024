package com.team.erik

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

object Main {
    def loadData(): (CipherDataBlock[Char], CipherFormatResult) = {
        val path = Paths.get(".\\resources\\text\\Orwell1984.txt")
        val text = Source.fromFile(path.toFile, "UTF-8").take(1000).mkString
        CipherDataBlock.formatAndCreate(text, UppercaseLettersNoJ)
    }

    def saveText(text: String, path: String): Unit = {
        Files.write(Paths.get(path), text.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
    }

    def job() = {
        val ciphertext = """MZDFP OCFFO YFQZY ZMXDE CTCWZ HOWCS OCYTX DSPCD WOEUC UUCNO PZDEW OKFZI ODEOC EFPZD EWOKE GDDOK OYNWC YTXKT OCDSZ GYFOE EXDUC UUCNO QPCHO SZXAW OFOTX KQYHO EFQNC FQZYE CFFPO QXAOD QCWCD XESGE FZXEI CDOPZ GEOCY TIQWW DOFGD YMZDF PIQFP UGFVY ZIQYN RGEFP ZIWZY NFPCF RZGDY OKXCK FCVOC YTPZI DOXCD VCUWK EIQMF KZGDD ZKCWX CQWSC YUOQF PZGNP FQFUO EFFZE OYTKZ GXKDO AZDFU KWOFF ODXKY OIMDQ OYTEQ YWQHO DAZZW IODOD OCWPO WAMGW CYTAD ZHQTO TXOIQ FPFPO EPQAA QYNAC AODEC WFPZG NPQCX YZFEG DOPZI XGSPC EEQEF CYSOF POKIQ WWADZ HQTOC WWQSC YEOOQ EFPCF FPOSD CFOEC WWWOM FFPOG YQFOT EFCFO EFZNO FPODC YTOCS PIOQN POTFP OECXO EGNNO EFQYN FPCFY ZFPQY NPCTU OOYFC VOYQM QYTQF XQNPF KGYWQ VOWKF PCFCY KZYOQ YFODO EFOTQ YEFOC WQYNF POSZY FOYFE ZMCEQ YNWOS DCFOI ZGWTF CVOFP OFDZG UWOFZ DOAWC SOFPO XIQFP CSCDO MGWWK SCWQU DCFOT IOQNP FZMDG UUWOZ DZFPO DYZYE OYEOC YTQYC YKSCE OFPOE OCWPC TUOOY DOAWC SOTFP CFEOO XEWQV OCYCI MGWWZ FZMFD ZGUWO MZDCM OIDQM WOECY TUZJO EZMCX XGYQF QZYIQ FPFPO SDCFO EQYEF ZDCNO FPOEO CWEPC HOUOO YDOXZ HOTAD OEGXC UWKFZ CWWZI KZGDS GEFZX EZMMQ SQCWE FZQYE AOSFF POXCY TFPCF NCHOX OCYZA AZDFG YQFKF ZOJCX QYOFP OSZYF OYFEQ FFZZV XOCWZ YNFQX OFZIZ DVFPD ZGNPF POXCW WUGFQ FEFPO TCDYO TOEFF PQYNF PODOQ EYZFP QYNXQ EEQYN CYTQX OCYYZ FPQYN YZFOH OYCEQ YNWOU GWWOF CYTIO VYZIF PCFCF WOCEF ZYOIC EWOMF ZYFPO MWZZD ZMFPO ICDOP ZGEOQ YODQY XKMDQ OYTEF OWWXO FPCFC PQYTG NOYFW OXCYP CTUOO YEOOY QYFPO HQSQY QFKZM FPOEP OTQYF POACE FYQNP FUGFQ CXXQY TOTFZ FPQYV FPCFQ ERGEF ESGFF WOUGF FMZWV EQYOD QYFCW VWQVO FPCFC UZGFE FDCYN ODECW WFPOF QXOCY TIQFP YZFPQ YNFZS ZYYOS FPQXF ZFPOS CDNZC YTYZI CKFZF DCSVP QXTZI YQFPQ YVIOS CYQNY ZDOQM YZFMZ DNOFF PONZE EQAQP ZAOFZ UOIQF PKZGC NCQYQ YFPDO OTCKE CYTWZ ZVMZD ICDTF ZTQES GEEQY NFPQE XKEFO DKIQF PKZGF POYXQ EEVCF OICDY O"""
        val formatted = CipherDataBlock.formatAndCreate(ciphertext)
        val cipherdata = formatted._1        
        val broken = SubstitutionCipherBreaker.break(cipherdata)
        val reformatted = formatted._2.revertFormat(broken.outData).mkString;
        println(broken.textData)
        saveText(broken.toXml, ".\\submissions\\Challenge4B.xml")
    }

    def main(args: Array[String]): Unit = {
        job()
    }
}
