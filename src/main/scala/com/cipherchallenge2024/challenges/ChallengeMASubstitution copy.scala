package com.cipherchallenge2024.challenges

import com.core.cipherbreakers.MonoAlphabeticSubstitutionCipherBreaker
import com.core.cipherdata.CipherDataBlock

object ChallengeMASubstitution extends ChallengeSolution {
    def decrypt(ciphertext: String): String = {
        val formatResult = CipherDataBlock.formatAndCreate(ciphertext)
        val broken = MonoAlphabeticSubstitutionCipherBreaker.break(formatResult._1)
        return broken.textData
    }
}
