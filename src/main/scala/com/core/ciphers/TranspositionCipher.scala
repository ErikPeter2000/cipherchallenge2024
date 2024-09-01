package com.core.ciphers

import com.core.cipherdata._

/**
  * The Transposition cipher is a simple form of encryption where the positions of the characters are changed based on a key of indices.  
  * 
  * Like all transposition ciphers, the index of coincidence is the same as the plaintext. The frequency distribution is also the same as the plaintext.
  */
object TranspositionCipher extends BaseCipher[Char, Char, IndexedSeq[Int]] {
    def encrypt(data: CipherDataBlock[Char], key: IndexedSeq[Int]): CipherDataBlock[Char] = {
        val outData = data.grouped(key.size).flatMap { group =>
            key.indices.flatMap { i =>
                group.lift(key(i))
            }
        }.toIndexedSeq
        CipherDataBlock.createFrom(outData, data.alphabet)
    }

    def decrypt(data: CipherDataBlock[Char], key: IndexedSeq[Int]): CipherDataBlock[Char] = {
        val outData = data.grouped(key.size).flatMap { group =>
            key.indices.flatMap { i =>
                group.lift(key.indexOf(i))
            }
        }.toIndexedSeq
        CipherDataBlock.createFrom(outData, data.alphabet)
    }
}
