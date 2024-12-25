package com.core.ciphers

import com.core.cipherdata._
import com.core.collections.BiMap

/** The Substitution cipher is a simple form of encryption where each character in the plaintext is replaced by another
  * character.
  *
  * The Substitution cipher has the same index of coincidence as the plaintext, but the frequency distribution is
  * different.
  */
object SubstitutionCipher extends BaseCipher[Char, Char, BiMap[Char, Char]] {

    /** Decrypt the data using the Substitution cipher.
      */
    def decrypt(data: CipherDataBlock[Char], key: BiMap[Char, Char]): CipherDataBlock[Char] = {
        val alphabet = data.alphabet
        val ciphertext = data.map(x => key.getReverse(x, x))
        CipherDataBlock.create(ciphertext, alphabet)
    }

    /** Encrypt the data using the Substitution cipher.
      */
    def encrypt(data: CipherDataBlock[Char], key: BiMap[Char, Char]): CipherDataBlock[Char] = {
        val alphabet = data.alphabet
        val ciphertext = data.map(x => key.get(x, x))
        CipherDataBlock.create(ciphertext, alphabet)
    }
}
