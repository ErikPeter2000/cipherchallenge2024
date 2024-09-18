package com.core.cipherdata

import com.core.alphabets._
import com.core.extensions._
import scala.collection.mutable.ArrayBuffer
import com.core.cipherdata.CipherDataBlockFormatExtensions.format

/** Represents the plaintext or ciphertext data for a cipher. This class is mutable, and most methods will modify the
  * data in place.
  * @tparam T
  *   The type of the data in the cipher, usually `Char`.
  */
class CipherDataBlock[T](
    var data: ArrayBuffer[T],
    var alphabet: Alphabet[T]
) extends Seq[T] {
    def this(data: Seq[T], alphabet: Alphabet[T]) = {
        this(ArrayBuffer(data*), alphabet)
    }
    def this(alphabet: Alphabet[T]) = {
        this(ArrayBuffer(), alphabet)
    }
    def apply(index: Int): T = data(index)
    def update(index: Int, elem: T): Unit = data(index) = elem
    def iterator: Iterator[T] = data.iterator
    def length: Int = data.length

    def padTo(length: Int, elem: T): CipherDataBlock[T] = {
        data = data.padTo(length, elem)
        return this
    }

    /** Pads the data block to a multiple of the given length with the given element. Performs the padding in place, and
      * returns itself.
      *
      * @param length
      *   The length to pad to.
      * @param elem
      *   The element to pad with.
      * @return
      *   The data block itself.
      */
    def padToMultiple(length: Int, elem: T): CipherDataBlock[T] = {
        val targetLength = (data.length + length - 1) / length * length
        data = data.padTo(targetLength, elem)
        return this
    }

    /** Transposes the data block with the given pad character. This is the same as organising the data into a grid with
      * the specified width and/or height, padding it, and then reading along the columns. Performs the transpose in
      * place, and returns itself.
      *
      * @param padCharacter
      * @param inputWidth
      * @param inputHeight
      */
    def transpose(
        padCharacter: T,
        inputWidth: Option[Int] = None,
        inputHeight: Option[Int] = None
    ): CipherDataBlock[T] = {
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
        data = data.grouped(width).toSeq.transpose.flatten.to(ArrayBuffer)
        return this
    }

    def swapValues(item1: T, item2: T): CipherDataBlock[T] = {
        data = data.map { item =>
            item.match
                case `item1` => item2
                case `item2` => item1
                case _       => item
        }
        return this
    }

    /** Removes the element at the given index.
      */
    def remove(index: Int): T = {
        data.remove(index)
    }

    /** Removes the element at the given index.
      */
    def insert(index: Int, elem: T): Unit = {
        data.insert(index, elem)
    }

    override def clone(): CipherDataBlock[T] = {
        val newData = data.clone()
        new CipherDataBlock[T](newData, alphabet)
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
        new CipherDataBlock[Char](Alphabet.default)
    }

    /** Creates a new CipherDataBlock with the given data and alphabet.
      *
      * @param data
      *   The data for the cipher.
      * @param alphabet
      *   The alphabet for the cipher.
      * @return
      */
    def create[T](
        data: Seq[T],
        alphabet: Alphabet[T]
    ): CipherDataBlock[T] = {
        new CipherDataBlock[T](data, alphabet)
    }
    def create(
        data: Seq[Char],
    ): CipherDataBlock[Char] = {
        new CipherDataBlock[Char](data, Alphabet.default)
    }

    /** Creates a new CipherDataBlock with the given data and alphabet.
      */
    def create(data: String, alphabet: Alphabet[Char]): CipherDataBlock[Char] = {
        new CipherDataBlock(data, alphabet)
    }

    def create(data: String): CipherDataBlock[Char] = {
        new CipherDataBlock(data, Alphabet.default)
    }

    /** Creates a new CipherDataBlock with the given data and the default UppercaseAlphabet. Formats the data to remove
      * any non-alphabetic characters and convert lowercase characters to uppercase.
      *
      * @param data
      *   The data for the cipher.
      * @return
      *   A tuple containing the new CipherDataBlock and the result of the formatting operation.
      */
    def formatAndCreate(data: String): (CipherDataBlock[Char], CipherFormatResult) = {
        val instance = new CipherDataBlock(data, Alphabet.default)
        val formatResult = instance.format()
        (instance, formatResult)
    }
}
