package com.core.evolutionaryalgorithms

import scala.collection.parallel.CollectionConverters._

import com.core.ciphers.BaseCipher
import com.core.cipherdata.CipherDataBlock
import com.core.cipherdata.CipherResult
import com.core.progressbar.ProgressBar

/** Base evolutionary "1 + λ" algorithm for breaking ciphers.
  *
  * The 1 + λ evolutionary strategy is a simple evolutionary algorithm that generates λ children from the current key.
  * The best child is then selected as the new key if it has a higher score than the current key. This process is
  * repeated for a number of generations.
  *
  * @param cipher
  *   The cipher to break. `Cipher.decrypt` will be called with a key, and the plaintext data evaluated for a score.
  * @param evaluationFunction
  *   A function that takes a `CipherResult` and returns a score for how close the key is to the correct key. This
  *   function could be the number of english words in the deciphered data, or the number of common frequent bigrams,
  *   etc.
  * @param randomiser
  *   A function that takes the current key and some other information, and should return a new mutated key. The current key is
  *   passed **by reference** and should be **cloned** before modification.
  *
  * @tparam T
  *   The type of the **plaintext** data; what comes out of the algorithm.
  * @tparam K
  *   The type of the **ciphertext** data; what goes into the algorithm.
  * @tparam V
  *   The type of the key used by the cipher.
  */
class BaseEvolutionaryAlgorithm[T, K, V](
    cipher: BaseCipher[T, K, V],
    evaluationFunction: (CipherDataBlock[T]) => Double,
    randomiser: (
        currentKey: V,
        currentScore: Double,
        generation: Int,
        childIndex: Int,
        maxGenerations: Int,
        maxChildren: Int
    ) => V,
    selectionPolicy: (currentScore: Double, newScore: Double) => Boolean = ChildSelectionPolicy.bestScore
) {

    /** Run the evolutionary algorithm to break the cipher.
      * @param data
      *   The encrypted ciphertext data to decrypt.
      * @param initialKey
      *   The initial key to start the algorithm with.
      * @param generations
      *   The number of generations to run the algorithm for.
      * @param children
      *   The number of children to generate each generation.
      * @return
      *   The key that was found to be the best.
      */
    def run(data: CipherDataBlock[K], initialKey: V, generations: Int, children: Int, progressBarName: Option[String] = None): EvolutionaryAlgorithmResult[T, V] = {
        // Initial key, plaintext, and score
        var currentKey = initialKey
        var currentPlaintext = cipher.decrypt(data, currentKey)
        var currentScore = evaluationFunction(currentPlaintext)

        val progressBar = progressBarName.map(x => new ProgressBar(generations, x))

        // Run the algorithm for the specified number of generations
        for (generation <- 0 until generations) {
            // Iterate over children in parallel and select the best
            val newKeyScorePairs = (0 to children).par.map { childIndex =>
                val childKey = randomiser(currentKey, currentScore, generation, childIndex, generations, children)
                val childPlainText = cipher.decrypt(data, childKey)
                val childScore = evaluationFunction(childPlainText)
                // Return a tuple of the key, plaintext, and score
                (childKey, childPlainText, childScore)
            }

            // Select the best child
            val bestChild = newKeyScorePairs.maxBy(_._3)
            if (selectionPolicy(currentScore, bestChild._3)) {
                currentKey = bestChild._1
                currentPlaintext = bestChild._2
                currentScore = bestChild._3
            }
            progressBar.foreach(_.update(generation))
        }
        progressBar.foreach(_.finish())
        // Return the best key, plaintext, and score
        new EvolutionaryAlgorithmResult[T, V](currentKey, currentPlaintext, currentScore)
    }
}
