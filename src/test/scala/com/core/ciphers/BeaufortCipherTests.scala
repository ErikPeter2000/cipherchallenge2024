package com.core.ciphers

import com.core.cipherdata.CipherDataBlock

class BeaufortCipherTests extends munit.FunSuite {
    test("BeaufortCipher encrypt") {
        val plaintext =
            "BYGEORGEORWELLPARTONEITWASABRIGHTCOLDDAYINAPRILANDTHECLOCKSWERESTRIKINGTHIRTEENWINSTONSMITHHISC"
        val key = "KEY"
        val expected = "JGSGQHEAKTIUZTJKNFWRUCLCKMYJNQEXFIQNHBYMWLKPHCTYXBFDAWZQWAMCGNUSLHCUQXYFDWHRAUXIQXMFWRGYWFDXQSC"

        val data = CipherDataBlock.create(plaintext)
        val result = BeaufortCipher.encrypt(data, key).mkString

        assertEquals(result, expected)
    }
    test("BeaufortCipher decrypt") {
        val ciphertext =
            "JGSGQHEAKTIUZTJKNFWRUCLCKMYJNQEXFIQNHBYMWLKPHCTYXBFDAWZQWAMCGNUSLHCUQXYFDWHRAUXIQXMFWRGYWFDXQSC"
        val key = "KEY"
        val expected = "BYGEORGEORWELLPARTONEITWASABRIGHTCOLDDAYINAPRILANDTHECLOCKSWERESTRIKINGTHIRTEENWINSTONSMITHHISC"

        val data = CipherDataBlock.create(ciphertext)
        val result = BeaufortCipher.decrypt(data, key).mkString

        assertEquals(result, expected)
    }
}
