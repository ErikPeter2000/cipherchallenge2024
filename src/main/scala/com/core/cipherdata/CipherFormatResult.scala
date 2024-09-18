package com.core.cipherdata

import com.core.collections.BiMap
import com.core.cipherdata._
import com.core.alphabets._
import scala.collection.mutable.TreeMap

/** Represents the result of formatting cipher data. Allows you to reinsert removed elements back into the data.
  *
  * @param formattedData
  * @param removedElements
  */
class CipherFormatResult(
    val removedElements: TreeMap[Int, Char],
    val caseChanged: List[Int],
    val originalAlphabet: Alphabet[Char]
) {

    /** Reinserts the removed elements back into the data by inserting them at the index they were removed from, and
      * changes the case of the elements that were changed.
      *
      * @param data
      * @return
      */
    def revertFormat(data: CipherDataBlock[Char]): CipherDataBlock[Char] = {
        val result = data.clone()
        result.alphabet = originalAlphabet
        removedElements.toSeq
            .sortBy(_._1)
            .foreach((index, element) => {
                result.insert(index, element)
            })
        caseChanged.foreach(index => {
            if (result(index).isUpper) result(index) = result(index).toLower
            else result(index) = result(index).toLower
        })
        result
    }
}
