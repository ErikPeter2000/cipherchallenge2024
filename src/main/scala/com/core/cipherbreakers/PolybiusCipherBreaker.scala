package com.core.cipherbreakers

import scala.collection.mutable

import com.core.cipherdata.CipherDataBlock
import com.core.alphabets.FastUppercaseLetters
import com.core.alphabets.PosIntAlphabet
import com.core.ciphers.PolybiusCipher
import com.core.analysers.FrequencyAnalysis
import com.core.evolutionaryalgorithms.BaseEvolutionaryAlgorithm
import com.core.alphabets.UppercaseLetters
import com.core.evolutionaryalgorithms.FitnessFunctions
import com.core.extensions.SeqExtensions._
import com.core.evolutionaryalgorithms.ChildSelectionPolicy
import com.core.keys.KeyFactory
import com.core.languagedata.DataTable

object PolybiusCipherBreaker extends CipherBreaker[Int, IndexedSeq[Char]] {
    def break(text: String, isOneBased: Boolean): BreakerResult[Char, Int, IndexedSeq[Char]] = {
        val offset = if (isOneBased) 1 else 0
        val textInt = text.map(x => x - '0' - offset)
        val dataBlock = CipherDataBlock.create(textInt, PosIntAlphabet)
        break(dataBlock)
    }
    def break(text: String): BreakerResult[Char, Int, IndexedSeq[Char]] = {
        break(text, false)
    }
    def break(data: CipherDataBlock[Int]): BreakerResult[Char, Int, IndexedSeq[Char]] = {
        break(data, 'J')
    }
    def break(data: CipherDataBlock[Int], missingLetter: Char): BreakerResult[Char, Int, IndexedSeq[Char]] = {
        val frequencies = data
            .grouped(2)
            .map(x => x(0) * 5 + x(1))
            .toSeq
            .groupBy(identity)
            .mapValues(_.size.toDouble)
            .to(mutable.Map)
        for (i <- 0 until 25) {
            if (!frequencies.contains(i)) {
                frequencies(i) = 0
            }
        }
        val initialMap = KeyFactory.createSubstitutionKeyFromFrequencies[Char, Int](frequencies.toMap, DataTable.unigramFrequenciesChar.removed(missingLetter))
        val initialKey = initialMap.keys.toIndexedSeq.sortBy(x => initialMap(x))
        val evolutionaryAlgo = new BaseEvolutionaryAlgorithm[Char, Int, IndexedSeq[Char]](
            PolybiusCipher,
            FitnessFunctions.polygramFitness(4),
            (currentKey, currentScore, generation, childIndex, maxGenerations, maxChildren) => {
                val result = currentKey.swapRandom(1)
                result.toIndexedSeq
            },
            ChildSelectionPolicy.expDfOverT(20, 0)
        )

        val result = evolutionaryAlgo.run(data, initialKey, 5000, 10, Some("Breaking Polybius Cipher"))
        new BreakerResult(data, result.outData, PolybiusCipher, result.key, result.score)
    }
}
