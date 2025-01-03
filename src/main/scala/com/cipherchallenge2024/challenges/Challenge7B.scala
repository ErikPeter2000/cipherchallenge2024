package com.cipherchallenge2024.challenges

import com.core.cipherbreakers.VigenereCipherBreaker
import com.core.cipherdata.CipherDataBlock
import com.core.cipherdata.CipherFormatResult
import com.core.cipherbreakers.PolybiusCipherBreaker

object Challenge7B extends ChallengeSolution {
    def decrypt(ciphertext: String): String = {
        val formatted = ciphertext.filter(x => x.isLetter || x.isDigit).toUpperCase
        val firstHalf = formatted.substring(0, formatted.length / 2).map(x => x.toChar.toInt - 65)
        val secondHalf = formatted.substring(formatted.length / 2).map(x => x.toString.toInt - 1)
        val zipped = firstHalf.zip(secondHalf).map(x => (x._1.toInt - 65, x._2.toInt - 65))
        val zippedToString = zipped.map(x => x._1.toString + x._2.toString).mkString
        val broken = PolybiusCipherBreaker.break(zippedToString)
        broken.textData
    }
}
