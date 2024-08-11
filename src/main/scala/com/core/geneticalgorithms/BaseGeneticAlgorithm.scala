package com.core.geneticalgorithms

import scala.collection.parallel.CollectionConverters._

import com.core.ciphers.BaseCipher
import com.core.cipherdata.CipherDataBlock
import com.core.cipherdata.CipherResult

/** Base genetic algorithm for use with ciphers. These algorithms are designed to break ciphers by slowly modifying a
  * key until the correct key is found. They use an evaluation function or to determine how close a key is to the
  * correct key. Higher scores are better, with the highest score being the correct key.
  *
  * @param cipher
  *   The cipher to break. `Cipher.decrypt` will be called with a key, and the plaintext data evaluated for score.
  * @param evaluationFunction
  *   A function that takes a `CipherResult` and returns a score for how close the key is to the correct key. This
  *   function could be the number of english words in the deciphered data, or the number of common frequent bigrams,
  *   etc.
  * @param randomiser
  *   A function that takes the current key and some other information, and should return a new key. The current key is
  *   passed **by reference** and should be **cloned** before modification.
  *
  * @tparam T
  *   The type of the **plaintext** data; what comes out of the cipher.
  * @tparam K
  *   The type of the **ciphertext** data; what goes into the cipher.
  * @tparam V
  *   The type of the key used by the cipher.
  */
class BaseGeneticAlgorithm[T, K, V](
    cipher: BaseCipher[T, K, V],
    evaluationFunction: (CipherResult[K, T]) => Double,
    randomiser: (currentKey: V, currentScore: Double, generation: Int, childIndex: Int, maxGenerations: Int, maxChildren: Int) => V
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
