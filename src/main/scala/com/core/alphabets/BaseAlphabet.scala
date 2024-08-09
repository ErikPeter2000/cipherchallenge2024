package com.core.alphabets

import com.core.collections._

/** Base class for all alphabets. Acts as a bidirectional map between the indices and the letters of the alphabet.
  * @tparam T
  *   The type of the letters in the alphabet.
  */
class BaseAlphabet[T] extends Iterable[(Int, T)] {
    protected val biMap: BiMap[Int, T] = BiMap.empty[Int, T]

    def this(pairs: Seq[T]) = {
        this()
        pairs.zipWithIndex.foreach { case (letter, index) =>
            biMap += (index -> letter)
        }
    }
    def this(letters: Iterable[(Int, T)]) = {
        this()
        this.foreach(biMap += _)
    }
    override def iterator: Iterator[(Int, T)] = biMap.iterator
    def get(key: Int): Option[T] = biMap.get(key)
    def getReverse(key: T): Option[Int] = biMap.getReverse(key)
    def contains(value: T): Boolean = biMap.containsValue(value)

    /** Returns a new bidirectional map against another alphabet by mapping the letters of this alphabet to the letters
      * of the other alphabet.
      * Ignores any letters that are not in both alphabets.
      *
      * @param other
      *   The other alphabet to map letters against.
      * @return
      *   A bidirectional map between the letters of this alphabet and the letters of the other alphabet.
      */
    def createLetterMapAgainst(other: BaseAlphabet[T]): BiMap[T, T] = {
        val newMap = BiMap.empty[T, T]
        this.foreach { case (index, letter) =>
            other.get(index) match {
                case Some(otherLetter) => newMap += (letter -> otherLetter)
                case None              =>
            }
        }
        return newMap
    }

    /** Returns a new bidirectional map against another alphabet by mapping the letters of this alphabet to the letters
      * Exceptions may occur if this alphabet has indices that are not in the other alphabet.
      *
      * @param other
      *   The other alphabet to map letters against.
      * @return
      *   A bidirectional map between the letters of this alphabet and the letters of the other alphabet.
      */
    def createLetterMapAgainst(other: Seq[T]): BiMap[T, T] = {
        if (this.size > other.size) {
            throw new IllegalArgumentException("The other alphabet must contain at least as many letters as this alphabet.")
        }
        val newMap = BiMap.empty[T, T]
        this.foreach { case (index, letter) =>
            newMap += (letter -> other(index))
        }
        return newMap
    }

    /** Returns a new bidirectional map against another alphabet by mapping the indices of this alphabet to the indices
      * of the other alphabet.
      *
      * @param other
      *   The other alphabet to map indices against.
      * @return
      *   A bidirectional map between the indices of this alphabet and the
      */
    def createIndexMapAgainst(other: BaseAlphabet[T]): BiMap[Int, Int] = {
        val newMap = BiMap.empty[Int, Int]
        this.foreach { case (index, letter) =>
            other.getReverse(letter) match {
                case Some(otherIndex) => newMap += (index -> otherIndex)
                case None             =>
            }
        }
        return newMap
    }

    /**
      * Sorts a collection of letters ascending based on their position in the alphabet.
      *
      * @param collection The collection of letters to sort.
      * @return The sorted collection of letters.
      */
    def sortCollection(collection: Seq[T]): Seq[T] = {
        collection.sortBy(x => biMap.getReverse(x))
    }

    /**
      * Returns a copy of the alphabet as a bidirectional map.
      * @return
      */
    def toBiMap: BiMap[Int, T] = {
        val newBiMap = BiMap.empty[Int, T]
        newBiMap ++= biMap
        newBiMap
    }
}
