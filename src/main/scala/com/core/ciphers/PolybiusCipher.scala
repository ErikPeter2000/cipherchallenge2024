package com.core.ciphers

import com.core.cipherdata.CipherDataBlock
import com.core.cipherdata.CipherResult
import com.core.alphabets.Alphabet
import com.core.alphabets.PosIntAlphabet

/** A Polybius cipher maps the letters of the alphabet to a coordinate in a 5x5 grid. The result is then given as a flat list of coordinates.
  *
  * This grid is usually the alphabet with an uncommon letter like J or Q omitted.
  * 
  * Polybius ciphertext can be converted to substitution plaintext by taking every two digits and converting them to a letter using the grid.
  */
object PolybiusCipher extends BaseCipher[Char, Int, IndexedSeq[Char]] {
    def encrypt(data: CipherDataBlock[Char], key: IndexedSeq[Char], width: Int): CipherDataBlock[Int] = {
        var result = new scala.collection.mutable.ArrayBuffer[Int]()
        data.foreach { x =>
            val index = key.indexOf(x)
            if (index == -1) {
                result += -1
                result += -1
            } else {
                result += index / width
                result += index % width
            }
        }
        CipherDataBlock.create(result.toSeq, PosIntAlphabet)
    }

    def decrypt(data: CipherDataBlock[Int], key: IndexedSeq[Char], width: Int): CipherDataBlock[Char] = {
        var result = new scala.collection.mutable.ArrayBuffer[Char]()
        data.grouped(2).foreach { x =>
            if (x(0) == -1 || x.size < 2) {
                result += '\u0000'
            } else {
                result += key(x(0) * width + x(1))
            }
        }
        CipherDataBlock.create(result.toSeq, Alphabet.default)
    }

    def encrypt(data: CipherDataBlock[Char], key: IndexedSeq[Char]): CipherDataBlock[Int] = {
        val width = Math.ceil(Math.sqrt(key.size)).toInt
        return encrypt(data, key, width)
    }

    def decrypt(data: CipherDataBlock[Int], key: IndexedSeq[Char]): CipherDataBlock[Char] = {
        val width = Math.ceil(Math.sqrt(key.size)).toInt
        return decrypt(data, key, width)
    }
}
