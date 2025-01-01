package com.core.analysers

import com.core.cipherdata.CipherDataBlock

class IndexOfCoincidenceTests extends munit.FunSuite {
    test("IndexOfCoincidence.calculate") {
        val text = """To be, or not to be, that is the question-
Whether 'tis Nobler in the mind to suffer
The Slings and Arrows of outrageous Fortune,
Or to take Arms against a Sea of troubles,
And by opposing end them?
""".toUpperCase().replaceAll("[^A-Z]", "")
        val data = CipherDataBlock.create(text)
        val result = IndexOfCoincidence.calculate(data)
        assertEqualsDouble(result, 0.070, 0.001)
    }
}
