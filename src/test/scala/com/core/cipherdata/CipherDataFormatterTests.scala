package com.core.cipherdata

class CipherDataFormatterTests extends munit.FunSuite {
    test("CipherDataBlock.formatAndCreate should format the data correctly") {
        val original =
            "We stood by a pond that winter day, And the sun was white, as though chidden of God, And a few leaves lay on the starving sod; - They had fallen from an ash, and were gray."
        val formattedString =
            "WESTOODBYAPONDTHATWINTERDAYANDTHESUNWASWHITEASTHOUGHCHIDDENOFGODANDAFEWLEAVESLAYONTHESTARVINGSODTHEYHADFALLENFROMANASHANDWEREGRAY"

        val creation = CipherDataBlock.formatAndCreate(original)
        val formattedData = creation._1
        val formatResult = creation._2
        val reinserted = formatResult.reinsertElements(formattedData)

        assertEquals(formattedData.mkString, formattedString)        
        assertEquals(reinserted.mkString, original)
    }
}
