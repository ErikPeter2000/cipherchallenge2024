package com.core.cipherbreakers

import com.core.cipherdata.CipherDataBlock

/** Breakers are designed to provide simple interfaces for turning encrypted text into plaintext.
  */
abstract trait CipherBreaker[K, V] {
    def break(text: String): BreakerResult[Char, K, V]
    def break(data: CipherDataBlock[K]): BreakerResult[Char, K, V]
}
