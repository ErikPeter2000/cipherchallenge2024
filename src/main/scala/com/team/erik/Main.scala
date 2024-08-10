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

        // println(commonWords.take(5))

        val freqAnalysis = FrequencyAnalysis.calculateRelative(result).toMap
        // println(freqAnalysis.pretty)

        val guessKey = KeyFactory.createReverseSubstitutionKeyFromFrequencies(UppercaseLetters, freqAnalysis, DataTable.unigramFrequenciesChar)
        // println(guessKey.pretty)

        val commonWords = DataTable.iterateCommonWords.filter(_.size > 3).take(200).map(_.toUpperCase.toSeq).toSet

        val decrypt = SubstitutionCipher.decrypt(result, guessKey).outData
        val freq2 = FrequencyAnalysis.calculateRelative(decrypt).toMap
        println(data.mkString)
        println(decrypt.mkString)

        val generations = 100
        val children = 1000
        val swaps = 2;
        val standardDeviation: Double = 10;

        val source = DataTable.quadgramFrequencies
        def evaluation(data: CipherDataBlock[Char]): Double = {
            val counts = FrequencyCounter.calculate(data, commonWords)
            val score = counts.map { case (word, count) =>
                count.toDouble * word.size
            }.sum
            return score
        }
        
        println(evaluation(data))
        val originalScore = evaluation(result)-0.1
        println(originalScore)
        var currentKeyPair = (guessKey, originalScore)
        for (i <- 0 until generations) {
            val newKeys = (0 to children).par.map { _ =>
                val newKey = currentKeyPair._1.clone().swapElementsGaussian(standardDeviation, swaps, UppercaseLetters)
                val newResult = SubstitutionCipher.decrypt(result, newKey).outData
                (newKey, evaluation(newResult))
                val score = evaluation(newResult)
                (newKey, score)
            }.toList
            val (newKey, newScore) = newKeys.maxBy(_._2)
            if (newScore > currentKeyPair._2) {
                println(s": $newScore")
                currentKeyPair = (newKey, newScore) // ??
            }
            if (newScore > originalScore) {
                return
            }
        }

        val finalResult = SubstitutionCipher.decrypt(result, currentKeyPair._1).outData
        println(currentKeyPair._2)
        println(finalResult.mkString)

    }

    def main(args: Array[String]): Unit = {
        job(args)
    }
}
