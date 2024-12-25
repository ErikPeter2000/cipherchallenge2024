package com.core.collections

class BiMapTest extends munit.FunSuite {

    test("BiMap should have correct mappings") {
        val biMap = new BiMap[Int, String](
            0 -> "A",
            1 -> "B",
            2 -> "Y",
            3 -> "Z"
        )
        assertEquals(biMap.size, 4)
        assertEquals(biMap.get(0), Some("A"))
        assertEquals(biMap.get(3), Some("Z"))
        assertEquals(biMap.getReverse("A"), Some(0))
        assertEquals(biMap.getReverse("Z"), Some(3))
    }

    test("Bimap concat should behave correctly") {
        val biMap1 = new BiMap[Int, String](
            0 -> "A",
            1 -> "B",
            24 -> "Y",
            25 -> "Z"
        )
        val biMap2 = new BiMap[Int, String](
            2 -> "C",
            3 -> "D",
            22 -> "W",
            23 -> "X"
        )
        biMap1 += (2 -> "C")
        assertEquals(biMap1.size, 5)
        assertEquals(biMap1.get(2), Some("C"))

        biMap1 ++= biMap2
        assertEquals(biMap1.size, 8)
        assertEquals(biMap1.get(3), Some("D"))
        assertEquals(biMap1.get(22), Some("W"))
    }
}
