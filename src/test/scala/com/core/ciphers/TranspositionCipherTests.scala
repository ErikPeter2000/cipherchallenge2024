package com.core.ciphers

import com.core.cipherdata.CipherDataBlock
import com.core.ciphers.PermutationCipher

class TranspositionCipherTest extends munit.FunSuite {

    test("TranspositionCipher.encrypt") {
        val data = CipherDataBlock.create("BYGEORGEORWELLPARTONEITWASABRIGHTCOLDDAYINAPRILANDTHECLOCKSWERESTRIKINGTHIR")
        val expected = "GBYOEERGROLWEPLTARNOTEIAWBSAIRTGHOCDLDYAAINRPAILDNETHLCKOCWSEERTSKRINIHGTRI"
        val key = IndexedSeq(2, 0, 1, 4, 3)
        val result = PermutationCipher.encrypt(data, key)
        assertEquals(result.mkString, expected)
    }

    test("TranspositionCipher.decrypt") {
        val data = CipherDataBlock.create("GBYOEERGROLWEPLTARNOTEIAWBSAIRTGHOCDLDYAAINRPAILDNETHLCKOCWSEERTSKRINIHGTRI")
        val expected = "BYGEORGEORWELLPARTONEITWASABRIGHTCOLDDAYINAPRILANDTHECLOCKSWERESTRIKINGTHIR"
        val key = IndexedSeq(2, 0, 1, 4, 3)
        val result = PermutationCipher.decrypt(data, key)
        assertEquals(result.mkString, expected)
    }
}
