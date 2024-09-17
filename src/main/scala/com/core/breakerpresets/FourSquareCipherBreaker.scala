package com.core.breakerpresets

import com.core.collections.BiMap
import com.core.cipherdata.CipherDataBlock
import com.core.ciphers.FourSquareCipher
import com.core.extensions.BiMapExtensions._
import com.core.evolutionaryalgorithms._
import com.core.alphabets.UppercaseLetters

object FourSquareCipherBreaker extends BreakerPreset[Char, Vector[BiMap[Int, Char]]] {
    def break(data: CipherDataBlock[Char]): BreakerResult[Char, Char, Vector[BiMap[Int, Char]]] = {
        val breaker = new BaseEvolutionaryAlgorithm[Char, Char, Vector[BiMap[Int, Char]]](
            FourSquareCipher,
            FitnessFunctions.polygramFitness(2),
            (
                currentKey: Vector[BiMap[Int, Char]],
                currentScore: Double,
                generation: Int,
                childIndex: Int,
                maxGenerations: Int,
                maxChildren: Int
            ) => {
                if (generation % 2 == 0) {
                    Vector(
                        currentKey(0),
                        currentKey(1),
                        currentKey(2).clone().swapElements(1),
                        currentKey(3)
                    )
                } else {
                    Vector(
                        currentKey(0),
                        currentKey(1).clone().swapElements(1),
                        currentKey(2),
                        currentKey(3)
                    )
                }
            },
            ChildSelectionPolicy.expDfOverT(200, 0)
        );
        
        val alphabet = data.alphabet
        if (alphabet.size != 25) {
            throw new IllegalArgumentException("FourSquareCipherBreaker requires an alphabet of size 25")
        }
        val initialKey = Vector(
            alphabet.toBiMap,
            alphabet.toBiMap.shuffleValues(),
            alphabet.toBiMap.shuffleValues(),
            alphabet.toBiMap
        )
        val result = breaker.run(data, initialKey, 2000, 10, Some("Breaking Four Square Cipher"))

        new BreakerResult(
            inData = data,
            outData = result.outData,
            cipherUsed = FourSquareCipher,
            key = result.key,
            score = result.score
        )
    }

    def break(text: String): BreakerResult[Char, Char, Vector[BiMap[Int, Char]]] = {
        break(CipherDataBlock.create(text, UppercaseLetters.dropLetter('J')))
    }
}
