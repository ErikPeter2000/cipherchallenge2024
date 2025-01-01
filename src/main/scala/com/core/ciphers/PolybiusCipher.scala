package com.core.ciphers

import com.core.alphabets.BaseAlphabet
import com.core.alphabets.PosIntAlphabet
import com.core.cipherdata.CipherDataBlock

/** A Polybius cipher maps the letters of the alphabet to a coordinate in a 5x5 grid. The result is then given as a flat
  * list of coordinates.
  *
  * This grid is usually the alphabet with an uncommon letter like J or Q omitted.
  *
  * Polybius ciphertext can be converted to substitution plaintext by taking every two digits and converting them to a
  * letter using the grid.
  */
object PolybiusCipher extends BaseCipher[Char, Int, IndexedSeq[Char]] {

    /** Encrypt the data using the Polybius cipher.
      * @param data
      *   The data to encrypt.
      * @param key
      *   The key to use for encryption.
      * @param gridWidth
      *   The width of the grid.
      */
    def encrypt(data: CipherDataBlock[Char], key: IndexedSeq[Char], gridWidth: Int): CipherDataBlock[Int] = {
        var result = new scala.collection.mutable.ArrayBuffer[Int]()
        data.foreach { x =>
            val index = key.indexOf(x)
            if (index == -1) {
                result += -1
                result += -1
            } else {
                result += index / gridWidth
                result += index % gridWidth
            }
        }
        CipherDataBlock.create(result.toSeq, PosIntAlphabet)
    }

    /** Decrypt the data using the Polybius cipher.
      * @param data
      *   The data to decrypt.
      * @param key
      *   The key to use for decryption.
      * @param gridWidth
      *   The width of the grid.
      */
    def decrypt(data: CipherDataBlock[Int], key: IndexedSeq[Char], gridWidth: Int): CipherDataBlock[Char] = {
        var result = new scala.collection.mutable.ArrayBuffer[Char]()
        data.grouped(2).foreach { x =>
            if (x(0) == -1 || x.size < 2) {
                result += '\u0000'
            } else {
                result += key(x(0) * gridWidth + x(1))
            }
        }
        CipherDataBlock.create(result.toSeq, BaseAlphabet.default)
    }

    /** Encrypt the data using the Polybius cipher.
      *
      * The grid width is calculated as the square root of the key size, rounded up.
      * @param data
      *   The data to encrypt.
      * @param key
      *   The key to use for encryption.
      */
    def encrypt(data: CipherDataBlock[Char], key: IndexedSeq[Char]): CipherDataBlock[Int] = {
        val width = Math.ceil(Math.sqrt(key.size)).toInt
        return encrypt(data, key, width)
    }

    /** Decrypt the data using the Polybius cipher.
      *
      * The grid width is calculated as the square root of the key size, rounded up.
      * @param data
      *   The data to decrypt.
      * @param key
      *   The key to use for decryption.
      */
    def decrypt(data: CipherDataBlock[Int], key: IndexedSeq[Char]): CipherDataBlock[Char] = {
        val width = Math.ceil(Math.sqrt(key.size)).toInt
        return decrypt(data, key, width)
    }
}
