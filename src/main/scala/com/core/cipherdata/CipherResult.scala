package com.core.cipherdata

import scala.compiletime.uninitialized

import com.core.alphabets.BaseAlphabet

enum CipherResultStatus:
    case Success, Failure, Unspecified

/** Represents the result of a cipher operation.
  * @tparam T
  *   The type of the input data in the cipher.
  * @tparam K
  *   The type of the output data in the cipher.
  */
class CipherResult[T, K] {
    var inData: CipherDataBlock[T] = uninitialized
    var outData: CipherDataBlock[K] = uninitialized
    var status = CipherResultStatus.Unspecified
}


@deprecated("Return the CipherDataBlock instead.", "0.1")
object CipherResult {
    def create[T, K](inData: CipherDataBlock[T], outData: CipherDataBlock[K]): CipherResult[T, K] = {
        val result = new CipherResult[T, K]()
        result.inData = inData
        result.outData = outData
        result.status = CipherResultStatus.Success
        return result
    }
    def create[T, K](inData: CipherDataBlock[T], outData: Seq[K], outAlphabet: BaseAlphabet[K]): CipherResult[T, K] = {
        val result = new CipherResult[T, K]()
        result.inData = inData
        result.outData = new CipherDataBlock[K](outData, outAlphabet)
        result.status = CipherResultStatus.Success
        return result
    }
}
