package com.core.cipherbreakers

import com.core.ciphers.BaseCipher
import com.core.cipherdata.CipherDataBlock
import com.core.collections.BiMap

class BreakerResult[T, K, V](
    val inData: CipherDataBlock[K],
    val outData: CipherDataBlock[T],
    val cipherUsed: BaseCipher[T, K, V],
    val key: V,
    val score: Double
    ) {
    var notes: String = ""
    def textData: String = outData.mkString
    def toXml: String = {
        val keyString = key match {
            case biMap: BiMap[_, _] => biMap.keys.toSeq.sortBy(_.toString).map(x => biMap(x))
            case seq: Seq[_] => seq.mkString(",")
            case _ => key.toString
        }
        s"""<BreakerResult>
           |  <inData>${inData.mkString}</inData>
           |  <outData>${outData.mkString}</outData>
           |  <cipherUsed>${cipherUsed.getClass.getName}</cipherUsed>
           |  <key>$keyString</key>
           |  <notes>$notes</notes>
           |  <score>$score</score>
           |</BreakerResult>""".stripMargin
    }
    override def toString(): String = textData
}