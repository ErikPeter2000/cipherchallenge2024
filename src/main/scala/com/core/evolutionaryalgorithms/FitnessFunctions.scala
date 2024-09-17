package com.core.evolutionaryalgorithms

import com.core.cipherdata.CipherDataBlock
import com.core.analysers.FrequencyCounter
import com.core.data.DataTable
import com.core.collections.TrieNode


object FitnessFunctions {
    private lazy val commonWords = DataTable.iterateCommonWords.filter(_.size > 3).take(1000).map(_.toUpperCase.toIterable).toSet
    private lazy val commonTrie = TrieNode.buildTrie(commonWords)
    private var polygramCache: Map[Int, Vector[Double]] = Map()

    def eriksWordFitness(data: CipherDataBlock[Char]) = {
        val counts = FrequencyCounter.calculate(data, commonTrie)
        val score = counts.map { case (word, count) =>
            count.toDouble * (word.size - 2) * (word.size - 2)
        }.sum
        score
    }
    // http://www.practicalcryptography.com/cryptanalysis/text-characterisation/tetragrams/
    def polygramFitness(n: Int): (CipherDataBlock[Char]) => Double = {
        val polygramFrequencies = DataTable.polygramFrequenciesLog(n)
        (data: CipherDataBlock[Char]) => {
            val polygrams = data.sliding(n)
            val score = polygrams.map(
                polygram => polygramFrequencies.getOrElse(polygram.mkString, -10.0)
            ).sum
            score
        }
    }
}
