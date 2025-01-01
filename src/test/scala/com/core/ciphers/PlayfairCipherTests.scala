package com.core.ciphers

import com.core.cipherdata.CipherDataBlock
import com.core.cipherkeys.KeyFactory
import com.core.alphabets.UppercaseLettersNoJ

class PlayfairCipherTests extends munit.FunSuite {
    test("PlayfairCipher.encrypt") {
        val data = CipherDataBlock.create("BYGEORGEORWELLPARTONEITWASABRIGHTCOLDDAYINAPRILAND", UppercaseLettersNoJ)
        val key = KeyFactory.combinePhraseWithAlphabet("KEYWORD", UppercaseLettersNoJ)
        val encrypted = PlayfairCipher.encrypt(data, key)
        assertEquals(encrypted.mkString, "AWNDKCNDKCOYIZHSBDZKUDFXYBPCCDLHFVLCGCABWHPDMALFDPBU")
    }
    test("PlayfairCipher.decrypt") {
        val data = CipherDataBlock.create("AWNDKCNDKCOYIZHSBDZKUDFXYBPCCDLHFVLCGCABWHPDMALFDPBU", UppercaseLettersNoJ)
        val key = KeyFactory.combinePhraseWithAlphabet("KEYWORD", UppercaseLettersNoJ)
        val decrypted = PlayfairCipher.decrypt(data, key)
        assertEquals(decrypted.mkString, "BYGEORGEORWELXLPARTONEITWASABRIGHTCOLDDAYINAPRILANDX")
    }
    test("PlayfairCipher.encrypt pads bigrams correctly") {
        val data = CipherDataBlock.create("CAABCAABXXXXAAAAAAXXAXQQ")
        val key = KeyFactory.combinePhraseWithAlphabet("", UppercaseLettersNoJ)
        val encrypted = PlayfairCipher.encrypt(data, key)
        assertEquals(encrypted.mkString, "DBBCDBBCVSVSVSVCCVCVCVCVCVVCVSSV")
    }
}
