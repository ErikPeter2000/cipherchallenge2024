package com.core.cipherbreakers

class CaesarCipherBreakerTests extends munit.FunSuite {
    test("CaesarCipher.break") {
        val ciphertext = "EBJHRUJHRUZHOOSDUWRQHLWZDVDEULJKWFROGGDBLQDSULODQGWKHFORFNVZHUHVWULNLQJWKLUWHHQ"
        val key = 3

        val result = CaesarCipherBreaker.break(ciphertext)
        assertEquals(result.key, 3)
    }
}
