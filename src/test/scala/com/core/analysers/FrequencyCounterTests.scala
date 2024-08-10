package com.core.analysers

import com.core.cipherdata.CipherDataBlock

class FrequencyCounterTests extends munit.FunSuite {
    test("FrequencyCounter.calculate") {
        val data = CipherDataBlock.createFrom("ABCDABCDABCDABCD")
        val keys = Set("AB".toIterable, "CD".toIterable)
        val result = FrequencyCounter.calculate(data, keys)
        val resultData = result.map { case (k, v) => (k.mkString, v) }
        assertEquals(resultData("AB"), 4)
        assertEquals(resultData("CD"), 4)
    }  
}
