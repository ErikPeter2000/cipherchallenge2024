package com.core.cipherbreakers

import com.core.cipherdata.CipherDataBlock
import com.core.ciphers.PermutationCipher
import com.core.analysers.IndexOfCoincidence
import com.core.evolutionaryalgorithms.FitnessFunctions
import com.core.progressbar.ProgressBar

/** Breaker for the Permutation cipher.
  *
  * Performs a brute-force attack to determine the key for the Permutation cipher.
  */
object PermutationCipherBreaker extends CipherBreaker[Char, IndexedSeq[Int]] {
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
        val progressBar = new ProgressBar(maxKeyLength - 1, "PermutationCipherBreaker")
        for (keyLength <- 1 to maxKeyLength) {
            (0 until keyLength).permutations.foreach { permutation =>
                val padded = data.clone().padToMultiple(permutation.length, 'X')
                val result = PermutationCipher.decrypt(padded, permutation)
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
            cipherUsed = PermutationCipher,
            key = bestKey,
            score = bestScore
        )
    }
}
