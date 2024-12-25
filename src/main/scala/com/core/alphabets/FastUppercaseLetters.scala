package com.core.alphabets

/** Alphabet for uppercase letters only, that uses their unicode values.
  *
  * This may be faster than using a BiMap.
  */
object FastUppercaseLetters extends Alphabet[Char] {
    def iterator: Iterator[(Int, Char)] = (0 until 26).iterator.map(i => (i, ('A' + i).toChar))
    def values: Iterable[Char] = ('A' to 'Z')
    def apply(index: Int): Char = ('A' + index).toChar
    def get(key: Int): Option[Char] = if (key >= 0 && key < 26) Some(('A' + key).toChar) else None
    def reverse(value: Char): Int = value - 'A'
    def getReverse(key: Char): Option[Int] = if (key >= 'A' && key <= 'Z') Some(key - 'A') else None
    def contains(key: Int): Boolean = key >= 0 && key < 26
    def contains(value: Char): Boolean = value >= 'A' && value <= 'Z'
}
