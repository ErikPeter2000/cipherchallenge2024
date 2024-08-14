package com.core.analysers

import com.core.cipherdata.CipherDataBlock

/** The Friedman test tries to approximate the key length of a Vigenère cipher by comparing the indices of coincidence.
  *
  * It returns an estimated key length. The longer the data block, the more accurate the result.
  */
object FriedmanTest {

    /** Estimate the key length of a Vigenère cipher using the Friedman test.
      *
      * @param data
      *   The data block to analyse.
      * @param plainTextIoC
      *   The index of coincidence of the plaintext.
      * @param randomIoC
      *   The index of coincidence of random data.
      * @return
      *   The estimated key length.
      */
    def calculate[T](data: CipherDataBlock[T], plainTextIoC: Double = 0.067, randomIoC: Double = 0.0385): Double = {
        val ioc = IndexOfCoincidence.calculate(data)
        (plainTextIoC - ioc) / (ioc - randomIoC)
    }
}
