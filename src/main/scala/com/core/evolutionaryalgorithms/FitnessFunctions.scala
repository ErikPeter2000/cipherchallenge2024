package com.core.evolutionaryalgorithms

import com.core.cipherdata.CipherDataBlock
import com.core.analysers.FrequencyCounter
import com.core.data.DataTable
import com.core.collections.TrieNode


object FitnessFunctions {
    private lazy val commonWords = DataTable.iterateCommonWords.filter(_.size > 3).take(1000).map(_.toUpperCase.toIterable).toSet
    private lazy val commonTrie = TrieNode.buildTrie(commonWords)
    private lazy val quadgramFrequencies = DataTable.quadgramFrequencies
    private lazy val quadgramFrequenciesLog = quadgramFrequencies.mapValues(frequency => Math.log(frequency.toDouble))
    def EriksWordFitness(data: CipherDataBlock[Char]) = {
        val counts = FrequencyCounter.calculate(data, commonTrie)
        val score = counts.map { case (word, count) =>
            count.toDouble * (word.size - 2) * (word.size - 2)
        }.sum
        score
    }
    // http://www.practicalcryptography.com/cryptanalysis/text-characterisation/quadgrams/
    def QuadgramFitness(data: CipherDataBlock[Char]) = {
        val quadgrams = data.sliding(4).toVector
        val score = quadgrams.map(quadgram => {
            val quadgramString = quadgram.mkString
            quadgramFrequenciesLog.getOrElse(quadgramString, -10.0)
        }).sum
        score
    } 
}
