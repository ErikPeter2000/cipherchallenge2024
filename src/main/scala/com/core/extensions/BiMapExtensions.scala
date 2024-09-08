package com.core.extensions

import com.core.alphabets.BiMapAlphabet
import scala.util.Random
import scala.annotation.switch
import com.core.collections.BiMap

object BiMapExtensions {
    val MAX_DISPLAY_LINES = 10
    lazy val rng = new Random(0)

    extension [T, K](biMap: BiMap[T, K]) {

        /** Shuffles the values of the BiMap. Performs the shuffle in-place, and returns the BiMap
          */
        def shuffleValues(): BiMap[T, K] = {
            val keys = biMap.keys.toSeq
            val values = biMap.values.toSeq
            val shuffledValues = rng.shuffle(values)
            keys.zip(shuffledValues).foreach { case (key, value) =>
                biMap.addMapping(key, value)
            }
            biMap
        }

        /** Shuffles the values of the BiMap a number of times. Performs the shuffle in-place, and returns the BiMap
          * instance.
          * @param iterations
          * @return
          */
        def shuffleValues(iterations: Int): BiMap[T, K] = {
            var newBiMap = biMap
            for (_ <- 0 until iterations) {
                // ...
            }
            biMap
        }

        /** Swaps the values of random keys in the BiMap. Does it in-place, and returns the BiMap instance.
          *
          * @param times
          * @return
          */
        def swapElements(times: Int): BiMap[T, K] = {
            val keys = biMap.keys.toSeq
            for (_ <- 0 until times) {
                val key1 = keys(rng.nextInt(keys.size))
                var key2 = keys(rng.nextInt(keys.size))
                while (key1 == key2) {
                    key2 = keys(rng.nextInt(keys.size))
                }
                val value1 = biMap(key1)
                val value2 = biMap(key2)
                biMap(key1) = value2
                biMap(key2) = value1
            }
            biMap
        }

        /** Shuffles the value of a BiMap using a Gaussian distribution to determine how far to swap the values. Does it
          * in-place, and returns the BiMap instance.
          *
          * @param standardDeviation
          * @param times
          * @param alphabet
          * @return
          */
        def swapElementsGaussian(standardDeviation: Double, iterations: Int, alphabet: BiMapAlphabet[T]): BiMap[T, K] = {
            for (_ <- 0 until iterations) {
                val index1 = rng.nextInt(alphabet.size)
                val index2 = Math.floorMod(index1 + (rng.nextGaussian() * standardDeviation).toInt, alphabet.size)
                val key1 = alphabet(index1)
                val key2 = alphabet(index2)
                val value1 = biMap(key1)
                val value2 = biMap(key2)
                biMap.addMapping(key1, value2).addMapping(key2, value1)
            }
            biMap
        }
    }

    extension [T, K](iterable: Iterable[(T, K)]) {
        def toBiMap: BiMap[T, K] = {
            val biMap = new BiMap[T, K]
            iterable.foreach((k, v) => biMap.addMapping(k, v))
            biMap
        }
    }
}
