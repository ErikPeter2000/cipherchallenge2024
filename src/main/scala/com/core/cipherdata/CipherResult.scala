package com.core.cipherdata

import scala.compiletime.uninitialized

import com.core.alphabets.Alphabet

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
