package com.core.cipherbreakers

import com.core.extensions.StringExtensions.relativeMatch

class SubstitutionCipherBreakerTests extends munit.FunSuite {
    test("SubstitutionCipherBreaker.break") {
        val ciphertext =
            "WNUTGKUTGKVTSSHQKZGFTOZVQLQWKOUIZEGSRRQNOFQHKOSQFRZITESGEALVTKTLZKOAOFUZIOKZTTFVOFLZGFLDOZIIOLEIOFFXMMSTROFZGIOLWKTQLZOFQFTYYGKZZGTLEQHTZITCOSTVOFRLSOHHTRJXOEASNZIKGXUIZITUSQLLRGGKLGYCOEZGKNDQFLOGFLZIGXUIF"
        val key = "QWERTYUIOPASDFGHJKLZXCVBNM"

        val result = MonoAlphabeticSubstitutionCipherBreaker.break(ciphertext)
        val matchFactor = result.key.values.mkString.relativeMatch(key)
        assert(matchFactor > 0.9, s"Got: $matchFactor")
    }
}
