package com.core.ciphers

import scala.math._
import com.core.cipherdata._

/** The Column cipher is a transposition cipher that encrypts data by writing it into a grid, transposing the columns,
  * and then reading the data back out in columns.
  */
object ColumnCipher extends BaseCipher[Char, Char, IndexedSeq[Int]] {

    /** Encrypts the data using the Columnar Transposition cipher.
      *
      * Data should be padded to a multiple of the key size and made from digits 0 to key.size-1, else errors may occur.
      * Use `data.padTo(targetLength, padCharacter)` to pad the data.
      *
      * @param data
      *   The data to encrypt.
      * @param key
      *   A set of integers from 0 to key.size-1, representing the desired order of columns.
      */
    def encrypt(data: CipherDataBlock[Char], key: IndexedSeq[Int]): CipherDataBlock[Char] = {
        if (data.length % key.size != 0) {
            throw new IllegalArgumentException("Data length must be a multiple of the key size.")
        }
        val columnSize = (data.length + key.size - 1) / key.size
        val columns = data.grouped(key.size).toSeq.transpose
        val encrypted = key.map(columns).flatten
        CipherDataBlock.create(encrypted, data.alphabet)
    }

    /** Decrypts the data using the Columnar Transposition cipher.
      *
      * Data should be padded to a multiple of the key size and made from digits 0 to key.size-1, else errors may occur.
      * Use `data.padTo(targetLength, padCharacter)` to pad the data.
      *
      * @param data
      *   The data to decrypt.
      * @param key
      *   A set of integers from 0 to key.size-1, representing the current order of columns.
      */
    def decrypt(data: CipherDataBlock[Char], key: IndexedSeq[Int]): CipherDataBlock[Char] = {
        if (data.length % key.size != 0) {
            throw new IllegalArgumentException("Data length must be a multiple of the key size.")
        }
        val columnSize = (data.length + key.size - 1) / key.size
        val columns = data.grouped(columnSize).toSeq
        val decrypted = (0 to key.size - 1).map(i => columns(key.indexOf(i))).transpose.flatten
        CipherDataBlock.create(decrypted, data.alphabet)
    }
}
