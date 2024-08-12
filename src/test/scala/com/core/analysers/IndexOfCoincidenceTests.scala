package com.core.analysers

import com.core.cipherdata.CipherDataBlock
import com.core.alphabets.BaseAlphabet
import com.core.alphabets.UppercaseLetters

class IndexOfCoincidenceTests extends munit.FunSuite {
    test("IndexOfCoincidence.calculate") {
        val text = """
        To be, or not to be, that is the question-
Whether 'tis Nobler in the mind to suffer
The Slings and Arrows of outrageous Fortune,
Or to take Arms against a Sea of troubles,
And by opposing end them?
William Shakespeare - Hamlet
""".toUpperCase().replaceAll("[^A-Z]", "")
        val data = new CipherDataBlock(text, UppercaseLetters)
        val result = IndexOfCoincidence.calculate(data)
        assertEqualsDouble(result, 0.067, 0.001)
    }
}
