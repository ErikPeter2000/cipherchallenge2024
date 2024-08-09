package com.core.ciphers

import com.core.cipherdata._
import com.core.collections.BiMap

object SubstitutionCipher extends BaseCipher[Char, Char, BiMap[Char, Char]] {
    def decrypt(data: CipherDataBlock[Char], key: BiMap[Char, Char]): CipherResult[Char, Char] = {
        val alphabet = data.alphabet
        val ciphertext = data.map(x => key.getReverse(x, x))
        CipherResult.create(data, ciphertext, alphabet)
    }
    def encrypt(data: CipherDataBlock[Char], key: BiMap[Char, Char]): CipherResult[Char, Char] = {
        val alphabet = data.alphabet
        val ciphertext = data.map(x => key.get(x, x))
        CipherResult.create(data, ciphertext, alphabet)
    }
}
