package com.core.ciphers

import com.core.cipherdata.CipherDataBlock
import com.core.cipherdata.CipherResult

/** The Vigenere cipher is a polyalphabetic substitution cipher that uses a repeating keyword to shift the plaintext.
  *
  * It will have an index of coincidence between 0.04 and 0.05, depending on the length of the keyword. (Longer keys
  * have lower IoCs).
  *
  * The length of the key can be determined using Kasiski examination, or by using the Friedman test:
  *
  * `L = (K_p - K_t) / (K_o - K_o) = (0.067 - K_t) / (0.0385 - K_o)`
  *
  * Where `L` is the key length, `K_p` is the IoC of the plaintext, `K_t` is the IoC of the text, and `K_o` is the IoC
  * of the alphabet.
  */
object VigenereCipher extends BaseCipher[Char, Char, Seq[Char]] {

    /** Encrypt the data using the Vigenere cipher.
      */
    def encrypt(data: CipherDataBlock[Char], key: Seq[Char]): CipherDataBlock[Char] = {
        val alphabet = data.alphabet
        val encrypted = data.zipWithIndex.map { case (c, i) =>
            val shift = key(i % key.length)
            val shiftIndex = alphabet.reverse(shift)
            val charIndex = alphabet.reverse(c)
            data.alphabet((charIndex + shiftIndex) % data.alphabet.size)
        }
        CipherDataBlock.create(encrypted, alphabet)
    }

    /** Decrypt the data using the Vigenere cipher.
      */
    def decrypt(data: CipherDataBlock[Char], key: Seq[Char]): CipherDataBlock[Char] = {
        val alphabet = data.alphabet
        val decrypted = data.zipWithIndex.map { case (c, i) =>
            val shift = key(i % key.length)
            val shiftIndex = alphabet.reverse(shift)
            val charIndex = alphabet.reverse(c)
            data.alphabet((charIndex - shiftIndex + data.alphabet.size) % data.alphabet.size)
        }
        CipherDataBlock.create(decrypted, alphabet)
    }
}
