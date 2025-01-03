package com.cipherchallenge2024.challenges

import com.core.cipherdata.CipherDataBlock
import com.core.cipherbreakers.ColumnCipherBreaker

object ChallengeColumn extends ChallengeSolution {
    def decrypt(ciphertext: String): String = {
        val formatResult = CipherDataBlock.formatAndCreate(ciphertext)
        val broken = ColumnCipherBreaker.break(formatResult._1)
        return broken.textData
    }
}
