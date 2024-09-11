package com.core.breakerpresets

import com.core.analysers.KasiskisTest
import com.core.cipherdata.CipherDataBlock
import com.core.alphabets.UppercaseLetters
import com.core.ciphers.CaesarCipher
import com.core.ciphers.VigenereCipher
import com.core.evolutionaryalgorithms.FitnessFunctions.EriksWordFitness
import com.core.progressbar.ProgressBar

object VigenereCipherBreaker extends BreakerPreset[Char, Seq[Char]] {
    def break(data: String) = {
        break(CipherDataBlock.create(data, UppercaseLetters))
    }
    def break(data: CipherDataBlock[Char]) = {
        val kasiskiTest = KasiskisTest.calculate(data)
        val bestLengths = kasiskiTest.keys.toVector.sortBy(x => -kasiskiTest(x))
        val progressBar = new ProgressBar(bestLengths.length, "VigenereCipherBreaker")
        val scoreKeyDataPairs = bestLengths.map { case length =>
            val groups = data.grouped(length).toVector.dropRight(1).transpose
            val shiftValues = groups.map { group =>
                val shift = CaesarCipherBreaker.getKey(group)._1
                UppercaseLetters(shift)
            }
            val decrypted = VigenereCipher.decrypt(data, shiftValues)
            val score = EriksWordFitness(decrypted)
            progressBar.increment()
            (score, shiftValues, decrypted)
        }
        progressBar.finish()
        val bestScore = scoreKeyDataPairs.maxBy(_._1)
        new BreakerResult(
            inData = data,
            outData = bestScore._3,
            cipherUsed = VigenereCipher,
            key = bestScore._2,
            score = bestScore._1
        )
    }
}
