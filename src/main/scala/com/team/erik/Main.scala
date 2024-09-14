package com.team.erik

import scala.io.Source
import scala.util.control.Breaks._
import scala.collection.parallel.CollectionConverters._
import java.nio.file.{Files, Paths, StandardOpenOption}

import com.core._
import com.core.alphabets._
import com.core.keys.KeyFactory
import com.core.data.DataTable
import com.core.evolutionaryalgorithms._
import com.core.ciphers._
import com.core.extensions._
import com.core.analysers._
import com.core.cipherdata._
import com.core.breakerpresets._
import com.core.extensions.BiMapExtensions._
import com.core.collections._
import com.core.extensions.StringExtensions.highlight
import com.core.progressbar.ProgressBar

object Main {
    def loadData(): CipherDataBlock[Char] = {
        val path = Paths.get(".\\resources\\text\\Orwell1984.txt")
        val text = Source.fromFile(path.toFile, "UTF-8").take(1000).mkString.toUpperCase.replaceAll("[^A-ZJ]", "")
        new CipherDataBlock(text, UppercaseLetters)
    }

    def saveText(text: String, path: String): Unit = {
        Files.write(Paths.get(path), text.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
    }

    def partA(): Unit = {
        val text = "YBY JMTCJYAC YLB AFYPJCQ ZYZZYEC YPC RUM MD RFC KMQR GKNMPRYLR KCKZCPQ MD MSP AMKKSLGRW. RFCW DCYRSPCB GL RFC DGPQR LYRGMLYJ AGNFCP AFYJJCLEC, YLB YEYGL QMKC WCYPQ JYRCP UFCL UC CVNJMPCB RFCGP CYPJW KCCRGLE. GL RFGQ YBTCLRSPC UC YPC PCRSPLGLE RM RFC CLB MD FCP JGDC, YR JCYQR RFC NYPR PCAMPBCB GL RFC YPAFGTCQ YR ZMQQ FCYBOSYPRCPQ. G DMSLB RFGQ QRMPW ZW YAAGBCLR UFCL G UYQ PCQCYPAFGLE RFC PMJC MD RFC PMWYJ DYKGJW GL MSP LYRGMLYJ QCASPGRW. KYLW MD RFCK QCPTCB GL RFC KGJGRYPW YLB YPC DYKGJGYP UGRF AMBCQ YLB AGNFCPQ, YLB G UMLBCPCB FMU DYP ZYAI RFYR GLRCPCQR UCLR. RFYR UYQ FMU G QRSKZJCB YAPMQQ Y LMRC DPMK NPGLAC YJZCPR RM JMPB NYJKCPQRML YLB RFYR QRYPRCB KC BMUL RFC PYZZGR FMJC RFYR JCB SQ FCPC. G FMNC WMS CLHMW GR YQ KSAF YQ G BGB. UC UGJJ KCCR QMKC DYQAGLYRGLE LCU AFYPYARCPQ ML RFC UYW, YLB RFC QRMPW RYICQ Y RUGQR MP RUM. MD AMSPQC, G ILMU FMU GR CLBQ, WMS BML\'R WCR, ZSR GD WMS QRGAI UGRF GR WMS UGJJ, YLB, JGIC KC, WMS UGJJ NPMZYZJW JCYPL QMKCRFGLE ML RFC UYW. G FYTC NMQRCB Y QICRAF MD Y ZSJJCR RFYR UYQ QCLR RM YBY ZYAI GL 1851 ML RFC AYQC DGJCQ NYEC. RFYR UYQ UFCPC RFC QRMPW ZCEYL YLB UFYR BPCU YBY GL. RYIC Y JMMI YLB QCC GD WMS AYL DGESPC MSR UFYR GR KGEFR FYTC KCYLR. GR GQ LMR Y ZYB NJYAC RM QRYPR."

        val formatResult = CipherDataBlock.formatAndCreate(text)
        val broken = CaesarCipherBreaker.break(formatResult._1)
        val recombined = formatResult._2.reinsertElements(broken.outData)
        val resultText = recombined.mkString
        saveText(broken.toXml, ".\\submissions\\Challenge0A.xml")
    }

    def partB():Unit = {
        val text = """CQ ATGJ HGBCTJKLFD, X OGK JTBMELGDL LF RTBXTNT LWGL LWT CQKLTJQ FU LWT KXBNTJ RMBBTL EFMBA ATUTGL LWT RJXBBXGDL CXDAK XD QFMJ FUUXET, RML X WGNT EFCT LF LWT EFDEBMKXFD LWGL LWT KXDVMBGJ CXKK OGJDT OGK EFJJTEL XD WTJ GKKMCHLXFD LWGL LWXK HGJLXEMBGJ EXHWTJ OFMBA ATUTGL MK. WTJ EWFXET FU LWT EFMDLTKK GK WTJ EFDUXATDL GDA EFBBGRFJGLFJ OGK XDKHXJTA. LWT CGJZXDVK FD LWT RMBBTL OTJT EBTGJBQ KXVDXUXEGDL, LWFMVW LWTXJ CTGDXDV TKEGHTA CT TDLXJTBQ MDLXB TPHBGXDTA. OXLW LWGL ZDFOBTAVT, LWT GEEFCHGDQXDV BTLLTJ EFCCGDAK FMJ GLLTDLXFD OXLW XLK AGJZ GBBMKXFDK LF LWT TDA FU LWT TCHXJT.

X ZDFO LWGL QFM GJT LTCHLTA LF GKZ LWT LOF UTGJKFCT BGAXTK LF KLTH GKXAT GDA GBBFO LWT TPHTJLK XD QFMJ ATHGJLCTDL LF LGZT FD LWT XDNTKLXVGLXFD, RML CQ FOD TPHTJXTDET FU CQ ATGJ OXUT KMVVTKLK LWGL, FDET JFMKTA, LWT LXVJTKK XK MDBXZTBQ LF GRGDAFD XLK CTGB, GDA X UTGJ LWGL OT OXBB WGNT LF KLGDA GKXAT GDA GBBFO LWTC LF EFDLXDMT FD LWTXJ GANTDLMJT. XL XK EBTGJ LWGL LWTQ WGNT LWT KZXBBK GDA ATLTJCXDGLXFD LF KTT LWXK LWJFMVW, GDA X LWXDZ OT OXBB FDBQ FRKLJMEL LWTC XU OT XDLTJUTJT.

X LJMKL LWGL QFM WGNT JTGEWTA LWT KGCT EFDEBMKXFD GDA LWGL X EGD GKZ RGRRGVT LF EFDNTQ FMJ LWGDZK GDA RTKL OXKWTK LF LWTC RFLW, XD LWT WFHT LWGL LWTQ EGD RJXDV G KHTTAQ JTKFBMLXFD LF LWXK LJFMRBXDV CGLLTJ.

QFMJK, GBRTJL"""

        val formatResult = CipherDataBlock.formatAndCreate(text)
        val broken = SubstitutionCipherBreaker.break(formatResult._1)
        val recombined = formatResult._2.reinsertElements(broken.outData)
        saveText(broken.toXml, ".\\submissions\\Challenge0B.xml")
    }

    def main(args: Array[String]): Unit = {
        println(Analyser.hello());
    }
}
