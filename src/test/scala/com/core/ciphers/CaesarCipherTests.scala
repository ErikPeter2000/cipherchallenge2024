package com.core.ciphers

import com.core.cipherdata._
import com.core.alphabets.UppercaseLetters

class CaesarCipherTest extends munit.FunSuite {

    test("CaesarCipher encrypts correctly with key 3") {
        val data = new CipherDataBlock("HELLO", UppercaseLetters)
        val key = 3
        val result = CaesarCipher.encrypt(data, key)
        assert(result.mkString == "KHOOR")
    }

    test("CaesarCipher decrypts correctly with key 3") {
        val data = new CipherDataBlock("KHOOR", UppercaseLetters)
        val key = 3
        val result = CaesarCipher.decrypt(data, key)
        assert(result.mkString == "HELLO")
    }

    test("CaesarCipher handles empty input") {
        val data = new CipherDataBlock("", UppercaseLetters)
        val key = 3
        val encryptedResult = CaesarCipher.encrypt(data, key)
        val decryptedResult = CaesarCipher.decrypt(data, key)
        assert(encryptedResult.mkString == "")
        assert(decryptedResult.mkString == "")
    }

    test("CaesarCipher handles key wrap around") {
        val data = new CipherDataBlock("XYZ", UppercaseLetters)
        val key = 3
        val result = CaesarCipher.encrypt(data, key)
        assert(result.mkString == "ABC")
    }

    test("CaesarCipher decrypts correctly with key wrap around") {
        val data = new CipherDataBlock("ABC", UppercaseLetters)
        val key = 3
        val result = CaesarCipher.decrypt(data, key)
        assert(result.mkString == "XYZ")
    }
}
