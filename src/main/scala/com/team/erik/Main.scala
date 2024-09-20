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
        CipherDataBlock.formatAndCreate(text)
    }

    def saveText(text: String, path: String): Unit = {
        Files.write(Paths.get(path), text.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
    }

    def job() = {
        val data = "WNUTGKUTGKVTSSHQKZGFTOZVQLQWKOUIZEGSRRQNOFQHKOSQFRZITESGEALVTKTLZKOAOFUZIOKZTTFVOFLZGFLDOZIIOLEIOFFXMMSTROFZGIOLWKTQLZOFQFTYYGKZZGTLEQHTZITCOSTVOFRLSOHHTRJXOEASNZIKGXUIZITUSQLLRGGKLGYCOEZGKNDQFLOGFLZIGXUIFGZJXOEASNTFGXUIZGHKTCTFZQLVOKSGYUKOZZNRXLZYKGDTFZTKOFUQSGFUVOZIIODZITIQSSVQNLDTSZGYWGOSTREQWWQUTQFRGSRKQUDQZLQZGFTTFRGYOZQEGSGXKTRHGLZTKZGGSQKUTYGKOFRGGKROLHSQNIQRWTTFZQEATRZGZITVQSSOZRTHOEZTRLODHSNQFTFGKDGXLYQETDGKTZIQFQDTZKTVORTZITYQETGYQDQFGYQWGXZYGKZNYOCTVOZIQITQCNWSQEADGXLZQEITQFRKXUUTRSNIQFRLGDTYTQZXKTLVOFLZGFDQRTYGKZITLZQOKLOZVQLFGXLTZKNOFUZITSOYZTCTFQZZITWTLZGYZODTLOZVQLLTSRGDVGKAOFUQFRQZHKTLTFZZITTSTEZKOEEXKKTFZVQLEXZGYYRXKOFURQNSOUIZIGXKLOZVQLHQKZGYZITTEGFGDNRKOCTOFHKTHQKQZOGFYGKIQZTVTTAZITYSQZVQLLTCTFYSOUIZLXHQFRVOFLZGFVIGVQLZIOKZNFOFTQFRIQRQCQKOEGLTXSETKQWGCTIOLKOUIZQFASTVTFZLSGV"

        val cipherData = CipherDataBlock.create(data)
        val fitness = FitnessFunctions.polygramFitness(4)(cipherData)
        println(fitness)

        println(DataTable.tetragramFrequenciesLog.table.take(10))
    }

    def main(args: Array[String]): Unit = {
        job()
    }
}
