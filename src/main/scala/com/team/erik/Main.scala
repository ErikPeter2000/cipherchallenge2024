package com.team.erik

import scala.io.Source
import scala.util.control.Breaks._
import scala.collection.parallel.CollectionConverters._
import java.nio.file.Paths

import com.core._
import com.core.alphabets._
import com.core.keys.KeyFactory
import com.core.data.DataTable
import com.core.evolutionaryalgorithms.SubstitutionEvolutionaryAlgorithm
import com.core.ciphers._
import com.core.collections.KeyPairOrder
import com.core.collections.IterableExtensions.pretty
import com.core.analysers._
import com.core.cipherdata.CipherDataBlock

object Main {
    def loadData(): CipherDataBlock[Char] = {
        val path = Paths.get(".\\resources\\text\\Orwell1984.txt")
        val text = Source.fromFile(path.toFile, "UTF-8").take(100).mkString.toUpperCase.replaceAll("[^A-Z]", "")
        new CipherDataBlock(text, UppercaseLetters)
    }

    def job(args: Array[String]): Unit = {
        val data = loadData()
        println(data.mkString)
    }

    def main(args: Array[String]): Unit = {
        job(args)
    }
}
