package com.cipherchallenge2024.challenges

import com.core.cipherbreakers.CaesarCipherBreaker
import com.core.cipherdata.CipherDataBlock
import com.core.cipherdata.CipherFormatResult

object Challenge0A extends ChallengeSolution {
    def decrypt(ciphertext: String): String = {
        val formatResult = CipherDataBlock.formatAndCreate(ciphertext)
        val broken = CaesarCipherBreaker.break(formatResult._1)
        val recombined = formatResult._2.revertFormat(broken.outData)
        return recombined.mkString
    }
}
