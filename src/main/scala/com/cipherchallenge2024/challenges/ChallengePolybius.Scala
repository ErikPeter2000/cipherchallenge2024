package com.cipherchallenge2024.challenges

import com.core.cipherbreakers.PolybiusCipherBreaker

object ChallengePolybius extends ChallengeSolution {
    def decrypt(ciphertext: String, saveToSubmissions: Boolean): String = {
        val formatted = ciphertext.map(c => c.toString.toInt - 1).mkString
        val broken = PolybiusCipherBreaker.break(formatted)
        return broken.textData
    }
}
