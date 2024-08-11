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

        val guessKey = KeyFactory.createReverseSubstitutionKeyFromFrequencies(
            UppercaseLetters,
            freqAnalysis,
            DataTable.unigramFrequenciesChar
        )
        // println(guessKey.pretty)

        val commonWords = DataTable.iterateCommonWords.filter(_.size > 3).take(300).map(_.toUpperCase.toIterable).toSet

        val decrypt = SubstitutionCipher.decrypt(result, guessKey).outData
        val freq2 = FrequencyAnalysis.calculateRelative(decrypt).toMap
        println(data.mkString)
        println()
        println(result.mkString)

        val generations = 80
        val children = 100
        val swaps = 6;
        val standardDeviation: Double = 10;

        val sourceA = DataTable.trigramFrequencies.take(0).map(_._1.toIterable).toSet
        val sourceB = DataTable.bigramFrequencies.take(0).map(_._1.toIterable).toSet
        val sourceC = commonWords
        val source = sourceA ++ sourceB ++ sourceC
        def evaluation(data: CipherDataBlock[Char]): Double = {
            val counts = FrequencyCounter.calculate(data, commonWords)
            val score = counts.map { case (word, count) =>
                count.toDouble * (word.size-2)*(word.size-2)
            }.sum
            return score
        }

        val originalScore = evaluation(data) - 0.1
        println(originalScore)
        var currentKeyPair = (guessKey, 0.0)
        breakable {
            for (i <- 0 until generations) {
                val newKeys = (0 to children).par.map { i =>
                    val numSwaps = i / children * (swaps - 1) + 1
                    // val newKey = currentKeyPair._1.clone().swapElementsGaussian(standardDeviation, numSwaps, UppercaseLetters)
                    val newKey = currentKeyPair._1.clone().swapElements(numSwaps)
                    val newResult = SubstitutionCipher.decrypt(result, newKey).outData
                    (newKey, evaluation(newResult))
                    val score = evaluation(newResult)
                    (newKey, score)
                }.toList
                val (newKey, newScore) = newKeys.maxBy(_._2)
                if (newScore > currentKeyPair._2) {
                    currentKeyPair = (newKey, newScore)
                }
                if (newScore > originalScore) {
                    break()
                }
            }
        }
        val finalResult = SubstitutionCipher.decrypt(result, currentKeyPair._1).outData
        println(finalResult.mkString)
    }

    def main(args: Array[String]): Unit = {
        job(args)
    }
}
