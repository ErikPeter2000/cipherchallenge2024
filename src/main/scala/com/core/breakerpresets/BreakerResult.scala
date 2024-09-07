package com.core.breakerpresets

import com.core.ciphers.BaseCipher
import com.core.cipherdata.CipherDataBlock

class BreakerResult[T, K, V](
    val inData: CipherDataBlock[T],
    val outData: CipherDataBlock[K],
    val cipherUsed: BaseCipher[T, K, V],
    val key: V,
    val score: Double
) {
    def textData: String = outData.mkString
    def toXml: String = {
        val keyString = key match {
            case seq: Seq[_] => seq.mkString(",")
            case _ => key.toString
        }
        s"""<BreakerResult>
           |  <inData>${inData.mkString}</inData>
           |  <outData>${outData.mkString}</outData>
           |  <cipherUsed>${cipherUsed.getClass.getName}</cipherUsed>
           |  <key>$keyString</key>
           |  <score>$score</score>
           |</BreakerResult>""".stripMargin
    }
    override def toString(): String = textData
}