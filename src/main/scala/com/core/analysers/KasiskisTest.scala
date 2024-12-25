package com.core.analysers

import com.core.cipherdata.CipherDataBlock

/** Performs a Kasiski test on a given data block, to estimate the key length of a Polyalphabetic Substitution Cipher,
  * such as the Vigenere cipher.
  */
object KasiskisTest {
    // The Kasiski test works by identifying repeating sections of cipher in text in the ciphertext. The distance between blocks should be a multiple of the key length. If we take enough samples, we can determine the most common factors of the distances, which should help us find the key length.

    def getFactors(n: Int): Seq[Int] = {
        (1 to n).filter(n % _ == 0)
    }

    def calculate[T](
        data: CipherDataBlock[T],
        minLength: Int = 3,
        maxLength: Int = 6,
        maxFactor: Int = 26
    ): Map[Int, Double] = {
        if (minLength < 1 || maxLength < 1 || minLength > maxLength) {
            throw new IllegalArgumentException("Minimum and maximum key lengths are not valid.")
        }
        val dataLength = data.length

        // Initialise a new map for the positions of substrings in the dataBlock.
        // Each possible substring will have a list of the positions it appears in the datablock.
        val substringPositions = scala.collection.mutable.Map.empty[String, List[Int]].withDefault(_ => List.empty)

        // For every possible key length 'n'...
        (minLength to maxLength).foreach(sliceLength => {
            data.sliding(sliceLength) // Group the data into slices of length 'n'
                .zipWithIndex // Convert from a list of just slices to a list of (slice, index) tuples, so we can work with the index too
                .foreach { case (slice, index) => // now for every slice...
                    val sliceString = slice.mkString // Convert the slice to a string
                    val positions = substringPositions(sliceString)
                    substringPositions(sliceString) =
                        positions :+ index // Record the position of the occurrence of the slice
                }
        })

        // Now work out the distances between the occurrences of each substring
        val distances = substringPositions.values // Take only the substring slices.
            .filter(_.length > 1) // Only consider substrings that appear more than once
            .flatMap { positions => // from two consecutive positions, calculate the distance between them
                positions
                    .sliding(2)
                    .collect { case Seq(a, b) => b - a }
            } // flatten the list of lists of distances into a single list

        // Now get the factors of the distances
        val factors = distances
            .flatMap(getFactors) // get the factors of each distance, and un-flatten this into one big list.
            .filter(x => x >= minLength && x <= maxFactor) // filter out factors that are too small or too large
            .groupBy(identity) // count the occurrences of each factor
            .mapValues(_.size) // convert the list of factors into a map of (factor -> frequency of specific factor)

        // Normalise and return the factors
        val sum = factors.values.sum.toDouble
        return factors.mapValues(_ / sum).toMap
    }
}
