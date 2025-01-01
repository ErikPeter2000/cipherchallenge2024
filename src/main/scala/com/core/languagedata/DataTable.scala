package com.core.languagedata

import com.core.alphabets.{BaseAlphabet, FastUppercaseLetters}

import java.nio.file.Paths
import scala.collection.immutable.WrappedString

/** Object containing data tables for language analysis.
  */
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
    lazy val commonWords300: Set[WrappedString] = readListCsv("englishwords/google-10000-english-no-swears.txt")
        .filter(_.size > 3)
        .take(300)
        .map(_.toUpperCase.toIterable)
        .toSet

    // slow method to get the index of any n-gram
    private def getPolygramIndex(polygram: Seq[Char], alphabet: BaseAlphabet[Char]): Int = {
        val length = polygram.length
        if (length < 1 || length > 4)
            throw new IllegalArgumentException("Only n-grams of size 1-4 are supported")
        var index = 0;
        for (i <- 0 until length) {
            index += (math.pow(alphabet.size, i) * alphabet.reverse(polygram(i))).toInt
        }
        index
    }

    /** Helper function to generate a log frequency table for n-grams. */
    private def generatePolygramLogTable(
        n: Int,
        frequencies: Map[String, Double],
        alphabet: BaseAlphabet[Char],
        default: Double = -10
    ): Vector[Double] = {
        val table = Array.fill(math.pow(alphabet.size, n).toInt)(default)
        frequencies.foreach { case (k, v) =>
            val index = getPolygramIndex(k, alphabet)
            table(index) = math.log10(v)
        }
        table.toVector
    }

    lazy val unigramFrequenciesLog = new UnigramFrequencyTable(
        generatePolygramLogTable(1, unigramFrequencies, FastUppercaseLetters)
    )
    lazy val bigramFrequenciesLog = new BigramFrequencyTable(
        generatePolygramLogTable(2, bigramFrequencies, FastUppercaseLetters)
    )
    lazy val trigramFrequenciesLog = new TrigramFrequencyTable(
        generatePolygramLogTable(3, trigramFrequencies, FastUppercaseLetters)
    )
    lazy val tetragramFrequenciesLog = new TetragramFrequencyTable(
        generatePolygramLogTable(4, tetragramFrequencies, FastUppercaseLetters)
    )

    /** Returns an iterator of the most common English words.
      */
    def iteratorCommonWords: Iterator[String] = readListCsv("englishwords/google-10000-english-no-swears.txt")

    /** Returns the log frequency table of a given n-gram. Only n-grams of size 1-4 are supported.
      *
      * @param n
      *   The size of the n-gram.
      * @return
      *   The log frequencies all n-grams of size n.
      */
    def polygramFrequenciesLog(n: Int): PolygramLookupTable = {
        val polygramFrequencies = n match {
            case 1 => unigramFrequenciesLog
            case 2 => bigramFrequenciesLog
            case 3 => trigramFrequenciesLog
            case 4 => tetragramFrequenciesLog
            case _ => throw new IllegalArgumentException("Only n-grams of size 1-4 are supported")
        }
        polygramFrequencies
    }
}
