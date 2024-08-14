package com.core.ciphers

import breeze.linalg._
import com.core.cipherdata.CipherDataBlock
import com.core.cipherdata.CipherResult

/** A Hill cipher is a polygraphic substitution cipher that uses a matrix.
  *
  * The key is an `n` x `n` invertible matrix. The plaintext is grouped into blocks of `n` and multiplied by the key.
  * Their product is then taken modulo the size of the alphabet to get the ciphertext.
  */

object HillCipher extends BaseCipher[Char, Char, DenseMatrix[Int]] {
    def encrypt(data: CipherDataBlock[Char], key: DenseMatrix[Int]): CipherResult[Char, Char] = {
        val keySize = key.rows
        val dataLength = data.length
        val dataBlocks = data.map(data.alphabet.reverse).grouped(keySize).toVector
        val encrypted = dataBlocks.flatMap { block =>
            val blockMatrix = DenseMatrix(block*).t
            val encryptedBlock = (key * blockMatrix)
            encryptedBlock.data.map(i => data.alphabet(i % data.alphabet.size))
        }
        CipherResult.create(data, encrypted, data.alphabet)
    }
    def decrypt(data: CipherDataBlock[Char], key: DenseMatrix[Int]): CipherResult[Char, Char] = {
        val inverse = inv(key).map(_.toInt)
        encrypt(data, inverse)
    }
}
