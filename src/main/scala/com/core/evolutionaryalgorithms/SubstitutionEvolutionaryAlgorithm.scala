package com.core.evolutionaryalgorithms

import com.core.collections.BiMap
import com.core.ciphers.SubstitutionCipher
import com.core.data.DataTable
import com.core.analysers.FrequencyCounter
import javax.xml.crypto.Data
import com.core.collections.BiMapExtensions.swapElements
import com.core.collections.TrieNode

private object Constants {
    val commonWords = DataTable.iterateCommonWords.filter(_.size > 3).take(1000).map(_.toUpperCase.toIterable).toSet
    val commonTrie = TrieNode.buildTrie(commonWords)
}

/** An evolutionary algorithm with predefined settings to break a substitution cipher.
 * 
 * I found that good parameters are 30 generations and 500 children. You can get away with 20 generations and 100 children, but it's less reliable.
  */
class SubstitutionEvolutionaryAlgorithm
    extends BaseEvolutionaryAlgorithm[Char, Char, BiMap[Char, Char]](
         // Cipher to use
        SubstitutionCipher,
        // Arbitrary fitness function: freq_of_word * (len_of_word - 2)^2
        FitnessFunctions.EriksWordFitness,
        // Mutation function
        (currentKey, currentScore, generation, childIndex, maxGenerations, maxChildren) => { 
            // This swaps between 1 and 4 elements, depending on the child index. This varies the degree of mutation across children.
            val newKey = currentKey.clone()
            val swaps = childIndex * 4 / maxChildren + 1
            newKey.swapElements(swaps)
            newKey;
        }
    ) {}
