package com.core.evolutionaryalgorithms

import scala.collection.parallel.CollectionConverters._

import com.core.ciphers.BaseCipher
import com.core.cipherdata.CipherDataBlock
import com.core.cipherdata.CipherResult

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
  *   A function that takes the current key and some other information, and should return a new key. The current key is
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
    ) => V
) {
    def run(data: CipherDataBlock[K], initialKey: V, generations: Int, children: Int): V = {
        var currentKey = initialKey
        var currentScore = evaluationFunction(cipher.decrypt(data, currentKey))
        for (generation <- 0 until generations) {
            val newKeyScorePairs = (0 to children).par.map { childIndex =>
                val childKey = randomiser(currentKey, currentScore, generation, childIndex, generations, children)
                val childScore = evaluationFunction(cipher.decrypt(data, childKey))
                if (childScore > currentScore) {
                    currentKey = childKey
                    currentScore = childScore
                }
                (childKey, childScore)
            }
            val bestChild = newKeyScorePairs.maxBy(_._2)
            if (bestChild._2 > currentScore) {
                currentKey = bestChild._1
                currentScore = bestChild._2
            }
        }
        currentKey
    }
}
