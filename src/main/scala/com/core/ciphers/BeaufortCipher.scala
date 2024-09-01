package com.core.ciphers

import com.core.cipherdata.CipherDataBlock
import com.core.cipherdata.CipherResult

/** The Beaufort cipher is similar to the Vigenère cipher, but the key is subtracted from the plaintext instead of added.
  * 
  * It is also susceptible to Kasiski examination.
  */
object BeaufortCipher extends BaseCipher[Char, Char, Seq[Char]] {
    def encrypt(data: CipherDataBlock[Char], key: Seq[Char]): CipherDataBlock[Char] = {
        val alphabet = data.alphabet
        val encrypted = data.zipWithIndex.map { case (c, i) =>
            val shiftValue = key(i % key.length)
            val shiftIndex = alphabet.reverse(shiftValue)
            val charIndex = alphabet.reverse(c)
            data.alphabet((shiftIndex - charIndex + data.alphabet.size) % data.alphabet.size)
        }
        CipherDataBlock.createFrom(encrypted, alphabet)
    }
    def decrypt(data: CipherDataBlock[Char], key: Seq[Char]): CipherDataBlock[Char] = {
        val alphabet = data.alphabet
        val decrypted = data.zipWithIndex.map { case (c, i) =>
            val shiftValue = key(i % key.length)
            val shiftIndex = alphabet.reverse(shiftValue)
            val charIndex = alphabet.reverse(c)
            data.alphabet((shiftIndex - charIndex + data.alphabet.size) % data.alphabet.size)
        }
        CipherDataBlock.createFrom(decrypted, alphabet)
    }
}
