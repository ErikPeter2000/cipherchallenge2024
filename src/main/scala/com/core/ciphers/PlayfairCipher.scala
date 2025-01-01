package com.core.ciphers

import com.core.cipherdata.CipherDataBlock

/** The Playfair cipher is a digraph substitution cipher that encrypts pairs of characters. It locates the coordinates
  * of two letters in a bigram in a 5x5 grid, and then mixes their positions.
  */
object PlayfairCipher extends BaseCipher[Char, Char, Seq[Char]] {

    /** Encrypts the given data using the Playfair cipher.
      *
      * @param data
      * @param key
      *   25 unique characters to use as the 5x5 grid.
      * @param padChars
      *   The two distinct characters to use for padding.
      * @return
      */
    def encrypt(data: CipherDataBlock[Char], key: Seq[Char], padChars: (Char, Char)): CipherDataBlock[Char] = {
        val encrypted = data.tail
            // Pad bigrams that are the same character
            // We need to do some additional checks because this rule only applies to consecutive bigrams, not consecutive characters
            // For example, CAAB is left unpadded because CA and AB are separate bigrams, whilst AAAA has to be padded to AXAXAXAX because:
            // AAAA -> AA AA -> AXA AXA -> AX AA XA -> AX AXA XA -> AX AX AX AX
            // The left-fold is powerful enough to handle this
            .foldLeft(Seq(data.head))((acc, char) =>
                if (acc.length % 2 == 0 || acc.last != char) acc :+ char
                else if (char == padChars._1) acc :+ padChars._2 :+ char
                else acc :+ padChars._1 :+ char
            )
            .grouped(2) // Take bigrams
            .map(bigram =>
                // Get the first and last characters of the bigram, padding if necessary
                val (a, b) =
                    if (bigram.length == 1 && bigram.head == padChars._1) (bigram.head, padChars._2)
                    else if (bigram.length == 1) (bigram.head, padChars._1)
                    else (bigram.head, bigram.last)

                // Get the row and column of each character in the key
                val (aRow, aCol) = (key.indexOf(a) / 5, key.indexOf(a) % 5)
                val (bRow, bCol) = (key.indexOf(b) / 5, key.indexOf(b) % 5)

                // Perform the encryption
                if (aRow == bRow) {
                    val newA = key(aRow * 5 + (aCol + 1) % 5)
                    val newB = key(bRow * 5 + (bCol + 1) % 5)
                    Seq(newA, newB)
                } else if (aCol == bCol) {
                    val newA = key(((aRow + 1) % 5) * 5 + aCol)
                    val newB = key(((bRow + 1) % 5) * 5 + bCol)
                    Seq(newA, newB)
                } else {
                    val newA = key(aRow * 5 + bCol)
                    val newB = key(bRow * 5 + aCol)
                    Seq(newA, newB)
                }
            )
            .flatten
            .toSeq
        CipherDataBlock.create(encrypted, data.alphabet)
    }

    /** Encrypts the given data using the Playfair cipher.
      *
      * This method uses the default padding characters 'X' and 'Q'.
      *
      * @param data
      * @param key
      *   25 unique characters to use as the 5x5 grid.
      * @return
      */
    def encrypt(data: CipherDataBlock[Char], key: Seq[Char]): CipherDataBlock[Char] = {
        encrypt(data, key, ('X', 'Q'))
    }

    /** Decrypts the given data using the Playfair cipher.
      *
      * @param data
      * @param key
      *   25 unique characters to use as the 5x5 grid.
      * @return
      */
    def decrypt(data: CipherDataBlock[Char], key: Seq[Char]): CipherDataBlock[Char] = {
        val decrypted = data
            .grouped(2)
            .map(bigram =>
                val (a, b) = (bigram.head, bigram.last)

                val (aRow, aCol) = (key.indexOf(a) / 5, key.indexOf(a) % 5)
                val (bRow, bCol) = (key.indexOf(b) / 5, key.indexOf(b) % 5)

                if (aRow == bRow) {
                    val newA = key(aRow * 5 + (aCol + 4) % 5)
                    val newB = key(bRow * 5 + (bCol + 4) % 5)
                    Seq(newA, newB)
                } else if (aCol == bCol) {
                    val newA = key(((aRow + 4) % 5) * 5 + aCol)
                    val newB = key(((bRow + 4) % 5) * 5 + bCol)
                    Seq(newA, newB)
                } else {
                    val newA = key(aRow * 5 + bCol)
                    val newB = key(bRow * 5 + aCol)
                    Seq(newA, newB)
                }
            )
            .flatten
            .toSeq
        CipherDataBlock.create(decrypted, data.alphabet)
    }
}
