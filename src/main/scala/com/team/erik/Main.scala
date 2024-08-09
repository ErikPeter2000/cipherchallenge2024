package com.team.erik

import scala.io.Source
import java.nio.file.Paths

import com.core._
import com.core.alphabets.UppercaseLetters
import com.core.ciphers.SubstitutionCipher
import com.core.cipherdata.CipherDataBlock
import com.core.keys.KeyFactory

object Main {
    def main(args: Array[String]): Unit = {
        val orwellPath = Paths.get("resources\\data\\texts\\Orwell1984.txt").toAbsolutePath.toString

        val plaintext = Source.fromFile(orwellPath, "UTF-8").getLines().take(50).mkString("\n").toUpperCase().replaceAll("[^A-Z]", "")
        val plaintextBlock = new CipherDataBlock(plaintext, UppercaseLetters)
        println(plaintext)
        println()

        val key = KeyFactory.createRandomSubstitutionKey(UppercaseLetters)
        val result = SubstitutionCipher.encrypt(plaintextBlock, key)

        println(result.outData.mkString)
        println()

        val ciphertextBlock = new CipherDataBlock(result.outData, UppercaseLetters)
        val decryptedResult = SubstitutionCipher.decrypt(ciphertextBlock, key)

        println(decryptedResult.outData.mkString)
    }
}
