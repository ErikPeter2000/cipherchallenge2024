package com.core.ciphers

import com.core.cipherdata.CipherDataBlock
import com.core.alphabets.UppercaseLetters
import com.core.alphabets.BiMapAlphabet
import com.core.keys.KeyFactory
import com.core.alphabets.PosIntAlphabet

class PolybiusCipherTests extends munit.FunSuite {
    test("PolybiusCipher.encrypt") {
        val data = CipherDataBlock.create("BYGEORGEORWELLPARTONEITWASABRIGHTCOLDDAYINAPRILANDTHECLOCKSWERESTRIKINGTH", UppercaseLetters)
        val expected =
            "15142413354324133543531332324111434535341331455311441115433124254521353222221114313411414331321134224525132132352112445313431344454331123134244525" // The expected is from DCode.fr, which uses 1-based indexing, so we add 1 to each value after decryption
        val key = KeyFactory.combinePhraseWithAlphabet("AKEY", UppercaseLetters.dropLetter('J'))
        val result = PolybiusCipher.encrypt(data, key, 5).map(x => (x + 1).toString).mkString
        assertEquals(result, expected)
    }

    test("PolybiusCipher.decrypt") {
        val data = new CipherDataBlock(
            "15142413354324133543531332324111434535341331455311441115433124254521353222221114313411414331321134224525132132352112445313431344454331123134244525"
                .grouped(1).map(_.toInt - 1)
                .toSeq,
            PosIntAlphabet
        )
        val expected = "BYGEORGEORWELLPARTONEITWASABRIGHTCOLDDAYINAPRILANDTHECLOCKSWERESTRIKINGTH"
        val key = KeyFactory.combinePhraseWithAlphabet("AKEY", UppercaseLetters.dropLetter('J'))
        val result = PolybiusCipher.decrypt(data, key, 5).mkString
        assertEquals(result, expected)
    }
}
