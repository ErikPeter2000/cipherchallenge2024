package com.core.analysers

class FrequencyCounterTests extends munit.FunSuite {
    test("FrequencyCounter.calculate") {
        val data = "ABCDABCDABCDABCD"
        val keys = Set("AB", "CD")
        val result = FrequencyCounter.calculate(data, keys)
        val resultData = result.map { case (k, v) => (k.mkString, v) }
        assertEquals(resultData("AB"), 4)
        assertEquals(resultData("CD"), 4)
    }  
}
