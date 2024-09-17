package com.core.cipherbreakers

import com.core.analysers.FrequencyAnalysis
import com.core.cipherdata.CipherDataBlock
import com.core.data.DataTable
import com.core.ciphers.CaesarCipher
import com.core.alphabets.UppercaseLetters

object CaesarCipherBreaker extends CipherBreaker[Char, Int] {
    def break(data: String) = {
        val dataBlock = CipherDataBlock.create(data, UppercaseLetters)
        break(dataBlock)
    }

    def break(data: CipherDataBlock[Char]) = {
        val keyScore = getKey(data)
        val result = CaesarCipher.decrypt(data, keyScore._1)
        new BreakerResult(
            inData = data,
            outData = result,
            cipherUsed = CaesarCipher,
            key = keyScore._1,
            score = keyScore._2
        )
    }

    def getKey(data: Iterable[Char]) = {
        val dataAnalysis = FrequencyAnalysis.relative(data, UppercaseLetters)
        val temp = dataAnalysis.toVector.sortBy(_._1);
        val dataFrequencies = temp.map(x => x._2)
        val englishAnalysis = DataTable.unigramFrequenciesChar
        val englishFrequencies = englishAnalysis.toVector.sortBy(_._1).map(_._2)
        val shiftDiffs = (0 until 26).map { shift =>
            val shiftedFrequencies = dataFrequencies.drop(shift) ++ dataFrequencies.take(shift)
            val difference = englishFrequencies.zip(shiftedFrequencies).map { case (english, data) =>
                Math.abs(english-data)*english
            }.sum
            (shift, difference)
        }.sortBy(_._2)
        val bestShift = shiftDiffs.minBy(_._2)
        bestShift
    }
}
