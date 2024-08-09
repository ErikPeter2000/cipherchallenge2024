package com.core.analysers

import scala.collection.parallel.CollectionConverters._

/** Class for counting the frequency of elements in a contiguous data block.
  */
object FrequencyCounter {
    def calculate[T](data: Seq[T], keys: Set[Seq[T]]): Map[Seq[T], Int] = {
        keys.view.map(key => (key, data.view.sliding(key.length).count(_.sameElements(key)))).toMap
    }
    def calculate(data: String, keys: Set[String]): Map[String, Int] = {
        keys.par.map(key => (key, data.sliding(key.length).count(_ == key))).toMap.seq
    }
}
