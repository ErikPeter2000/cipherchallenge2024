package com.core.analysers

import com.core.cipherdata.CipherDataBlock

/** Calculates the Index of Coincidence (IoC) of a given data block. IoC is a measure of how likely it is that two randomly
  * selected letters from a text are the same. The formula is:
  *
  * `IoC = Σ(frequency of each letter * (frequency of each letter - 1)) / (n * (n - 1))`
  * 
  * For pure alphabetic English text, the IoC is around 0.067. Transposition ciphers do not change the IoC.
  */
object IndexOfCoincidence {
    /**
      * Calculates the Index of Coincidence of a given data block, using the datablock's alphabet.
      * If the data contains characters not in the alphabet, weird things will happen.
      *
      * @param data The data block to calculate the Index of Coincidence of.
      * @return The Index of Coincidence of the data block.
      */
    def calculate[T](data: CipherDataBlock[T]): Double = {
        val dataLength = data.length
        val dataFrequency = data.groupBy(identity).mapValues(_.size)
        val alphabetFrequency = data.alphabet.map(c => (c, dataFrequency.getOrElse(c._2, 0)))
        var sum = 0.0

        sum = dataFrequency.values.map(frequency => frequency * (frequency - 1)).sum

        sum / (dataLength * (dataLength - 1))
    }
}
