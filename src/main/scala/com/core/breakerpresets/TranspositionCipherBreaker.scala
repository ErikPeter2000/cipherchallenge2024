package com.core.breakerpresets

import com.core.cipherdata.CipherDataBlock
import com.core.ciphers.TranspositionCipher
import com.core.analysers.IndexOfCoincidence
import com.core.evolutionaryalgorithms.FitnessFunctions

object TranspositionCipherBreaker extends BreakerPreset {
    def break(text: String): String = {
        return break(text, 6)
    }
    def break(text: String, maxKeyLength: Int): String = {
        val dataBlock = CipherDataBlock.create(text)
        var bestScore = 0.0
        var bestDecryption: CipherDataBlock[Char] = null
        for (keyLength <- 1 to maxKeyLength) {
            (0 until keyLength).permutations.foreach { permutation =>
                val result = TranspositionCipher.decrypt(dataBlock, permutation)
                val score = FitnessFunctions.EriksWordFitness(result)
                if (score > bestScore) {
                    bestScore = score
                    bestDecryption = result
                }
            }
        }
        return bestDecryption.mkString
    }
}
