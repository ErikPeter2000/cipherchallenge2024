package com.core.analysers

import com.core.cipherdata.CipherDataBlock
import scala.compiletime.ops.double
import scala.collection.MapView

object FrequencyAnalysis {
    def calculate[T](data: CipherDataBlock[T]): Map[T, Int] = {
        val counts = scala.collection.mutable.Map[T, Int](data.alphabet.values.map(x => x -> 0).toSeq*).withDefaultValue(0)
        data.foreach(counts(_) += 1)
        return counts.toMap
    }
    def calculateRelative[T](data: CipherDataBlock[T]): MapView[T, Double] = {
        val counts = scala.collection.mutable.Map[T, Int](data.alphabet.values.map(x => x -> 0).toSeq*).withDefaultValue(0)
        var sum: Double = 0
        data.foreach { x =>
            counts(x) += 1
            sum += 1
        }
        val total = data.length
        return counts.mapValues(_.toDouble / total)
    }
}
