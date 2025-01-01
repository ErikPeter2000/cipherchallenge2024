package com.core.cipherkeys

import com.core.alphabets.LowercaseLetters

class KeyFactoryTests extends munit.FunSuite {

    test("Create substitution key creates valid key") {
        val phrase = "hello"
        val expected = "heloabcdfgijkmnpqrstuvwxyz"
        val inputAlphabet = LowercaseLetters
        val key = com.core.cipherkeys.KeyFactory.createSubstitutionKey(phrase, inputAlphabet)
        assertEquals(key.size, inputAlphabet.size)
        assertEquals(key('a'), 'h')
        assertEquals(key('b'), 'e')
        assertEquals(key('c'), 'l')
        assertEquals(key('d'), 'o')
        assertEquals(key('e'), 'a')
    }

    test("Create random substitution key creates valid key") {
        val inputAlphabet = LowercaseLetters
        val key = com.core.cipherkeys.KeyFactory.createRandomSubstitutionKey(inputAlphabet)
        assertEquals(key.size, inputAlphabet.size)
    }

    test("Create transposition key creates valid key") {
        val phrase = "hello"
        val expected = IndexedSeq(1, 0, 2, 3, 4)
        val key = com.core.cipherkeys.KeyFactory.createTranspositionKey(phrase, LowercaseLetters)
        assertEquals(key, expected)
    }

    test("Create longer transposition key creates valid key") {
        val phrase = "hello"
        val expected = IndexedSeq(1, 0, 2, 3, 4, 6, 5, 7, 8, 9, 11)
        val key = com.core.cipherkeys.KeyFactory.createTranspositionKey(phrase, LowercaseLetters, 11)
        assertEquals(key, expected)
    }
}
