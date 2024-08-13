package com.team.erik

import scala.io.Source
import scala.collection.parallel.CollectionConverters._
import java.nio.file.Paths

import com.core._
import com.core.alphabets.UppercaseLetters
import com.core.ciphers.SubstitutionCipher
import com.core.cipherdata.CipherDataBlock
import com.core.keys.KeyFactory
import com.core.alphabets.LowercaseLetters
import com.core.ciphers.ColumnCipher
import com.core.analysers.FrequencyCounter
import com.core.data.DataTable
import com.core.analysers.FrequencyAnalysis
import scala.util.control.Breaks._
import com.core.evolutionaryalgorithms.SubstitutionEvolutionaryAlgorithm
import com.core.ciphers.VigenereCipher
import com.core.collections.KeyPairOrder
import com.core.collections.IterableExtensions.pretty

object Main {
    def loadData(): CipherDataBlock[Char] = {
        val path = Paths.get(".\\resources\\text\\Orwell1984.txt")
        val text = Source.fromFile(path.toFile, "UTF-8").mkString.toUpperCase.replaceAll("[^A-Z]", "")
        new CipherDataBlock(text, UppercaseLetters)
    }

    def job(args: Array[String]): Unit = {
        val key = KeyFactory.createRandomSubstitutionKey(UppercaseLetters)
        println(key.pretty(order=KeyPairOrder.Key, title="An Alphabet"))

        val data = loadData()
        val words = Set("Winston", "Smith", "Julia", "O'Brien", "Emmanuel", "Goldstein").map(_.toUpperCase.toSeq)
        val counts = FrequencyCounter.calculate(data, words)
        println(counts.pretty(title="Count of Words"))

        val freqAna = FrequencyAnalysis.calculateRelative(data)
        println(freqAna.pretty(title="Frequency Analysis"))
    }

    def main(args: Array[String]): Unit = {
        job(args)
    }
}
