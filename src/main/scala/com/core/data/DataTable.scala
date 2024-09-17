package com.core.data

import java.nio.file.Paths
import scala.collection.immutable.WrappedString

object DataTable {
    private def readFrequencyCsv(path: String): Map[String, Double] = {
        val truePath = Paths.get("./resources/").resolve(path).toAbsolutePath
        val bufferedSource = io.Source.fromFile(truePath.toFile, "UTF-8")
        val lines = bufferedSource.getLines().toList
        bufferedSource.close()
        lines.map { line =>
            val Array(key, value) = line.split(",").map(_.trim)
            key -> value.toDouble
        }.toMap
    }
    private def readListCsv(path: String): Iterator[String] = {
        val truePath = Paths.get("./resources/").resolve(path).toAbsolutePath
        val bufferedSource = io.Source.fromFile(truePath.toFile, "UTF-8")
        val lines = bufferedSource.getLines().map(_.trim.replaceAll("[\",\\s]", ""))
        new Iterator[String] {
            def hasNext: Boolean = {
                val hasMore = lines.hasNext
                if (!hasMore) bufferedSource.close()
                hasMore
            }
            def next(): String = lines.next()
        }
    }

    lazy val unigramFrequencies: Map[String, Double] = readFrequencyCsv("polygrams/Unigram.csv")
    lazy val unigramFrequenciesChar: Map[Char, Double] = unigramFrequencies.map { case (k, v) => k.head -> v }
    lazy val bigramFrequencies: Map[String, Double] = readFrequencyCsv("polygrams/Bigram.csv")
    lazy val trigramFrequencies: Map[String, Double] = readFrequencyCsv("polygrams/Trigram.csv")
    lazy val tetragramFrequencies: Map[String, Double] = readFrequencyCsv("polygrams/Tetragram.csv")
    lazy val commonWords300: Set[WrappedString] = readListCsv("englishwords/google-10000-english-no-swears.txt").filter(_.size > 3).take(300).map(_.toUpperCase.toIterable).toSet
    def iterateCommonWords: Iterator[String] = readListCsv("englishwords/google-10000-english-no-swears.txt")
    def polygramFrequenciesLog(n: Int): Map[String, Double] = {
        val polygramFrequencies = n match {
            case 1 => unigramFrequencies
            case 2 => bigramFrequencies
            case 3 => trigramFrequencies
            case 4 => tetragramFrequencies
            case _ => throw new IllegalArgumentException("Only n-grams of size 1-4 are supported")
        }
        polygramFrequencies.map { case (k, v) => k -> math.log10(v) }
    }
}
