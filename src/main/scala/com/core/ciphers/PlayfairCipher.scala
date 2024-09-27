package com.core.ciphers

import com.core.cipherdata.CipherDataBlock

object PlayfairCipher extends BaseCipher[Char, Char, Seq[Char]] {
    def encrypt(data: CipherDataBlock[Char], key: Seq[Char], padChars: (Char, Char)): CipherDataBlock[Char] = {
        val encrypted = data
            .grouped(2)
            .map(bigram =>
                var newBigram = bigram;
                val (a, b) = (newBigram(0), newBigram(1))
                val (aRow, aCol) = (key.indexOf(a) / 5, key.indexOf(a) % 5)
                val (bRow, bCol) = (key.indexOf(b) / 5, key.indexOf(b) % 5)

                if (aRow == bRow) {
                    val newA = key(aRow * 5 + (aCol + 1) % 5)
                    val newB = key(bRow * 5 + (bCol + 1) % 5)
                    newBigram = Seq(newA, newB)
                } else if (aCol == bCol) {
                    val newA = key(((aRow + 1) % 5) * 5 + aCol)
                    val newB = key(((bRow + 1) % 5) * 5 + bCol)
                    newBigram = Seq(newA, newB)
                } else {
                    val newA = key(aRow * 5 + bCol)
                    val newB = key(bRow * 5 + aCol)
                    newBigram = Seq(newA, newB)
                }
                newBigram
            )
            .flatten
            .toSeq
        CipherDataBlock.create(encrypted, data.alphabet)
    }
    def encrypt(data: CipherDataBlock[Char], key: Seq[Char]): CipherDataBlock[Char] = {
        encrypt(data, key, ('X', 'Q'))
    }
    def decrypt(data: CipherDataBlock[Char], key: Seq[Char]): CipherDataBlock[Char] = {
        ???
    }
}
