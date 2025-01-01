package com.core.ciphers

import com.core.cipherdata.CipherDataBlock
import com.core.collections.BiMap

/** The Four Square Cipher is a polyalphabetic cipher that uses four 5x5 grids to encrypt and decrypt data.
  *
  * The Key is expected to be a Vector of four BiMaps[Int, Char] to represent the four grids, reading from left to
  * right, then top to bottom.
  *
  * Encryption is as follows:
  * 
  * 1. The plaintext is split into bigrams.
  *
  * 2. The coordinate of the first letter is found in the first grid and the coordinate of the second letter is found in
  * the last grid. Call these A: (x1, y1) and B: (x2, y2).
  *
  * 3. The letters are encrypted by mixing their coordinates: A' = (x2, y1) and B' = (x1, y2).
  *
  * 4. The letters at A' in the second grid and B' in the third are the encrypted letters. They are concatenated to form
  * the ciphertext.
  */
object FourSquareCipher extends BaseCipher[Char, Char, Vector[BiMap[Int, Char]]] {
    def encrypt(data: CipherDataBlock[Char], key: Vector[BiMap[Int, Char]]): CipherDataBlock[Char] = {
        val alphabet = data.alphabet
        val dataLength = data.length
        val dataPairs = data.grouped(2).toVector
        val encryptedData = dataPairs
            .map(pair => {
                val (a, b) = (pair(0), pair(1))
                val indexA = key(0).reverse(a)
                val (aRow, aCol) = (indexA / 5, indexA % 5)
                val indexB = key(3).reverse(b)
                val (bRow, bCol) = (indexB / 5, indexB % 5)
                val encryptedA = key(1)(aRow * 5 + bCol)
                val encryptedB = key(2)(bRow * 5 + aCol)
                Vector(encryptedA, encryptedB)
            })
            .flatten
        new CipherDataBlock(encryptedData, alphabet)
    }
    def decrypt(data: CipherDataBlock[Char], key: Vector[BiMap[Int, Char]]): CipherDataBlock[Char] = {
        val alphabet = data.alphabet
        val dataLength = data.length
        val dataPairs = data.grouped(2).toVector
        val decryptedData = dataPairs
            .map(pair => {
                val (a, b) = (pair(0), pair(1))
                val indexA = key(1).reverse(a)
                val (aRow, aCol) = (indexA / 5, indexA % 5)
                val indexB = key(2).reverse(b)
                val (bRow, bCol) = (indexB / 5, indexB % 5)
                val decryptedA = key(0)(aRow * 5 + bCol)
                val decryptedB = key(3)(bRow * 5 + aCol)
                Vector(decryptedA, decryptedB)
            })
            .flatten
        new CipherDataBlock(decryptedData, alphabet)
    }
}
