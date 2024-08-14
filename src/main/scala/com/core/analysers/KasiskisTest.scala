package com.core.analysers

import com.core.cipherdata.CipherDataBlock

/** Performs a Kasiski test on a given data block, to determine the key length of a Polyalphabetic Substitution Cipher,
  * such as the Vigenere cipher.
  */
object KasiskisTest {

    def _getFactors(n: Int): Seq[Int] = {
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

        val substringIndices = scala.collection.mutable.Map.empty[String, List[Int]].withDefault(_ => List.empty)

        (minLength to maxLength).foreach(sliceLength => {
            data.sliding(sliceLength).zipWithIndex.foreach { case (slice, index) =>
                val sliceString = slice.mkString
                val positions = substringIndices(sliceString)
                substringIndices(sliceString) = positions :+ index
            }
        })

        val distances = substringIndices.values
            .filter(_.length > 1)
            .flatMap { positions =>
                positions.sliding(2)
                .collect { case Seq(a, b) => b - a }
            }

        val factors = distances
            .flatMap(_getFactors)
            .filter(x => x >= minLength && x <= maxFactor)
            .groupBy(identity)
            .mapValues(_.size)

        val sum = factors.values.sum.toDouble
        return factors.mapValues(_ / sum).toMap
    }
}
