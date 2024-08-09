package com.core.ciphers

import scala.math._
import com.core.cipherdata._

object ColumnCipher extends BaseCipher[Char, Char, IndexedSeq[Int]] {
    def encrypt(data: CipherDataBlock[Char], key: IndexedSeq[Int]): CipherResult[Char, Char] = {
        val columnSize = (data.length + key.size - 1) / key.size
        val columns = data.transpose('\u0000', inputWidth=Option(key.size), inputHeight=Option(columnSize)).grouped(columnSize).toList
        val outData = key.map(columns).flatten
        CipherResult.create(data, outData, data.alphabet)
    }

    def decrypt(data: CipherDataBlock[Char], key: IndexedSeq[Int]): CipherResult[Char, Char] = {
        val columnSize = (data.length + key.size - 1) / key.size        
        val columns = data.transpose('\u0000', inputWidth=Option(key.size), inputHeight=Option(columnSize)).grouped(columnSize).toList
        val outData = key.map(x => columns(key.indexOf(x))).flatten
        CipherResult.create(data, outData, data.alphabet)
    }  
}
