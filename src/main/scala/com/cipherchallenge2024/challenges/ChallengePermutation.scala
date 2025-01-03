package com.cipherchallenge2024.challenges

import com.core.cipherbreakers.PermutationCipherBreaker
import com.core.cipherdata.CipherDataBlock

object ChallengePermutation extends ChallengeSolution {
    def decrypt(ciphertext: String): String = {
        val formatResult = CipherDataBlock.formatAndCreate(ciphertext)
        val broken = PermutationCipherBreaker.break(formatResult._1)
        return broken.textData
    }
}
