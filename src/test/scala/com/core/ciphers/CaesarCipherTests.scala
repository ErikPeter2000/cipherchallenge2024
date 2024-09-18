package com.core.ciphers

import com.core.cipherdata._

class CaesarCipherTest extends munit.FunSuite {

    test("CaesarCipher encrypts correctly with key 3") {
        val data = CipherDataBlock.create("HELLO")
        val key = 3
        val result = CaesarCipher.encrypt(data, key)
        assert(result.mkString == "KHOOR")
    }

    test("CaesarCipher decrypts correctly with key 3") {
        val data = CipherDataBlock.create("KHOOR")
        val key = 3
        val result = CaesarCipher.decrypt(data, key)
        assert(result.mkString == "HELLO")
    }

    test("CaesarCipher handles empty input") {
        val data = CipherDataBlock.create("")
        val key = 3
        val encryptedResult = CaesarCipher.encrypt(data, key)
        val decryptedResult = CaesarCipher.decrypt(data, key)
        assert(encryptedResult.mkString == "")
        assert(decryptedResult.mkString == "")
    }

    test("CaesarCipher handles key wrap around") {
        val data = CipherDataBlock.create("XYZ")
        val key = 3
        val result = CaesarCipher.encrypt(data, key)
        assert(result.mkString == "ABC")
    }

    test("CaesarCipher decrypts correctly with key wrap around") {
        val data = CipherDataBlock.create("ABC")
        val key = 3
        val result = CaesarCipher.decrypt(data, key)
        assert(result.mkString == "XYZ")
    }
}
