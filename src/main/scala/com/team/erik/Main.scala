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

object Main {
    def loadData(): CipherDataBlock[Char] = {
        val path = Paths.get(".\\resources\\text\\Orwell1984.txt")
        val text = Source.fromFile(path.toFile, "UTF-8").take(1000).mkString.toUpperCase.replaceAll("[^A-Z]", "")
        new CipherDataBlock(text, UppercaseLetters)
    }

    def job(args: Array[String]): Unit = {
        val data = loadData().padToMultiple(6, 'X')

        val key = Vector(1,0,5,4,2,3)
        val encrypted = TranspositionCipher.encrypt(data, key)

        val broken = TranspositionCipherBreaker.break(encrypted.mkString)
        println(broken)
    }

    def main(args: Array[String]): Unit = {
        job(args)
    }
}
