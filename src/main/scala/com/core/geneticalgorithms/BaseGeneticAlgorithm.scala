package com.core.geneticalgorithms

import com.core.ciphers.BaseCipher
import com.core.cipherdata.CipherDataBlock

class BaseGeneticAlgorithm[T, K, V](cipher: BaseCipher[T, K, V], key: V, data: CipherDataBlock[T]) {
    def run(): Unit = {
        // ...
    }
}
