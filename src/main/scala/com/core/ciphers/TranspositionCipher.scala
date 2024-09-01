package com.core.ciphers

import com.core.cipherdata._

/** The Transposition cipher is a simple form of encryption where the positions of the characters are changed based on a
  * key of indices.
  *
  * Data should be padded to a multiple of the key size and made from digits 0 to key.size-1, else errors may occur. Use
  * `data.padTo(targetLength, padCharacter)` to pad the data.
  *
  * Like all transposition ciphers, the index of coincidence is the same as the plaintext. The frequency distribution is
  * also the same as the plaintext.
  */
object TranspositionCipher extends BaseCipher[Char, Char, IndexedSeq[Int]] {
    def encrypt(data: CipherDataBlock[Char], key: IndexedSeq[Int]): CipherDataBlock[Char] = {
        val columnSize = data.size / key.size
        val columns = data.grouped(key.size).toSeq.transpose // split into columns
        val outData = key.map { i =>
            columns.lift(i).get
        } // reorder columns
        .flatten // flatten into a single sequence
        CipherDataBlock.createFrom(outData, data.alphabet)
    }

    def decrypt(data: CipherDataBlock[Char], key: IndexedSeq[Int]): CipherDataBlock[Char] = {
        val columnSize = data.size / key.size
        val columns = data.grouped(columnSize).toSeq // split into columns
        val outData =  key.indices.map { i =>
            columns.lift(key.indexOf(i)).get
        } // reorder columns
        .transpose// transpose back to rows
        .flatten // flatten into a single sequence
        CipherDataBlock.createFrom(outData, data.alphabet)
    }
}
