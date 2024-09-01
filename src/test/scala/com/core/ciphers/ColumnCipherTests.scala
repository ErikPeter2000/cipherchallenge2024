package com.core.ciphers

import com.core.cipherdata.CipherDataBlock
import com.core.alphabets.UppercaseLetters

class ColumnCipherTests extends munit.FunSuite {
    test("ColumnCipher encrypt") {
        val plaintext =
            "BYGEORGEORWELLPARTONEITWASABRIGHTCOLDDAYINAPRILANDTHECLOCKSWERESTRIKINGTHIRTEENWINSTONSMITHHISC"
        val key = IndexedSeq(2, 0, 1, 4, 3)
        val expected = "GELTTBTDAAEKEKHESMIBRWAESGLIITOERGTINHYGERIAHDNLHCRITENSHORPNAIOYRDLWTNRWOTCEOLOWRCAPNCSSIINTIS"

        val data = new CipherDataBlock(plaintext, UppercaseLetters)
        val result = ColumnCipher.encrypt(data, key).mkString

        assertEquals(result, expected)
    }
    test("ColumnCipher decrypt") {
        val ciphertext =
            "GELTTBTDAAEKEKHESMIBRWAESGLIITOERGTINHYGERIAHDNLHCRITENSHORPNAIOYRDLWTNRWOTCEOLOWRCAPNCSSIINTIS"
        val key = IndexedSeq(2, 0, 1, 4, 3)
        val expected = "BYGEORGEORWELLPARTONEITWASABRIGHTCOLDDAYINAPRILANDTHECLOCKSWERESTRIKINGTHIRTEENWINSTONSMITHHISC"

        val data = new CipherDataBlock(ciphertext, UppercaseLetters)
        val result = ColumnCipher.decrypt(data, key).mkString

        assertEquals(result, expected)
    }
}
