package com.core.keys

import scala.util.Random

import com.core.alphabets._
import com.core.collections.BiMap
import com.core.alphabets.UppercaseLetters

/** Utility functions for keys based on characters.
  */
object KeyFactory {
    lazy val random = new Random()

    /** Combines a phrase with an alphabet, to create a sequence of distinct letters.
      *
      * Useful for generating a different keys.
      *
      * @example
      *   {{{
      * KeyFactory.combinePhraseWithAlphabet("hello", LowercaseLetters) -> Seq('h', 'e', 'l', 'o', 'a', 'b', 'c', 'd', 'f', 'g', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')
      *   }}}
      *
      * @param phrase
      *   The input phrase. Duplicate letters are removed.
      * @param alphabet
      *   The target alphabet. All letters in the phrase should be in the alphabet, else errors will occur.
      * @return
      *   A sequence of distinct letters.
      */
    def combinePhraseWithAlphabet(phrase: String, alphabet: BiMapAlphabet[Char]): IndexedSeq[Char] = {
        val distinctPhrase = phrase.distinct.filter(alphabet.contains)
        val letters = new Array[Char](alphabet.size)
        distinctPhrase.copyToArray(letters)
        var count = distinctPhrase.length
        alphabet.foreach { case (index, letter) =>
            if (!letters.contains(letter)) {
                letters(count) = letter
                count += 1
            }
        }
        letters
    }

    /** Creates a substitution key based on the given phrase.
      *
      * @param phrase
      * @param inputAlphabet
      * @return
      *   A BiMap representing the substitution key.
      */
    def createSubstitutionKey(phrase: String, inputAlphabet: BiMapAlphabet[Char]): BiMap[Char, Char] = {
        val letters = combinePhraseWithAlphabet(phrase, inputAlphabet)
        return inputAlphabet.createLetterMapAgainst(letters)
    }

    /** Creates a random substitution key based on the given alphabet.
      *
      * @param inputAlphabet
      *   The alphabet to create the key from.
      * @return
      *   A BiMap representing the substitution key.
      */
    def createRandomSubstitutionKey(inputAlphabet: BiMapAlphabet[Char], seed: Option[Int] = None): BiMap[Char, Char] = {
        seed.foreach(random.setSeed(_))
        val letters = inputAlphabet.iterator.map(_._2).toSeq
        val shuffledLetters = random.shuffle(letters)
        return inputAlphabet.createLetterMapAgainst(new BiMapAlphabet[Char](shuffledLetters))
    }

    def createReverseSubstitutionKeyFromFrequencies[T](
        inputAlphabet: Alphabet[T],
        currentFrequencies: Map[T, Double],
        targetFrequencies: Map[T, Double],
        seed: Option[Int] = None
    ): BiMap[T, T] = {
        val inKeys = currentFrequencies.keys.toSeq.sortBy(currentFrequencies(_))
        val outKeys = targetFrequencies.keys.toSeq.sortBy(targetFrequencies(_))
        new BiMap[T, T](outKeys.zip(inKeys))
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
    def createTranspositionKey(phrase: String, alphabet: BiMapAlphabet[Char]): IndexedSeq[Int] = {
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
      * not in the alphabet. Length is expected to be a multiple of the key length for correct results.
      * @example
      *   {{{createTranspositionKey("hello", LowercaseLetters, 11) -> IndexedSeq(1, 0, 2, 3, 4, 6, 5, 7, 8, 9, 10)}}}
      *
      * @param phrase
      *   The phrase to create the key from.
      * @param alphabet
      *   The alphabet to use for the key.
      * @param length
      *   The length of the key.
      * @return
      */
    def createTranspositionKey(phrase: String, alphabet: BiMapAlphabet[Char], length: Int): IndexedSeq[Int] = {
        val original = createTranspositionKey(phrase, alphabet)
        return (0 until length).map(i => original(i % original.length) + i / original.length * original.length)
    }
}
