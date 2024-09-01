package com.core.evolutionaryalgorithms

import com.core.collections.BiMap
import com.core.ciphers.SubstitutionCipher
import com.core.data.DataTable
import com.core.analysers.FrequencyCounter
import javax.xml.crypto.Data
import com.core.collections.BiMapExtensions.swapElements
import com.core.collections.TrieNode

private object Constants {
    val common = DataTable.iterateCommonWords.filter(_.size > 3).take(1000).map(_.toUpperCase.toIterable).toSet
    val commonTrie = TrieNode.buildTrie(common)
}

class SubstitutionEvolutionaryAlgorithm
    extends BaseEvolutionaryAlgorithm[Char, Char, BiMap[Char, Char]](
        SubstitutionCipher,
        (result) => {
            val counts = FrequencyCounter.calculate(result, Constants.commonTrie)
            val score = counts.map { case (word, count) =>
                count.toDouble * (word.size - 2) * (word.size - 2)
            }.sum
            score
        },
        (currentKey, currentScore, generation, childIndex, maxGenerations, maxChildren) => {
            val newKey = currentKey.clone()
            val swaps = childIndex * 4 / maxChildren + 1
            newKey.swapElements(swaps)
            newKey;
        }
    ) {}
