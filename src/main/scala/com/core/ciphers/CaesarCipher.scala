package com.core.ciphers

import com.core.cipherdata._

/** The Caesar cipher is a substitution cipher where each letter in the plaintext is shifted by a certain number of
  * places down the alphabet. It is the most primitive form of encryption.
  * 
  * The Caesar cipher will have a similar frequency distribution to the plaintext, and an identical index of coincidence.
  */
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
