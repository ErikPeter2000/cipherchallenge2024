package com.core.breakerpresets

import com.core.ciphers.BaseCipher
import com.core.cipherdata.CipherDataBlock

class BreakerResult[T, K, V](
    val inData: CipherDataBlock[T],
    val outData: CipherDataBlock[K],
    val cipherUsed: BaseCipher[T, K, V],
    val key: V,
    val score: Double
) {
    def textData: String = outData.mkString
    override def toString(): String = textData
}