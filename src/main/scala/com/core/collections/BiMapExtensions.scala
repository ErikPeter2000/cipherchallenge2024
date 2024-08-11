package com.core.collections

import com.core.alphabets.BaseAlphabet
import scala.util.Random

object BiMapExtensions {
    extension [T, K](biMap: BiMap[T, K]) {

        /** Shuffles the values of the BiMap. Performs the shuffle in-place.
          */
        def shuffleValues(): BiMap[T, K] = {
            val keys = biMap.keys.toSeq
            val values = biMap.values.toSeq
            val shuffledValues = scala.util.Random.shuffle(values)
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
                val key1 = keys(scala.util.Random.nextInt(keys.size))
                var key2 = keys(scala.util.Random.nextInt(keys.size))
                while (key1 == key2) {
                    key2 = keys(scala.util.Random.nextInt(keys.size))
                }
                val value1 = biMap(key1)
                val value2 = biMap(key2)
                biMap(key1) = value2
                biMap(key2) = value1
            }
            biMap
        }

        /**
          * Shuffles the value of a BiMap using a Gaussian distribution to determine how far to swap the values.
          * Does it in-place, and returns the BiMap instance.
          *
          * @param standardDeviation
          * @param times
          * @param alphabet
          * @return
          */
        def swapElementsGaussian(standardDeviation: Double, iterations: Int, alphabet: BaseAlphabet[T]): BiMap[T, K] = {
            for (_ <- 0 until iterations) {
                val index1 = Random.nextInt(alphabet.size)
                val index2 = Math.floorMod(index1 + (Random.nextGaussian() * standardDeviation).toInt, alphabet.size)
                val key1 = alphabet(index1)
                val key2 = alphabet(index2)
                val value1 = biMap(key1)
                val value2 = biMap(key2)
                biMap.addMapping(key1, value2).addMapping(key2, value1)
            }
            biMap
        }

        def pretty: String = {
            biMap.map { case (k, v) => s"  $k: $v" }.mkString("{\n", ", \n", "\n}")
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