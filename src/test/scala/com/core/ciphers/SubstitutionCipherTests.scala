import munit.FunSuite

import com.core.ciphers.SubstitutionCipher
import com.core.cipherdata._
import com.core.collections.BiMap
import com.core.alphabets.UppercaseLetters

class SubstitutionCipherTest extends FunSuite {

    test("SubstitutionCipher encrypts correctly with given key") {
        val data = new CipherDataBlock("HELLO", UppercaseLetters)
        val key = new BiMap[Char, Char](
            'H' -> 'K',
            'E' -> 'H',
            'L' -> 'O',
            'O' -> 'R'
        )
        val result = SubstitutionCipher.encrypt(data, key)
        assertEquals(result.outData.mkString, "KHOOR")
    }

    test("SubstitutionCipher decrypts correctly with given key") {
        val data = new CipherDataBlock("KHOOR", UppercaseLetters)
        val key = new BiMap[Char, Char](
            'H' -> 'K',
            'E' -> 'H',
            'L' -> 'O',
            'O' -> 'R'
        )
        val result = SubstitutionCipher.decrypt(data, key)
        assertEquals(result.outData.mkString, "HELLO")
    }

    test("SubstitutionCipher handles empty input") {
        val data = new CipherDataBlock("", UppercaseLetters)
        val key = new BiMap[Char, Char]()
        val encryptedResult = SubstitutionCipher.encrypt(data, key)
        val decryptedResult = SubstitutionCipher.decrypt(data, key)
        assertEquals(encryptedResult.outData.mkString, "")
        assertEquals(decryptedResult.outData.mkString, "")
    }

    test("SubstitutionCipher handles non-matching characters") {
        val data = new CipherDataBlock("HELLO", UppercaseLetters)
        val key = new BiMap[Char, Char](
            'H' -> 'K',
            'E' -> 'H'
            // 'L' and 'O' are not in the key
        )
        val result = SubstitutionCipher.encrypt(data, key)
        assertEquals(result.outData.mkString, "KHLLO") // 'L' and 'O' remain unchanged
    }

    test("SubstitutionCipher decrypts correctly with non-matching characters") {
        val data = new CipherDataBlock("KHOOR", UppercaseLetters)
        val key = new BiMap[Char, Char](
            'H' -> 'K',
            'E' -> 'H'
            // 'L' and 'O' are not in the key
        )
        val result = SubstitutionCipher.decrypt(data, key)
        assertEquals(result.outData.mkString, "HEOOR") // 'O' and 'R' remain unchanged
    }
}