package com.core.cipherbreakers

import com.core.ciphers.BaseCipher
import com.core.cipherdata.CipherDataBlock
import com.core.collections.BiMap

/** Represents the result of a cipher breaker.
  *
  * @param inData
  *   The input data that was given to the breaker. This is not necessarily the ciphertext for the Challenge.
  * @param outData
  *   The output data that was produced by the breaker.
  * @param cipherUsed
  *   The cipher that was used to decrypt the data.
  * @param key
  *   The key that was used to produce the output data.
  * @param score
  *   An arbitrary score that measures the fitness of the output data.
  * @param note
  *   A note that can be used to describe the result. Useful for describing additional steps that were taken to decrypt
  *   the data.
  */
class BreakerResult[T, K, V](
    val inData: CipherDataBlock[K],
    val outData: CipherDataBlock[T],
    val cipherUsed: BaseCipher[T, K, V],
    val key: V,
    val score: Double,
    val note: String = ""
) {
    def textData: String = outData.mkString
    def xmlData: String = {
        val keyString = key match {
            case biMap: BiMap[_, _] => biMap.keys.toSeq.sortBy(_.toString).map(x => biMap(x))
            case seq: Seq[_]        => seq.mkString(",")
            case _                  => key.toString
        }
        s"""<BreakerResult>
           |  <inData>${inData.mkString}</inData>
           |  <outData>${outData.mkString}</outData>
           |  <cipherUsed>${cipherUsed.getClass.getName}</cipherUsed>
           |  <key>$keyString</key>
           |  <notes>$note</notes>
           |  <score>$score</score>
           |</BreakerResult>""".stripMargin
    }
    override def toString(): String = textData
}
