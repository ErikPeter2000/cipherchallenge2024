package com.team.erik

import scala.io.Source
import scala.util.control.Breaks._
import scala.collection.parallel.CollectionConverters._
import java.nio.file.Paths

import com.core._
import com.core.alphabets._
import com.core.keys.KeyFactory
import com.core.data.DataTable
import com.core.evolutionaryalgorithms._
import com.core.ciphers._
import com.core.extensions._
import com.core.analysers._
import com.core.cipherdata._
import com.core.breakerpresets._
import com.core.extensions.BiMapExtensions.shuffleValues
import com.core.collections._

object Main {
    def loadData(): CipherDataBlock[Char] = {
        val path = Paths.get(".\\resources\\text\\Orwell1984.txt")
        val text = Source.fromFile(path.toFile, "UTF-8").take(1000).mkString.toUpperCase.replaceAll("[^A-ZJ]", "")
        new CipherDataBlock(text, UppercaseLetters)
    }

    def job(args: Array[String]): Unit = {
        val data = loadData()
        val freqOriginal = FrequencyAnalysis.relative(data)

        val lettersNoJ = UppercaseLetters.dropLetter('J')
        val key1 = BiMap.createFromSeq(KeyFactory.combinePhraseWithAlphabet("HELLO", lettersNoJ))
        val key2 = BiMap.createFromSeq(KeyFactory.combinePhraseWithAlphabet("WORLD", lettersNoJ))
        val key3 = BiMap.createFromSeq(KeyFactory.combinePhraseWithAlphabet("FOOBAR", lettersNoJ))
        val key4 = BiMap.createFromSeq(KeyFactory.combinePhraseWithAlphabet("BARFOO", lettersNoJ))

        val totalKey = Vector(key1, key2, key3, key4)
        val encrypted = FourSquareCipher.encrypt(data, totalKey)
        println(encrypted.mkString)
        val decrypted = FourSquareCipher.decrypt(encrypted, totalKey)
        println(decrypted.mkString)
    }

    def main(args: Array[String]): Unit = {
        job(args)
    }
}
