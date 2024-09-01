package com.core.cipherdata

import com.core.alphabets._

/** Represents the plaintext or ciphertext data for a cipher. This class is mutable, and most methods will modify the
  * data in place.
  * @tparam T
  *   The type of the data in the cipher, usually `Char`.
  */
class CipherDataBlock[T](val alphabet: Alphabet[T]) extends Seq[T] {
    private var data: Seq[T] = Seq.empty[T]

    def this(data: Seq[T], alphabet: Alphabet[T]) = {
        this(alphabet)
        this.data = data
    }
    def apply(index: Int): T = data(index)
    def iterator: Iterator[T] = data.iterator
    def length: Int = data.length

    def padTo(length: Int, elem: T): CipherDataBlock[T] = {
        data = data.padTo(length, elem)
        return this
    }

    /** Transposes the data block with the given pad character. This is the same as organising the data into a grid with
      * the specified width and/or height, padding it, and then reading along the columns.
      * Performs the transpose in place, and returns itself.
      *
      * @param padCharacter
      * @param inputWidth
      * @param inputHeight
      */
    def transpose(padCharacter: T, inputWidth: Option[Int] = None, inputHeight: Option[Int] = None): CipherDataBlock[T] = {
        if (inputWidth.isEmpty && inputHeight.isEmpty) {
            throw new IllegalArgumentException("Invalid input width or height")
        }

        // Calculate dimensions
        var targetLength = 0 // The length of the data after padding
        var width = 0 // The width of the grid, calculated from the input width or height
        if (inputWidth.isDefined) {
            width = inputWidth.get
            if (inputHeight.isDefined) {
                targetLength = inputWidth.get * inputHeight.get
                if (data.length > targetLength) {
                    throw new IllegalArgumentException("Data length is greater than the target length")
                }
            } else {
                targetLength = (data.length + width - 1) / width * width
            }
        } else {
            width = (data.length + inputHeight.get - 1) / inputHeight.get
            targetLength = width * inputHeight.get
        }

        // Pad
        data = data.padTo(targetLength, padCharacter)

        // Transpose
        data = data.grouped(width).toSeq.transpose.flatten
        return this
    }
}

object CipherDataBlock {

    /** Creates a new CipherDataBlock with the given alphabet and data.
      * @param alphabet
      *   The alphabet for the cipher data.
      * @return
      *   A new CipherDataBlock with the given alphabet and data.
      */
    def empty[T](alphabet: Alphabet[T]): CipherDataBlock[T] = {
        new CipherDataBlock[T](alphabet)
    }

    /** Creates a new CipherDataBlock with the default UppercaseAlphabet.
      * @return
      *   A new CipherDataBlock with the default UppercaseAlphabet.
      */
    def empty(): CipherDataBlock[Char] = {
        new CipherDataBlock[Char](UppercaseLetters)
    }

    def createFrom[T](
        data: Seq[T],
        alphabet: Alphabet[T]
    ): CipherDataBlock[T] = {
        new CipherDataBlock[T](data, alphabet)
    }
    def create(data: String): CipherDataBlock[Char] = {
        new CipherDataBlock[Char](data, UppercaseLetters)
    }
}
