package com.core.evolutionaryalgorithms

import scala.compiletime.uninitialized

import com.core.cipherdata.CipherDataBlock

/**
  * Represents the result of an evolutionary algorithm, after an attempt at breaking a cipher.
  * 
  * @tparam T
  *  The type of the input data in the cipher.
  * @tparam V
  * The type of the key used in the cipher.
  */
class EvolutionaryAlgorithmResult[T, V] {
    var key: V = uninitialized
    var outData: CipherDataBlock[T] = uninitialized
    var score: Double = uninitialized

    def this(key: V, outData: CipherDataBlock[T], score: Double) = {
        this()
        this.key = key
        this.outData = outData
        this.score = score
    }

    def textData: String = outData.mkString
}
