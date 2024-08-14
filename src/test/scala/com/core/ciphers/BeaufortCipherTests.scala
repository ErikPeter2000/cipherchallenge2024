package com.core.ciphers

import com.core.cipherdata.CipherDataBlock
import com.core.alphabets.UppercaseLetters

class BeaufortCipherTests extends munit.FunSuite {
    test("BeaufortCipher encrypt") {
        val plaintext = "BYGEORGEORWELLPARTONEITWASABRIGHTCOLDDAYINAPRILANDTHECLOCKSWERESTRIKINGTHIRTEENWINSTONSMITHHISC"
        val key = "KEY"
        val expected = "JGSGQHEAKTIUZTJKNFWRUCLCKMYJNQEXFIQNHBYMWLKPHCTYXBFDAWZQWAMCGNUSLHCUQXYFDWHRAUXIQXMFWRGYWFDXQSC"

        val data = new CipherDataBlock(plaintext, UppercaseLetters)
        val result = BeaufortCipher.encrypt(data, key).outData.mkString

        assertEquals(result, expected)
    }
    test("BeaufortCipher decrypt") {
        val ciphertext = "JGSGQHEAKTIUZTJKNFWRUCLCKMYJNQEXFIQNHBYMWLKPHCTYXBFDAWZQWAMCGNUSLHCUQXYFDWHRAUXIQXMFWRGYWFDXQSC"
        val key = "KEY"
        val expected = "BYGEORGEORWELLPARTONEITWASABRIGHTCOLDDAYINAPRILANDTHECLOCKSWERESTRIKINGTHIRTEENWINSTONSMITHHISC"

        val data = new CipherDataBlock(ciphertext, UppercaseLetters)
        val result = BeaufortCipher.decrypt(data, key).outData.mkString

        assertEquals(result, expected)
    }
}
