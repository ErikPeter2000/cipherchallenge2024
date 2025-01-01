package com.core.alphabets

/** Alphabet for all non-negative integers (includes 0).
  */
object PosIntAlphabet extends BaseAlphabet[Int] {
    def iterator: Iterator[(Int, Int)] = Iterator.from(0).map(x => (x, x))
    override def values: Iterable[Int] = new Iterable[Int] {
        def iterator: Iterator[Int] = Iterator.from(0)
    }
    override def apply(index: Int): Int = index
    override def get(key: Int): Option[Int] = if (key >= 0) Option(key) else None
    override def reverse(value: Int): Int =
        if (value >= 0) value else throw new IllegalArgumentException("Value must be a positive integer.")
    override def getReverse(key: Int): Option[Int] = if (key >= 0) Option(key) else None
    override def contains(value: Int): Boolean = value >= 0
}
