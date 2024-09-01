package com.core.ciphers

import com.core.ciphers.TranspositionCipher
import com.core.cipherdata._
import com.core.alphabets.UppercaseLetters

class TranspositionCipherTest extends munit.FunSuite {

    test("TranspositionCipher encrypts correctly with given key") {
        val data = new CipherDataBlock("HELLO", UppercaseLetters)
        val key = IndexedSeq(2, 0, 1, 4, 3)
        val result = TranspositionCipher.encrypt(data, key)
        assertEquals(result.mkString, "LHEOL")
    }

    test("TranspositionCipher decrypts correctly with given key") {
        val data = new CipherDataBlock("LHEOL", UppercaseLetters)
        val key = IndexedSeq(2, 0, 1, 4, 3)
        val result = TranspositionCipher.decrypt(data, key)
        assertEquals(result.mkString, "HELLO")
    }

    test("TranspositionCipher handles empty input") {
        val data = new CipherDataBlock("", UppercaseLetters)
        val key = IndexedSeq(2, 0, 1)
        val encryptedResult = TranspositionCipher.encrypt(data, key)
        val decryptedResult = TranspositionCipher.decrypt(data, key)
        assertEquals(encryptedResult.mkString, "")
        assertEquals(decryptedResult.mkString, "")
    }

    test("TranspositionCipher handles non-matching key size") {
        val data = new CipherDataBlock("ABCDE", UppercaseLetters)
        val key = IndexedSeq(2, 1, 0)
        val result = TranspositionCipher.encrypt(data, key)
        assertEquals(result.mkString, "CBAED")
    }

    test("TranspositionCipher decrypts correctly with non-matching key size") {
        val data = new CipherDataBlock("CBAED", UppercaseLetters)
        val key = IndexedSeq(2, 1, 0)
        val result = TranspositionCipher.decrypt(data, key)
        assertEquals(result.mkString, "ABCDE")
    }
}