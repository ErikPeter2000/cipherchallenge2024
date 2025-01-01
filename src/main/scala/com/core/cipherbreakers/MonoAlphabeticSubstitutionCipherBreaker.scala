package com.core.cipherbreakers

import com.core.analysers.FrequencyAnalysis
import com.core.cipherdata.CipherDataBlock
import com.core.ciphers.MonoAlphabeticSubstitutionCipher
import com.core.collections.BiMap
import com.core.evolutionaryalgorithms.*
import com.core.extensions.BiMapExtensions.swapElements
import com.core.cipherkeys.KeyFactory

/** Breaker for the mono-alphabetic substitution cipher.
  *
  * Uses frequency analysis in combination with an evolutionary algorithm to determine the key for the mono-alphabetic
  * substitution cipher.
  */
object MonoAlphabeticSubstitutionCipherBreaker extends CipherBreaker[Char, BiMap[Char, Char]] {
    def break(text: String) = {
        val dataBlock = CipherDataBlock.create(text)
        break(dataBlock)
    }
    def break(data: CipherDataBlock[Char]) = {
        val frequencies = FrequencyAnalysis.relative(data).toMap
        val guessKey = KeyFactory.createSubstitutionKeyFromFrequencies(frequencies)
        val breaker = new BaseEvolutionaryAlgorithm[Char, Char, BiMap[Char, Char]](
            MonoAlphabeticSubstitutionCipher,
            FitnessFunctions.ngramFitness(4),
            (currentKey, currentScore, generation, childIndex, maxGenerations, maxChildren) => {
                val swaps = childIndex * 4 / maxChildren + 1
                currentKey.clone().swapElements(swaps)
            },
            ChildSelectionPolicy.expDfOverT(2, 0)
        )
        val result = breaker.run(data, guessKey, 30, 1000, Option("SubstitutionCipherBreaker"))
        new BreakerResult(
            inData = data,
            outData = result.outData,
            cipherUsed = MonoAlphabeticSubstitutionCipher,
            key = result.key,
            score = result.score
        )
    }
}
