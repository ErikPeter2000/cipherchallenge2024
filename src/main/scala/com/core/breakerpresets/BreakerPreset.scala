package com.core.breakerpresets

import com.core.cipherdata.CipherDataBlock

/** BreakerPresets are designed to provide simple interfaces for turning encrypted text into plaintext.
  */
abstract trait BreakerPreset {
    def break(text: String): String
}
