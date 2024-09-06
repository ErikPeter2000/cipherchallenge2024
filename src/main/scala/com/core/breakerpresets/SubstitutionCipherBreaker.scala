package com.core.breakerpresets

import com.core.cipherdata.CipherDataBlock
import com.core.analysers.FrequencyAnalysis
import com.core.keys.KeyFactory
import com.core.evolutionaryalgorithms.SubstitutionEvolutionaryAlgorithm
import com.core.ciphers.SubstitutionCipher
import com.core.collections.BiMap

object SubstitutionCipherBreaker extends BreakerPreset[Char, BiMap[Char, Char]] {
    def break(text: String) = {
        val dataBlock = CipherDataBlock.create(text)
        break(dataBlock)
    }
    def break(data: CipherDataBlock[Char]) = {
        val frequencies = FrequencyAnalysis.relative(data).toMap
        val guessKey = KeyFactory.createSubstitutionKeyFromFrequencies(frequencies)
        val breaker = new SubstitutionEvolutionaryAlgorithm()
        val result = breaker.run(data, guessKey, 30, 500)
        new BreakerResult(
            inData = data,
            outData = result.outData,
            cipherUsed = SubstitutionCipher,
            key = result.key,
            score = result.score,
        )
    }
}
