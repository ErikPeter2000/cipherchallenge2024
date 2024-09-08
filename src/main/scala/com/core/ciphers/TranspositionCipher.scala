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
        val groups = data.grouped(key.size)
        val encrypted = groups.map { group =>
            key.map(group(_))
        }
        new CipherDataBlock(encrypted.flatten.toSeq, data.alphabet)
    }

    def decrypt(data: CipherDataBlock[Char], key: IndexedSeq[Int]): CipherDataBlock[Char] = {
        val groups = data.grouped(key.size)
        val decrypted = groups.map { group =>
            key.indices.map(i => group(key.indexOf(i)))
        }
        new CipherDataBlock(decrypted.flatten.toSeq, data.alphabet)
    }
}
