package com.core.breakerpresets

import com.core.cipherdata.CipherDataBlock
import com.core.analysers.FrequencyAnalysis
import com.core.keys.KeyFactory
import com.core.evolutionaryalgorithms.SubstitutionEvolutionaryAlgorithm

object SubstitutionCipherBreaker extends BreakerPreset {
    def break(text: String): String = {
        val dataBlock = CipherDataBlock.create(text)
        val frequencies = FrequencyAnalysis.relative(dataBlock).toMap
        val guessKey = KeyFactory.createSubstitutionKeyFromFrequencies(frequencies)
        val breaker = new SubstitutionEvolutionaryAlgorithm()
        val result = breaker.run(dataBlock, guessKey, 30, 500)
        result.outData.mkString
    }
}
