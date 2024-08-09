package com.core.ciphers

import com.core.cipherdata._

object TranspositionCipher extends BaseCipher[Char, Char, IndexedSeq[Int]] {
    def encrypt(data: CipherDataBlock[Char], key: IndexedSeq[Int]): CipherResult[Char, Char] = {
        val outData = data.grouped(key.size).flatMap { group =>
            key.indices.flatMap { i =>
                group.lift(key(i))
            }
        }.toIndexedSeq
        CipherResult.create(data, outData, data.alphabet)
    }

    def decrypt(data: CipherDataBlock[Char], key: IndexedSeq[Int]): CipherResult[Char, Char] = {
        val outData = data.grouped(key.size).flatMap { group =>
            key.indices.flatMap { i =>
                group.lift(key.indexOf(i))
            }
        }.toIndexedSeq
        CipherResult.create(data, outData, data.alphabet)
    }
}
