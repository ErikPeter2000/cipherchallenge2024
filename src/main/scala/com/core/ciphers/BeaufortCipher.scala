package com.core.ciphers

import com.core.cipherdata.CipherDataBlock
import com.core.cipherdata.CipherResult

object BeaufortCipher extends BaseCipher[Char, Char, Seq[Char]] {
    def encrypt(data: CipherDataBlock[Char], key: Seq[Char]): CipherResult[Char, Char] = {
        val alphabet = data.alphabet
        val encrypted = data.zipWithIndex.map { case (c, i) =>
            val shift = key(i % key.length)
            val shiftIndex = alphabet.reverse(shift)
            val charIndex = alphabet.reverse(c)
            data.alphabet((shiftIndex - charIndex + data.alphabet.size) % data.alphabet.size)
        }
        CipherResult.create(data, encrypted, data.alphabet)
    }
    def decrypt(data: CipherDataBlock[Char], key: Seq[Char]): CipherResult[Char, Char] = {
        val alphabet = data.alphabet
        val decrypted = data.zipWithIndex.map { case (c, i) =>
            val shift = key(i % key.length)
            val shiftIndex = alphabet.reverse(shift)
            val charIndex = alphabet.reverse(c)
            data.alphabet((shiftIndex - charIndex + data.alphabet.size) % data.alphabet.size)
        }
        CipherResult.create(data, decrypted, data.alphabet)
    }
}
