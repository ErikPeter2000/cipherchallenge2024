package com.cipherchallenge2024.challenges

import com.core.cipherbreakers.VigenereCipherBreaker
import com.core.cipherdata.CipherDataBlock
import com.core.cipherdata.CipherFormatResult

object Challenge8A extends ChallengeSolution {
    def decrypt(ciphertext: String): String = {
        val formatResult = CipherDataBlock.formatAndCreate(ciphertext)
        val broken = VigenereCipherBreaker.break(formatResult._1.reverse.mkString)
        return broken.textData
    }
}
