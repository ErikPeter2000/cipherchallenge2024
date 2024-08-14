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
import com.core.analysers._

object Main {
    def loadData(): CipherDataBlock[Char] = {
        val path = Paths.get(".\\resources\\text\\DostoevskyCrimeAndPunishment.txt")
        val text = Source.fromFile(path.toFile, "UTF-8").take(10000).mkString.toUpperCase.replaceAll("[^A-Z]", "")
        new CipherDataBlock(text, UppercaseLetters)
    }

    def job(args: Array[String]): Unit = {
        val data = loadData()

        val key = "HELLOWORLD".toCharArray
        val encrypted = VigenereCipher.encrypt(data, key).outData
        val friedman = FriedmanTest.calculate(encrypted)
    }

    def main(args: Array[String]): Unit = {
        job(args)
    }
}
