package com.core.ciphers

import munit.FunSuite

import com.core.ciphers.MonoAlphabeticSubstitutionCipher
import com.core.cipherdata._
import com.core.collections.BiMap

class SubstitutionCipherTest extends FunSuite {

    test("SubstitutionCipher encrypts correctly with given key") {
        val data = CipherDataBlock.create("HELLO")
        val key = new BiMap[Char, Char](
            'H' -> 'K',
            'E' -> 'H',
            'L' -> 'O',
            'O' -> 'R'
        )
        val result = MonoAlphabeticSubstitutionCipher.encrypt(data, key)
        assertEquals(result.mkString, "KHOOR")
    }

    test("SubstitutionCipher decrypts correctly with given key") {
        val data = CipherDataBlock.create("KHOOR")
        val key = new BiMap[Char, Char](
            'H' -> 'K',
            'E' -> 'H',
            'L' -> 'O',
            'O' -> 'R'
        )
        val result = MonoAlphabeticSubstitutionCipher.decrypt(data, key)
        assertEquals(result.mkString, "HELLO")
    }

    test("SubstitutionCipher handles empty input") {
        val data = CipherDataBlock.create("")
        val key = new BiMap[Char, Char]()
        val encryptedResult = MonoAlphabeticSubstitutionCipher.encrypt(data, key)
        val decryptedResult = MonoAlphabeticSubstitutionCipher.decrypt(data, key)
        assertEquals(encryptedResult.mkString, "")
        assertEquals(decryptedResult.mkString, "")
    }

    test("SubstitutionCipher handles non-matching characters") {
        val data = CipherDataBlock.create("HELLO")
        val key = new BiMap[Char, Char](
            'H' -> 'K',
            'E' -> 'H'
            // 'L' and 'O' are not in the key
        )
        val result = MonoAlphabeticSubstitutionCipher.encrypt(data, key)
        assertEquals(result.mkString, "KHLLO") // 'L' and 'O' remain unchanged
    }

    test("SubstitutionCipher decrypts correctly with non-matching characters") {
        val data = CipherDataBlock.create("KHOOR")
        val key = new BiMap[Char, Char](
            'H' -> 'K',
            'E' -> 'H'
            // 'L' and 'O' are not in the key
        )
        val result = MonoAlphabeticSubstitutionCipher.decrypt(data, key)
        assertEquals(result.mkString, "HEOOR") // 'O' and 'R' remain unchanged
    }
}