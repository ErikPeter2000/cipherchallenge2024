package com.core.languagedata

/** Trait for quickly looking up the frequency of polygrams using an array.
  */
trait PolygramLookupTable(val table: Vector[Double]) {
    def lookup(index: Int): Double = table(index)
    def lookup(polygram: Seq[Char]): Double
}

class UnigramFrequencyTable(table: Vector[Double]) extends PolygramLookupTable(table) {
    def lookup(polygram: Seq[Char]): Double = {
        val index = polygram(0) - 'A'
        lookup(index)
    }
}

class BigramFrequencyTable(table: Vector[Double]) extends PolygramLookupTable(table) {
    def lookup(polygram: Seq[Char]): Double = {
        val index = polygram(0) - 'A' + (polygram(1) - 'A') * 26
        lookup(index)
    }
}

class TrigramFrequencyTable(table: Vector[Double]) extends PolygramLookupTable(table) {
    def lookup(polygram: Seq[Char]): Double = {
        val index = polygram(0) - 'A' + (polygram(1) - 'A') * 26 + (polygram(2) - 'A') * 676
        lookup(index)
    }
}

class TetragramFrequencyTable(table: Vector[Double]) extends PolygramLookupTable(table) {
    def lookup(polygram: Seq[Char]): Double = {
        val index = polygram(0) - 'A' + (polygram(1) - 'A') * 26 + (polygram(2) - 'A') * 676 + (polygram(3) - 'A') * 17576
        lookup(index)
    }
}
