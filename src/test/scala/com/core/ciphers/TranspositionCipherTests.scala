package com.core.ciphers

import com.core.ciphers.TranspositionCipher
import com.core.cipherdata._
import com.core.alphabets.UppercaseLetters

class TranspositionCipherTest extends munit.FunSuite {

    test("TranspositionCipher.encrypt") {
        val data = CipherDataBlock.create("BYGEORGEORWELLPARTONEITWASABRIGHTCOLDDAYINAPRILANDTHECLOCKSWERESTRIKINGTHIR", UppercaseLetters)
        val expected = "GBYOEERGROLWEPLTARNOTEIAWBSAIRTGHOCDLDYAAINRPAILDNETHLCKOCWSEERTSKRINIHGTRI"
        val key = IndexedSeq(2, 0, 1, 4, 3)
        val result = TranspositionCipher.encrypt(data, key)
        assertEquals(result.mkString, expected)
    }

    test("TranspositionCipher.decrypt") {
        val data = CipherDataBlock.create("GBYOEERGROLWEPLTARNOTEIAWBSAIRTGHOCDLDYAAINRPAILDNETHLCKOCWSEERTSKRINIHGTRI", UppercaseLetters)
        val expected = "BYGEORGEORWELLPARTONEITWASABRIGHTCOLDDAYINAPRILANDTHECLOCKSWERESTRIKINGTHIR"
        val key = IndexedSeq(2, 0, 1, 4, 3)
        val result = TranspositionCipher.decrypt(data, key)
        assertEquals(result.mkString, expected)
    }
}