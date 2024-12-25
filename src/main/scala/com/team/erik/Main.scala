package com.team.erik

import scala.collection.mutable
import scala.io.Source
import scala.util.control.Breaks._
import scala.collection.parallel.CollectionConverters._
import java.nio.file.{Files, Paths, StandardOpenOption}

import com.core._
import com.core.alphabets._
import com.core.keys.KeyFactory
import com.core.languagedata.DataTable
import com.core.evolutionaryalgorithms._
import com.core.ciphers._
import com.core.extensions._
import com.core.analysers._
import com.core.cipherdata._
import com.core.extensions.BiMapExtensions._
import com.core.collections._
import com.core.extensions.StringExtensions.highlight
import com.core.progressbar.ProgressBar
import com.core.keys.KeyFactory.random
import com.core.extensions.IterableExtensions.pretty
import com.core.cipherbreakers._
import com.core.extensions.SeqExtensions.swapRandom
import main.breakers.CipherBreakerOutput

object Main {
    def loadData(): (CipherDataBlock[Char], CipherFormatResult) = {
        val path = Paths.get(".\\resources\\text\\Orwell1984.txt")
        val text = Source.fromFile(path.toFile, "UTF-8").take(1000).mkString
        CipherDataBlock.formatAndCreate(text, UppercaseLettersNoJ)
    }

    def saveText(text: String, path: String): Unit = {
        if (Files.exists(Paths.get(path))) {
            throw new Exception(s"File already exists: $path")
        }
        Files.write(Paths.get(path), text.getBytes(), StandardOpenOption.CREATE)
    }

    def readFromFile(path: String): String = {
        Source.fromFile(path).mkString
    }

    def calculateFactors(n: Int): Seq[Int] = {
        (2 to n).filter(n % _ == 0)
    }

    def reorderSequence[T](sequence: Seq[T], permutation: Seq[Int]): Seq[T] = {
        permutation.map(sequence(_))
    }

    def job(ciphertext: String): BreakerResult[Char, Char, BiMap[Char, Char]] = {
        /* Decryption of 10B involves two steps:
         1. Apply a permutation cipher on the ciphertext by rearranging every block of `n` characters.
            This key is found to be (6, 2, 1, 3, 5, 4, 0).
         2. Apply a substitution cipher for every block of 6 characters to convert from Wheatstone's encoding to English plaintext.

         We can identify the permutation key by brute-force.
         A permutation key that results in the fewest combinations of 6 characters is likely to be the correct key.
         */

        // The permutation key length must be a factor of the ciphertext length.
        // We restrict to all keys that are shorter than 8, since it will take too long to brute-force longer permutations.
        val possiblePermutationKeyLengths = calculateFactors(ciphertext.length).filter(_ < 8)

        // Generate all possible permutations of the key length.
        val permutations = possiblePermutationKeyLengths.flatMap(length => (0 until length).permutations)

        // For each permutation, calculate the number of distinct blocks and the transposed ciphertext.
        val permutationResults = permutations
            .map(permutationKey => { // For each possible permutation key...
                val transposedCiphertext = ciphertext
                    .grouped(permutationKey.size) // Group into blocks of the key size
                    .flatMap(block => permutationKey.map(block(_))) // Rearrange the blocks according to the permutation key
                    .toSeq

                // Count the number of distinct blocks in the transposed ciphertext.
                val numberOfDistinctBlocks = transposedCiphertext.grouped(6).distinct.size
                // Return the permutation key, the number of distinct blocks, and the transposed ciphertext.
                (permutationKey, numberOfDistinctBlocks, transposedCiphertext)
            })
            .sortBy(_._2) // Sort by the number of distinct blocks ascending.

        // Print the first 10 keys and the number of distinct blocks for debugging.
        println(permutationResults.take(10).map(x => (x._1, x._2)).mkString("\n"))
        // Take the transposed ciphertext of the permutation that resulted in the fewest distinct blocks.
        val transposedCiphertext = permutationResults.head._3

        // Convert the transposed ciphertext that uses Wheatstone's encoding to an English alphabet for substitution decryption.
        // This is done by first finding all possible distinct blocks of 6 characters...
        val alphabet = transposedCiphertext.grouped(6).toSeq.distinct
        // ...and then mapping these blocks to an English alphabet letter.
        val textInEnglishAlphabet =
            transposedCiphertext.grouped(6).map(letter => (alphabet.indexOf(letter) + 'A').toChar).mkString

        // Decrypt the text using a hill-climbing algorithm.
        val brokenData = MonoAlphabeticSubstitutionCipherBreaker.break(textInEnglishAlphabet)
        println(brokenData.textData)

        return brokenData
    }

    def main(args: Array[String]): Unit = {
        val ciphertext = readFromFile(".\\src\\main\\scala\\com\\team\\erik\\ciphertext.txt")
        val broken = job(ciphertext)
    }
}
