package com.core.ciphers

import com.core.cipherdata.CipherDataBlock

/** The Caesar cipher is a substitution cipher where each letter in the plaintext is shifted by a certain number of
  * places down the alphabet. It is the most primitive form of encryption.
  *
  * The Caesar cipher will have a similar frequency distribution to the plaintext, and an identical index of
  * coincidence.
  */
object CaesarCipher extends BaseCipher[Char, Char, Int] {

    /** Encrypt the data using the Caesar cipher.
      */
    def encrypt(data: CipherDataBlock[Char], key: Int): CipherDataBlock[Char] = {
        val alphabet = data.alphabet
        val plaintext = data.map(alphabet.getReverse)
        val encrypted = plaintext.map(x => (x.get + key) % alphabet.size).map(alphabet(_))
        CipherDataBlock.create(encrypted, alphabet)
    }

    /** Decrypt the data using the Caesar cipher.
      */
    def decrypt(data: CipherDataBlock[Char], key: Int): CipherDataBlock[Char] = {
        val alphabet = data.alphabet
        val ciphertext = data.map(alphabet.getReverse)
        val decrypted = ciphertext.map(x => (x.get - key + alphabet.size) % alphabet.size).map(alphabet(_))
        CipherDataBlock.create(decrypted, alphabet)
    }
}
