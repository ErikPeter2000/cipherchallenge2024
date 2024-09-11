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
import com.core.extensions.BiMapExtensions._
import com.core.collections._
import com.core.extensions.StringExtensions.highlight
import com.core.progressbar.ProgressBar

object Main {
    def loadData(): CipherDataBlock[Char] = {
        val path = Paths.get(".\\resources\\text\\Orwell1984.txt")
        val text = Source.fromFile(path.toFile, "UTF-8").take(1000).mkString.toUpperCase.replaceAll("[^A-ZJ]", "")
        new CipherDataBlock(text, UppercaseLetters)
    }

    def job(args: Array[String]): Unit = {
        val data = loadData()
        val noJ = UppercaseLetters.dropLetter('J')
        val key = Vector(
            noJ.toBiMap,
            noJ.toBiMap.swapElements(26),
            noJ.toBiMap.swapElements(26),
            noJ.toBiMap,
        )
        val encrypted = FourSquareCipher.encrypt(data, key)

        println(encrypted.mkString)

        val breaker = new BaseEvolutionaryAlgorithm[Char, Char, Vector[BiMap[Int, Char]]](
            FourSquareCipher,
            FitnessFunctions.QuadgramFitness,
            (currentKey, currentScore, generation, childIndex, maxGenerations, maxChildren) => {
                val newKey = Vector(
                    currentKey(0).clone(),
                    currentKey(1).clone().swapElements(1),
                    currentKey(2).clone().swapElements(1),
                    currentKey(3).clone(),
                )
                newKey
            },
            ChildSelectionPolicy.expDfOverT(10)
        )
        
        val startKey = Vector(
            noJ.toBiMap,
            noJ.toBiMap,
            noJ.toBiMap,
            noJ.toBiMap,
        )

        val result = breaker.run(encrypted, startKey, 50000, 5, Option("FourSquareCipherBreaker"))
        println(result.outData.mkString)
    }

    def main(args: Array[String]): Unit = {
        job(args)
    }
}
