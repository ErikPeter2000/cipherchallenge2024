package com.core.alphabets

// test file for the Alphabets classes
class AlphabetTests extends munit.FunSuite {

    test("UppercaseAlphabet should have correct mappings") {
        assertEquals(UppercaseLetters.size, 26)
        assertEquals(UppercaseLetters.get(0), Some('A'))
        assertEquals(UppercaseLetters.get(25), Some('Z'))
        assertEquals(UppercaseLetters.getReverse('A'), Some(0))
        assertEquals(UppercaseLetters.getReverse('Z'), Some(25))
    }

    test("MorseAlphabet should have correct mappings") {
        assertEquals(MorseAlphabet.size, 36)
        assertEquals(MorseAlphabet.get(0), Some(".-"))
        assertEquals(MorseAlphabet.get(25), Some("--.."))
        assertEquals(MorseAlphabet.getReverse(".-"), Some(0))
        assertEquals(MorseAlphabet.getReverse("--.."), Some(25))
    }
    
    test("CreateLetterMapAgainst should map correctly") {
        val map = UppercaseLetters.createLetterMapAgainst(LowercaseLetters)
        assertEquals(map.size, 26)
        assertEquals(map.get('A'), Some('a'))
        assertEquals(map.get('Z'), Some('z'))
        assertEquals(map.getReverse('a'), Some('A'))
        assertEquals(map.getReverse('z'), Some('Z'))
    }

    test("CreateLetterMapAgainst should map correctly with Seq") {
        val thisAlphabet = new BaseAlphabet[Char](IndexedSeq('A', 'B', 'C', 'D', 'E'))
        val map = thisAlphabet.createLetterMapAgainst(Seq('a', 'b', 'c', 'd', 'e'))
        assertEquals(map.size, 5)
        assertEquals(map.get('A'), Some('a'))
        assertEquals(map.get('E'), Some('e'))
        assertEquals(map.getReverse('a'), Some('A'))
        assertEquals(map.getReverse('e'), Some('E'))
    }
}