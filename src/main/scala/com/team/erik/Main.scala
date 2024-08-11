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
import com.core.collections.MapExtensions._
import com.core.collections.BiMapExtensions._
import scala.util.control.Breaks._
import com.core.geneticalgorithms.SubstitutionGeneticAlgorithm

object Main {
    def loadData(): CipherDataBlock[Char] = {
        val path = Paths.get(".\\resources\\text\\Orwell1984.txt")
        val text = Source.fromFile(path.toFile, "UTF-8").take(2000).mkString.toUpperCase.replaceAll("[^A-Z]", "")
        new CipherDataBlock(text, UppercaseLetters)
    }

    def job(args: Array[String]): Unit = {
        val data = loadData()
        val key = KeyFactory.createRandomSubstitutionKey(UppercaseLetters, Some(8))
        val result = SubstitutionCipher.encrypt(data, key).outData

        val freqAnalysis = FrequencyAnalysis.calculateRelative(result).toMap
        val guessKey = KeyFactory.createReverseSubstitutionKeyFromFrequencies(
            UppercaseLetters,
            freqAnalysis,
            DataTable.unigramFrequenciesChar
        )

        val generations = 30
        val children = 500

        val now = System.currentTimeMillis()

        val algorithm = new SubstitutionGeneticAlgorithm()
        val key2 = algorithm.run(result, guessKey, generations, children)
        val decrypt2 = SubstitutionCipher.decrypt(result, key2).outData

        println(s"Time taken: ${System.currentTimeMillis() - now}ms")

        println(decrypt2.mkString)
    }

    def main(args: Array[String]): Unit = {
        job(args)
    }
}
