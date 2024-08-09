package com.core.cipherdata

import com.core.alphabets._

class CipherDataBlockTest extends munit.FunSuite {

    test("CipherDataBlock should be initialized with a custom alphabet") {
        val customAlphabet = new BaseAlphabet[Char] {
            biMap ++= Seq(
                0 -> 'A',
                1 -> 'B',
                2 -> 'C'
            )
        }
        val cipherDataBlock = CipherDataBlock.empty(customAlphabet)
        assertEquals(cipherDataBlock.alphabet, customAlphabet)
        assertEquals(cipherDataBlock.length, 0)
    }

    test("CipherDataBlock should be initialized with the default UppercaseAlphabet") {
        val cipherDataBlock = CipherDataBlock.empty()
        assertEquals(cipherDataBlock.alphabet, UppercaseLetters)
        assertEquals(cipherDataBlock.length, 0)
    }

    test("CipherDataBlock should allow adding elements and accessing them by index") {
        val cipherDataBlock = new CipherDataBlock[Char](Seq('A', 'B', 'C'), UppercaseLetters)
        assertEquals(cipherDataBlock(0), 'A')
        assertEquals(cipherDataBlock(1), 'B')
        assertEquals(cipherDataBlock(2), 'C')
    }

    test("CipherDataBlock should return the correct length") {
        val cipherDataBlock = new CipherDataBlock[Char](Seq('A', 'B', 'C'), UppercaseLetters)
        assertEquals(cipherDataBlock.length, 3)
    }

    test("CipherDataBlock iterator should iterate over all elements") {
        val cipherDataBlock = new CipherDataBlock[Char](Seq('A', 'B', 'C'), UppercaseLetters)
        val iterator = cipherDataBlock.iterator
        assertEquals(iterator.next(), 'A')
        assertEquals(iterator.next(), 'B')
        assertEquals(iterator.next(), 'C')
        assert(!iterator.hasNext)
    }

    test("CipherDataBlock padTo should add elements to the end of the data") {
        val cipherDataBlock = new CipherDataBlock[Char](Seq('A', 'B', 'C'), UppercaseLetters)
        cipherDataBlock.padTo(5, 'D')
        assertEquals(cipherDataBlock(3), 'D')
        assertEquals(cipherDataBlock(4), 'D')
    }
}
