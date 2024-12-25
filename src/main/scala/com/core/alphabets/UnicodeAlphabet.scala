package com.core.alphabets

/** Alphabet for all characters, using their Unicode values.
  *
  * Only use this when you do not care about order. It is better to use a more specific alphabet.
  */
object UnicodeAlphabet extends Alphabet[Char] {
    def iterator: Iterator[(Int, Char)] = Iterator.from(0).map(x => (x, x.toChar))
    def values: Iterable[Char] = new Iterable[Char] {
        def iterator: Iterator[Char] = Iterator.from(0).map(_.toChar)
    }
    def apply(index: Int): Char = index.toChar
    def get(key: Int): Option[Char] = if (key >= 0) Option(key.toChar) else None
    def reverse(value: Char): Int = value.toInt
    def getReverse(key: Char): Option[Int] = Option(key.toInt)
    def contains(value: Char): Boolean = value.toInt >= 0
}
