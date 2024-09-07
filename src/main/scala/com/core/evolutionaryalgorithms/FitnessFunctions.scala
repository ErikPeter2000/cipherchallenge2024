package com.core.evolutionaryalgorithms

import com.core.cipherdata.CipherDataBlock
import com.core.analysers.FrequencyCounter
import com.core.data.DataTable
import com.core.collections.TrieNode

private object Constants {
    val commonWords = DataTable.iterateCommonWords.filter(_.size > 3).take(1000).map(_.toUpperCase.toIterable).toSet
    val commonTrie = TrieNode.buildTrie(commonWords)
}

object FitnessFunctions {
    def EriksWordFitness(data: CipherDataBlock[Char]) = {
        val counts = FrequencyCounter.calculate(data, Constants.commonTrie)
        val score = counts.map { case (word, count) =>
            count.toDouble * (word.size - 2) * (word.size - 2)
        }.sum
        score
    }
}
