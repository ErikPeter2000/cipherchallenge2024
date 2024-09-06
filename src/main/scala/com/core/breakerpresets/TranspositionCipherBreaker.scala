package com.core.breakerpresets

import com.core.cipherdata.CipherDataBlock
import com.core.ciphers.TranspositionCipher
import com.core.analysers.IndexOfCoincidence
import com.core.evolutionaryalgorithms.FitnessFunctions

object TranspositionCipherBreaker extends BreakerPreset[Char, IndexedSeq[Int]] {
    def break(text: String) = {
        val dataBlock = CipherDataBlock.create(text)
        break(dataBlock, 6)
    }
    def break(data: CipherDataBlock[Char]) = {
        break(data, 6)
    }
    def break(data: CipherDataBlock[Char], maxKeyLength: Int) = {
        var bestScore = 0.0
        var bestKey: IndexedSeq[Int] = null
        var bestDecryption: CipherDataBlock[Char] = null
        for (keyLength <- 1 to maxKeyLength) {
            (0 until keyLength).permutations.foreach { permutation =>
                val result = TranspositionCipher.decrypt(data, permutation)
                val score = FitnessFunctions.EriksWordFitness(result)
                if (score > bestScore) {
                    bestScore = score
                    bestKey = permutation
                    bestDecryption = result
                }
            }
        }
        new BreakerResult(
            inData = data,
            outData = bestDecryption,
            cipherUsed = TranspositionCipher,
            key = bestKey,
            score = bestScore
        )
    }
}
