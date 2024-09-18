package com.core.cipherbreakers

import com.core.cipherdata.CipherDataBlock
import com.core.ciphers.TranspositionCipher
import com.core.analysers.IndexOfCoincidence
import com.core.evolutionaryalgorithms.FitnessFunctions
import com.core.progressbar.ProgressBar
import com.core.alphabets.UppercaseLetters

object TranspositionCipherBreaker extends CipherBreaker[Char, IndexedSeq[Int]] {
    def break(text: String) = {
        val dataBlock = CipherDataBlock.create(text, UppercaseLetters)
        break(dataBlock, 6)
    }
    def break(data: CipherDataBlock[Char]) = {
        break(data, 6)
    }
    def break(data: CipherDataBlock[Char], maxKeyLength: Int) = {
        var bestScore = 0.0
        var bestKey: IndexedSeq[Int] = null
        var bestDecryption: CipherDataBlock[Char] = null
        val progressBar = new ProgressBar(maxKeyLength - 1, "TranspositionCipherBreaker")
        for (keyLength <- 1 to maxKeyLength) {
            (0 until keyLength).permutations.foreach { permutation =>
                val result = TranspositionCipher.decrypt(data, permutation)
                val score = FitnessFunctions.eriksWordFitness(result)
                if (score > bestScore) {
                    bestScore = score
                    bestKey = permutation
                    bestDecryption = result
                }
            }
            progressBar.increment()
        }
        progressBar.finish()
        new BreakerResult(
            inData = data,
            outData = bestDecryption,
            cipherUsed = TranspositionCipher,
            key = bestKey,
            score = bestScore
        )
    }
}