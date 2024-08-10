package com.core.keys

import scala.util.Random

import com.core.alphabets.BaseAlphabet
import com.core.collections.BiMap
import com.core.alphabets.UppercaseLetters

/** Utility functions for keys based on characters.
  */
object KeyFactory {

    /** Creates a substitution key based on the given phrase.
      *
      * @param phrase
      * @param inputAlphabet
      * @return
      *   A BiMap representing the substitution key.
      */
    def createSubstitutionKey(phrase: String, inputAlphabet: BaseAlphabet[Char]): BiMap[Char, Char] = {
        val distinctPhrase = phrase.distinct.filter(inputAlphabet.contains)
        val letters = new Array[Char](inputAlphabet.size)
        distinctPhrase.copyToArray(letters)
        var count = distinctPhrase.length
        inputAlphabet.foreach { case (index, letter) =>
          if (!letters.contains(letter)) {
            letters(count) = letter
            count += 1
          }
        }
        return inputAlphabet.createLetterMapAgainst(letters)
    }

    /** Creates a random substitution key based on the given alphabet.
      *
      * @param inputAlphabet
      *   The alphabet to create the key from.
      * @return
      *   A BiMap representing the substitution key.
      */
    def createRandomSubstitutionKey(inputAlphabet: BaseAlphabet[Char]): BiMap[Char, Char] = {
        val letters = inputAlphabet.iterator.map(_._2).toSeq
        val shuffledLetters = Random.shuffle(letters)
        return inputAlphabet.createLetterMapAgainst(new BaseAlphabet[Char](shuffledLetters))
    }

    /** Creates a transposition key based on the given phrase. Ignores characters not in the alphabet. Example: "hello"
      * -> Seq(1, 0, 2, 3, 4)
      *
      * @param phrase
      *   The phrase to create the key from.
      * @param alphabet
      *   The alphabet to use for the key.
      * @return
      *   The transposition key.
      */
    def createTranspositionKey(phrase: String, alphabet: BaseAlphabet[Char]): IndexedSeq[Int] = {
        val phraseSorted = phrase.filter(alphabet.contains)
        val sortedPhrase = alphabet.sortCollection(phraseSorted).toArray
        val indices = phraseSorted.map(x => {
            val index = sortedPhrase.indexOf(x)
            sortedPhrase(index) = '\u0000'
            index
        })
        return indices
    }

    /** Creates a transposition key based on the given phrase. Repeats the key to the given length. Ignores characters
      * not in the alphabet.
      * Length is expected to be a multiple of the key length for correct results.
      * @example
      * {{{createTranspositionKey("hello", LowercaseLetters, 11) -> IndexedSeq(1, 0, 2, 3, 4, 6, 5, 7, 8, 9, 10)}}}
      *
      * @param phrase
      *   The phrase to create the key from.
      * @param alphabet
      *   The alphabet to use for the key.
      * @param length
      *   The length of the key.
      * @return
      */
    def createTranspositionKey(phrase: String, alphabet: BaseAlphabet[Char], length: Int): IndexedSeq[Int] = {
        val original = createTranspositionKey(phrase, alphabet)
        return (0 until length).map(i => original(i % original.length) + i / original.length * original.length)
    }
}
