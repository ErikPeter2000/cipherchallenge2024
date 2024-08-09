package com.core.ciphers

import com.core.cipherdata._

object CaesarCipher extends BaseCipher[Char, Char, Int] {
    def encrypt(data: CipherDataBlock[Char], key: Int): CipherResult[Char, Char] = {
        val alphabet = data.alphabet
        val plaintext = data.map(alphabet.getReverse)
        val ciphertext = plaintext.map(x => (x.get + key) % alphabet.size).map(alphabet.get(_).get)
        CipherResult.create(data, ciphertext, alphabet)
    }

    def decrypt(data: CipherDataBlock[Char], key: Int): CipherResult[Char, Char] = {
        val alphabet = data.alphabet
        val ciphertext = data.map(alphabet.getReverse)
        val plaintext = ciphertext.map(x => (x.get - key + alphabet.size) % alphabet.size).map(alphabet.get(_).get)
        CipherResult.create(data, plaintext, alphabet)
    }
}
