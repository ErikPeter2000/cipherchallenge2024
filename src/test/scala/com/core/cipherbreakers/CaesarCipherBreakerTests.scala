package com.core.cipherbreakers

import com.core.alphabets.BaseAlphabet

class CaesarCipherBreakerTests extends munit.FunSuite {
    test("CaesarCipher.break") {
        val ciphertext = "EBJHRUJHRUZHOOSDUWRQHLWZDVDEULJKWFROGGDBLQDSULODQGWKHFORFNVZHUHVWULNLQJWKLUWHHQ"
        val key = 3

        val result = CaesarCipherBreaker.break(ciphertext)
        assertEquals(result.key, 3)
    }

    test("CaesarCipher.getKey") {
        val ciphertext = "EBJHRUJHRUZHOOSDUWRQHLWZDVDEULJKWFROGGDBLQDSULODQGWKHFORFNVZHUHVWULNLQJWKLUWHHQ"
        val expectedKey = 3

        val givenKey = CaesarCipherBreaker.getKey(ciphertext, BaseAlphabet.default)._1
        assertEquals(givenKey, expectedKey)
    }
}
