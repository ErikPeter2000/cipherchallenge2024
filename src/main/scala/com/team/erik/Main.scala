package com.team.erik

import scala.io.Source
import scala.util.control.Breaks._
import scala.collection.parallel.CollectionConverters._
import java.nio.file.Paths

import com.core._
import com.core.alphabets._
import com.core.keys.KeyFactory
import com.core.data.DataTable
import com.core.evolutionaryalgorithms.SubstitutionEvolutionaryAlgorithm
import com.core.ciphers._
import com.core.collections.KeyPairOrder
import com.core.collections.IterableExtensions.pretty
import com.core.analysers._
import com.core.cipherdata.CipherDataBlock

object Main {
    def loadData(): CipherDataBlock[Char] = {
        val path = Paths.get(".\\resources\\text\\DostoevskyCrimeAndPunishment.txt")
        val text = Source.fromFile(path.toFile, "UTF-8").take(10000).mkString.toUpperCase.replaceAll("[^A-Z]", "")
        new CipherDataBlock(text, UppercaseLetters)
    }

    def job(args: Array[String]): Unit = {
        val data = loadData()

        val key = KeyFactory.createRandomSubstitutionKey(UppercaseLetters)
        val encrypted = SubstitutionCipher.encrypt(data, key)

        val frequencies = FrequencyAnalysis.relative(encrypted).toMap
        val guessKey = KeyFactory.createSubstitutionKeyFromFrequencies(frequencies)
        val breaker = new SubstitutionEvolutionaryAlgorithm()
        val result = breaker.run(encrypted, guessKey, 30 , 500)
        println(result.outData.mkString)
    }

    def main(args: Array[String]): Unit = {
        job(args)
    }
}
