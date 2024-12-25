package com.core.cipherbreakers

import com.core.analysers.KasiskisTest
import com.core.cipherdata.CipherDataBlock
import com.core.ciphers.CaesarCipher
import com.core.ciphers.VigenereCipher
import com.core.evolutionaryalgorithms.FitnessFunctions.eriksWordFitness
import com.core.progressbar.ProgressBar

/** Breaker for the Vigenere cipher.
  *
  * Uses Kasiski's test to determine the key for the Vigenere cipher, and then tests all possible key lengths to
  * determine the best key.
  */
object VigenereCipherBreaker extends CipherBreaker[Char, Seq[Char]] {
    // The vulnerability of the Vigenere cipher lies in the fact that if we know the key length 'n', we know that every nth letter has been encrypted with the same shift value. This means that we can treat each column as a Caesar cipher, and solve it using frequency analysis.
    def break(data: String) = {
        break(CipherDataBlock.create(data))
    }
    def break(data: CipherDataBlock[Char]) = {
        // Get possible key lengths
        val kasiskiTest = KasiskisTest.calculate(data)

        // Convert to a vector of key lengths, with the most frequent factors first. (We are testing all key lengths returned, so the sort is unnecessary)
        val bestLengths = kasiskiTest.keys.toVector.sortBy(x => -kasiskiTest(x))

        // Setup a progress bar
        val progressBar = new ProgressBar(bestLengths.length, "VigenereCipherBreaker")

        // Now generate some decryption attempts for each key length. We currently brute force all possible lengths, but this can be optimised by taking the top n. I didn't find this necessary because it's fast enough.
        val scoreKeyDataPairs = bestLengths.map { case length =>
            // Group the data into groups of the key length
            val columns = data
                .grouped(length)
                .toVector
                .dropRight(1) // drop in case the last group is not full length, otherwise transpose won't work
                .transpose // Convert to a list of columns. Now, all elements of a column have been encrypted with the same shift value, so we can treat each column as a Caesar cipher, and is susceptible to frequency analysis.
            // .map(x => CipherDataBlock.create(x, data.alphabet))

            // Get the shift value for each column by solving the Caesar cipher
            val shiftValues = columns.map { group =>
                val shift = CaesarCipherBreaker.getKey(group, data.alphabet)._1 // get the value
                data.alphabet(shift) // return the letter at that shift value.
            }

            // Decrypt the data using the shift values taken from this key length
            val decrypted = VigenereCipher.decrypt(data, shiftValues)

            // Measure the fitness of the decrypted text
            val score = eriksWordFitness(decrypted)

            // Update the progress bar
            progressBar.increment()

            // Return what we found
            (score, shiftValues, decrypted)
        }
        progressBar.finish()

        // Get the best score
        val bestScore = scoreKeyDataPairs.maxBy(_._1)

        // Return the best result
        new BreakerResult(
            inData = data,
            outData = bestScore._3,
            cipherUsed = VigenereCipher,
            key = bestScore._2,
            score = bestScore._1
        )
    }
}
