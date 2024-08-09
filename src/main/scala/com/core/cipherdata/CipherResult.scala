package com.core.cipherdata

import com.core.alphabets.BaseAlphabet

object CipherResultStatus extends Enumeration {
    type CipherResultStatus = Value
    val Success, Failure, Unspecified = Value
}

/** Represents the result of a cipher operation.
  * @tparam T
  *   The type of the input data in the cipher.
  * @tparam K
  *   The type of the output data in the cipher.
  */
class CipherResult[T, K] {
    var inData: CipherDataBlock[T] = _
    var outData: CipherDataBlock[K] = _
    var status = CipherResultStatus.Unspecified
}

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
