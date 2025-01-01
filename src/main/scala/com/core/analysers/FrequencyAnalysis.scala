package com.core.analysers

import com.core.alphabets.BaseAlphabet
import com.core.cipherdata.CipherDataBlock

import scala.collection.MapView

object FrequencyAnalysis {

    /** Calculate the frequency of each element in the data.
      *
      * @param data
      * @return
      */
    def calculate[T](data: CipherDataBlock[T]): Map[T, Int] = {
        val counts =
            scala.collection.mutable.Map[T, Int](data.alphabet.values.map(x => x -> 0).toSeq*).withDefaultValue(0)
        data.foreach(counts(_) += 1)
        return counts.toMap
    }

    /** Calculate the relative frequency of each element in the data.
      *
      * Uses the alphabet of the data block to determine the possible values.
      *
      * @param data
      * @return
      */
    def relative[T](data: CipherDataBlock[T]): MapView[T, Double] = {
        relative(data, data.alphabet)
    }

    /** Calculate the relative frequency of each element in the iterable.
      *
      * @param data
      * @return
      */
    def relative[T](data: Iterable[T]): MapView[T, Double] = {
        val counts: MapView[T, Int] = data.groupBy(identity).view.mapValues(_.size)
        val sum = counts.values.sum
        return counts.mapValues(_.toDouble / sum)
    }

    /** Calculate the relative frequency of each element in the iterable.
      *
      * Uses an alphabet to ensure all possible values have a corresponding frequency. Letters in the alphabet that do
      * not appear in the data will have a frequency of 0.
      */
    def relative[T](data: Iterable[T], alphabet: BaseAlphabet[T]): MapView[T, Double] = {
        val counts =
            scala.collection.mutable.Map[T, Int](alphabet.values.map(x => x -> 0).toSeq*).withDefaultValue(0)
        data.foreach { x =>
            counts(x) += 1
        }
        return counts.mapValues(_.toDouble / data.size)
    }
}
