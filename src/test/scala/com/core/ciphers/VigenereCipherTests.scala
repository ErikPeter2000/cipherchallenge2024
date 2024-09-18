package com.core.ciphers

import com.core.cipherdata.CipherDataBlock

class VigenereCipherTests extends munit.FunSuite {
    test("VigenereCipher encrypt") {
        val plaintext = "BYGEORGEORWELLPARTONEITWASABRIGHTCOLDDAYINAPRILANDTHECLOCKSWERESTRIKINGTHIRTEENWINSTONSMITHHISC"
        val key = "KEY"
        val expected = "LCEOSPQIMBACVPNKVRYRCSXUKWYLVGQLRMSJNHYIMLKTPSPYXHRRIAVSAUWUOVCCXPSOGXKRRMPDICXAGXWRYRQWMRRLGCG"

        val data = CipherDataBlock.create(plaintext)
        val result = VigenereCipher.encrypt(data, key).mkString

        assertEquals(result, expected)
    }
    test("VigenereCipher decrypt") {
        val ciphertext = "LCEOSPQIMBACVPNKVRYRCSXUKWYLVGQLRMSJNHYIMLKTPSPYXHRRIAVSAUWUOVCCXPSOGXKRRMPDICXAGXWRYRQWMRRLGCG"
        val key = "KEY"
        val expected = "BYGEORGEORWELLPARTONEITWASABRIGHTCOLDDAYINAPRILANDTHECLOCKSWERESTRIKINGTHIRTEENWINSTONSMITHHISC"

        val data = CipherDataBlock.create(ciphertext)
        val result = VigenereCipher.decrypt(data, key).mkString

        assertEquals(result, expected)
    }
}
