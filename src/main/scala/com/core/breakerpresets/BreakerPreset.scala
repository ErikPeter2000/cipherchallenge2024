package com.core.breakerpresets

import com.core.cipherdata.CipherDataBlock

/** BreakerPresets are designed to provide simple interfaces for turning encrypted text into plaintext.
  */
abstract trait BreakerPreset[K, V] {
    def break(text: String): BreakerResult[Char, K, V]
    def break(data: CipherDataBlock[Char]): BreakerResult[Char, K, V]
}