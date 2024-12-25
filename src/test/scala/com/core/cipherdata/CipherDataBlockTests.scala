package com.core.cipherdata

import com.core.alphabets._

class CipherDataBlockTest extends munit.FunSuite {

    test("CipherDataBlock should be initialized with a custom alphabet") {
        val BiMapAlphabet = new BiMapAlphabet[Char] {
            biMap ++= Seq(
                0 -> 'A',
                1 -> 'B',
                2 -> 'C'
            )
        }
        val cipherDataBlock = CipherDataBlock.empty(BiMapAlphabet)
        assertEquals(cipherDataBlock.alphabet, BiMapAlphabet)
        assertEquals(cipherDataBlock.length, 0)
    }

    test("CipherDataBlock should be initialized with the default UppercaseAlphabet") {
        val cipherDataBlock = CipherDataBlock.empty()
        assertEquals(cipherDataBlock.alphabet, Alphabet.default)
        assertEquals(cipherDataBlock.length, 0)
    }

    test("CipherDataBlock should allow adding elements and accessing them by index") {
        val cipherDataBlock = CipherDataBlock.create(Seq('A', 'B', 'C'))
        assertEquals(cipherDataBlock(0), 'A')
        assertEquals(cipherDataBlock(1), 'B')
        assertEquals(cipherDataBlock(2), 'C')
    }

    test("CipherDataBlock should return the correct length") {
        val cipherDataBlock = CipherDataBlock.create(Seq('A', 'B', 'C'))
        assertEquals(cipherDataBlock.length, 3)
    }

    test("CipherDataBlock iterator should iterate over all elements") {
        val cipherDataBlock = CipherDataBlock.create(Seq('A', 'B', 'C'))
        val iterator = cipherDataBlock.iterator
        assertEquals(iterator.next(), 'A')
        assertEquals(iterator.next(), 'B')
        assertEquals(iterator.next(), 'C')
        assert(!iterator.hasNext)
    }

    test("CipherDataBlock padTo should add elements to the end of the data") {
        val cipherDataBlock = CipherDataBlock.create(Seq('A', 'B', 'C'))
        cipherDataBlock.padTo(5, 'D')
        assertEquals(cipherDataBlock(3), 'D')
        assertEquals(cipherDataBlock(4), 'D')
    }

    test("CipherDataBlock transpose should swap elements at the given indices with width") {
        val cipherDataBlock = CipherDataBlock.create(Seq('A', 'B', 'C'))
        cipherDataBlock.transpose('\u0000', inputWidth = Option(2))
        assertEquals(cipherDataBlock(1), 'C')
        assertEquals(cipherDataBlock(2), 'B')
        assertEquals(cipherDataBlock(3), '\u0000')
    }

    test("CipherDataBlock transpose should swap elements at the given indices with height") {
        val cipherDataBlock = CipherDataBlock.create(Seq('A', 'B', 'C', 'D', 'E', 'F'))
        cipherDataBlock.transpose('\u0000', inputHeight = Option(2))
        assertEquals(cipherDataBlock(1), 'D')
        assertEquals(cipherDataBlock(2), 'B')
        assertEquals(cipherDataBlock(3), 'E')
    }

    test("CipherDataBlock.swapValues should swap the values of two elements") {
        val cipherDataBlock = CipherDataBlock.create(Seq('A', 'B', 'C', 'A'))
        cipherDataBlock.swapValues('A', 'C')
        assertEquals(cipherDataBlock(0), 'C')
        assertEquals(cipherDataBlock(2), 'A')
        assertEquals(cipherDataBlock(3), 'C')
    }
}
