package com.core.evolutionaryalgorithms

import com.core.cipherdata.CipherDataBlock
import com.core.analysers.FrequencyCounter
import com.core.languagedata.DataTable
import com.core.collections.TrieNode

/** Contains fitness functions that can be used to evaluate the English-ness of decoded ciphertext.
  *
  * This is used in evolutionary attacks attacks.
  */
object FitnessFunctions {
    private lazy val commonWords =
        DataTable.iterateCommonWords.filter(_.size > 3).take(1000).map(_.toUpperCase.toIterable).toSet
    private lazy val commonTrie = TrieNode.buildTrie(commonWords)
    private var polygramCache: Map[Int, Vector[Double]] = Map()

    /** Use a trie to search for common words.
      *
      * Very fast, but requires longer texts to be effective.
      */
    def eriksWordFitness(data: CipherDataBlock[Char]) = {
        val counts = FrequencyCounter.calculate(data, commonTrie)
        val score = counts.map { case (word, count) =>
            count.toDouble * (word.size - 2) * (word.size - 2)
        }.sum
        score
    }

    // http://www.practicalcryptography.com/cryptanalysis/text-characterisation/tetragrams/
    def ngramFitness(n: Int): (CipherDataBlock[Char]) => Double = {
        val polygramFrequencies = DataTable.polygramFrequenciesLog(n)
        (data: CipherDataBlock[Char]) => {
            val ngrams = data.sliding(n)
            val score = ngrams.map(
                polygram => polygramFrequencies.lookup(polygram)
            ).sum
            score
        }
    }
}
