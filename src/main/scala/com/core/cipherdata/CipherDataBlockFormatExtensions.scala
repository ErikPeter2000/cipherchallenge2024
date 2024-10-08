package com.core.cipherdata

import com.core.cipherdata.CipherDataBlock
import com.core.cipherdata.CipherFormatResult
import com.core.alphabets.Alphabet

object CipherDataBlockFormatExtensions {
    extension (cipherDataBlock: CipherDataBlock[Char]) {
        def format(alphabet: Alphabet[Char]): CipherFormatResult = {
            val removedElements = scala.collection.mutable.TreeMap[Int, Char]()
            val caseSwapped = scala.collection.mutable.ListBuffer[Int]()
            var data = cipherDataBlock.data
            for (i <- data.size - 1 to 0 by -1) {
                if (data(i).isLower) {
                    data(i) = data(i).toUpper
                    caseSwapped += i
                }
                if (!alphabet.contains(data(i))) {
                    removedElements += i -> data(i)
                    data.remove(i)
                }
            }
            cipherDataBlock.alphabet = Alphabet.default
            new CipherFormatResult(removedElements, caseSwapped.toList, cipherDataBlock.alphabet)
        }
        def format(): CipherFormatResult = {
            format(Alphabet.default)
        }
    }
}
