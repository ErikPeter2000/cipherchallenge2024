package com.core.ciphers

import com.core.cipherdata.CipherDataBlock
import com.core.alphabets.UppercaseLetters

class VigenereCipherTests extends munit.FunSuite {
    test("VigenereCipher encrypt") {
        val plaintext = "BYGEORGEORWELLPARTONEITWASABRIGHTCOLDDAYINAPRILANDTHECLOCKSWERESTRIKINGTHIRTEENWINSTONSMITHHISC"
        val key = "KEY"
        val expected = "LCEOSPQIMBACVPNKVRYRCSXUKWYLVGQLRMSJNHYIMLKTPSPYXHRRIAVSAUWUOVCCXPSOGXKRRMPDICXAGXWRYRQWMRRLGCG"

        val data = new CipherDataBlock(plaintext, UppercaseLetters)
        val result = VigenereCipher.encrypt(data, key).outData.mkString

        assertEquals(result, expected)
    }
    test("VigenereCipher decrypt") {
        val ciphertext = "LCEOSPQIMBACVPNKVRYRCSXUKWYLVGQLRMSJNHYIMLKTPSPYXHRRIAVSAUWUOVCCXPSOGXKRRMPDICXAGXWRYRQWMRRLGCG"
        val key = "KEY"
        val expected = "BYGEORGEORWELLPARTONEITWASABRIGHTCOLDDAYINAPRILANDTHECLOCKSWERESTRIKINGTHIRTEENWINSTONSMITHHISC"

        val data = new CipherDataBlock(ciphertext, UppercaseLetters)
        val result = VigenereCipher.decrypt(data, key).outData.mkString

        assertEquals(result, expected)
    }
}
