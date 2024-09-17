package com.core.breakerpresets

import com.core.cipherdata.CipherDataBlock
import com.core.analysers.FrequencyAnalysis
import com.core.keys.KeyFactory
import com.core.ciphers.SubstitutionCipher
import com.core.collections.BiMap
import com.core.evolutionaryalgorithms._
import com.core.extensions.BiMapExtensions.swapElements
import com.core.alphabets.UppercaseLetters

object SubstitutionCipherBreaker extends BreakerPreset[Char, BiMap[Char, Char]] {
    def break(text: String) = {
        val dataBlock = CipherDataBlock.create(text, UppercaseLetters)
        break(dataBlock)
    }
    def break(data: CipherDataBlock[Char]) = {
        val frequencies = FrequencyAnalysis.relative(data).toMap
        val guessKey = KeyFactory.createSubstitutionKeyFromFrequencies(frequencies)
        val breaker = new BaseEvolutionaryAlgorithm[Char, Char, BiMap[Char, Char]](
            SubstitutionCipher,
            FitnessFunctions.eriksWordFitness,
            (currentKey, currentScore, generation, childIndex, maxGenerations, maxChildren) => {
                val newKey = currentKey.clone()
                val swaps = childIndex * 4 / maxChildren + 1
                newKey.swapElements(swaps)
                newKey
            },
            ChildSelectionPolicy.expDfOverT(5, 0)
        )
        val result = breaker.run(data, guessKey, 30, 500, Option("SubstitutionCipherBreaker"))
        new BreakerResult(
            inData = data,
            outData = result.outData,
            cipherUsed = SubstitutionCipher,
            key = result.key,
            score = result.score,
        )
    }
}
