package com.core.evolutionaryalgorithms

import com.core.cipherdata.CipherDataBlock
import com.core.analysers.FrequencyCounter

object FitnessFunctions {
    def EriksWordFitness(data: CipherDataBlock[Char]) = {
        val counts = FrequencyCounter.calculate(data, Constants.commonTrie)
        val score = counts.map { case (word, count) =>
            count.toDouble * (word.size - 2) * (word.size - 2)
        }.sum
        score
    }
}
