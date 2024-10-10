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
        val ciphertext = """AHKLE XRMHP XKLXT LMAHK LEXRL NKKXR XGZET GWFRW XTKFK LEHOX ETVXB PTLNG LNKXA HPMHT WWKXL LRHNL HBAHI XMATM MABLE XMMXK YBGWL RHNTG WYBGW LRHNP XEEBT FPKBM BGZUX VTNLX BWHGH MDGHP PAHXE LXMHM NKGMH TGWBY BZNKX MATMR HNFBZ AMUXT UEXMH AXEIB TFLHK KRYHK MAXMK HNUEX BFTRA TOXVT NLXWU RVRIA XKBGZ MABLE XMMXK UNMBP TLGHM LNKXM ATMRH NPHNE WKXTW TFXLL TZXLX GMBGF RATGW GHMDG HPBGZ GHMAB GZTUH NMFXB MLXXF XWFHK XEBDX ERRHN PHNEW FTDXM AXXYY HKMBY MAXFX LLTZX PTLAT KWXKM HKXTW BYGHM TGWBT FPKHG ZTUHN MRHNK BGMXK XLMLB GVHWX LTGWV RIAXK LMAXG IXKAT ILBMP HGMFT MMXKP AXMAX KRHNK XTWBM HKGHM BTFTW XMXVM BOXBG MAXLF TEEMH PGHYX KBGGX PRHKD TGWBG TKXVX GMBGO XLMBZ TMBHG BVTFX NIHGT LMKTG ZXGXL LMATM BVTGG HMNGI BVDBP TLTLD XWMHB GOXLM BZTMX TUKXT DBGTM TEHVT EPTKX AHNLX MAXAT LIATW UXXGK XFHOX WTGWV ENFLB ERKXI ETVXW TEXKM BGZMA XHPGX KUNMG HMABG ZLXXF XWMHU XFBLL BGZTG WMAXL ABIIB GZVKT MXLKX FTBGX WLXTE XWUXA BGWMA XFBYH NGWTU NEEXM VTKKR BGZTG NGNLN TEBGL VKBIM BHGTG WATOX BGVEN WXWTL DXMVA BMBLF BZAMR NGNLN TEYHK TVTLX MHUXF TKDXW BGMAB LPTRT GWMAX EXMMX KLFTD XGHLX GLXMH FXBTF AHIBG ZMATM RHNPB EEUXT UEXMH AXEIB GMXKI KXMMA XFPBM AENVD MATMF BZAML AXWLH FXEBZ AMHGF RBGOX LMBZT MBHGZ BOXGM AXUNE EXMPT LYHNG WPBMA TLABI FXGMU HNGWY HKXGZ ETGWM AXKXF TRTEL HUXKX TLHGY HKRHN MHUXP HKKBX WUNMB FHLML BGVXK XERAH IXGHM BYRHN ATOXK XTWMA BLYTK MAXGI EXTLX NGWXK LMTGW AHPFN VABTI IKXVB TMXRH NKTLL BLMTG VXBEH HDYHK PTKWP BMALH FXXTZ XKGXL LMHRH NKKXI ERPBM AFRZK TMBMN WXFBL LDTMX PTKGX"""
        val formatted = CipherDataBlock.formatAndCreate(ciphertext)
        val cipherdata = formatted._1        
        val broken = SubstitutionCipherBreaker.break(cipherdata)
        println(broken.textData)
        saveText(broken.toXml, ".\\submissions\\Challenge1B.xml")
    }

    def main(args: Array[String]): Unit = {
        job()
    }
}
