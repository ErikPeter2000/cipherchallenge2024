package com.core.alphabets

/** Trait for all alphabets. Alphabets are used to map characters to integers and vice versa.
  */
trait BaseAlphabet[T] extends Iterable[(Int, T)] {
    def values: Iterable[T]
    def apply(index: Int): T
    def get(key: Int): Option[T]
    def reverse(value: T): Int
    def getReverse(key: T): Option[Int]
    def contains(value: T): Boolean
}

object BaseAlphabet {
    val default: BaseAlphabet[Char] = FastUppercaseLetters
}
