package com.cipherchallenge2024.challenges

import com.core.cipherbreakers.MonoAlphabeticSubstitutionCipherBreaker
import com.core.cipherdata.CipherDataBlock
import com.core.cipherdata.CipherFormatResult

object Challenge0B extends ChallengeSolution {
    def decrypt(ciphertext: String): String = {
        val formatResult = CipherDataBlock.formatAndCreate(ciphertext)
        val broken = MonoAlphabeticSubstitutionCipherBreaker.break(formatResult._1)
        val recombined = formatResult._2.revertFormat(broken.outData)
        return recombined.mkString
    }
}
