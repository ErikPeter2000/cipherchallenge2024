package com.core.cipherdata

import com.core.cipherdata.CipherDataBlock
import com.core.cipherdata.CipherFormatResult
import com.core.alphabets.UppercaseLetters

object CipherDataBlockFormatExtensions {
    private val charFilter = "[^A-Z]".r
    extension (cipherDataBlock: CipherDataBlock[Char]) {
        def format(): CipherFormatResult = {
            val removedElements = scala.collection.mutable.TreeMap[Int, Char]()
            val caseSwapped = scala.collection.mutable.ListBuffer[Int]()
            var data = cipherDataBlock.data
            for (i <- data.size - 1 to 0 by -1) {
                if (data(i).isLower) {
                    data(i) = data(i).toUpper
                    caseSwapped += i
                }
                if (charFilter.matches(data(i).toString)) {
                    removedElements += i -> data(i)
                    data.remove(i)
                }
            }
            cipherDataBlock.alphabet = UppercaseLetters
            new CipherFormatResult(removedElements, caseSwapped.toList, cipherDataBlock.alphabet)
        }
    }
}
