package com.core.data

import java.nio.file.Paths

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
    lazy val quadgramFrequencies: Map[String, Double] = readFrequencyCsv("polygrams/Quadgram.csv")
    def iterateCommonWords: Iterator[String] = readListCsv("englishwords/google-10000-english-no-swears.txt")
}
