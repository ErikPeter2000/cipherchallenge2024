package com.team.erik

import scala.io.Source
import java.nio.file.Paths

import com.core._
import com.core.alphabets.UppercaseLetters
import com.core.ciphers.SubstitutionCipher
import com.core.cipherdata.CipherDataBlock
import com.core.keys.KeyFactory
import com.core.alphabets.LowercaseLetters
import com.core.ciphers.ColumnCipher

object Main {
    def main(args: Array[String]): Unit = {
        val path = Paths.get(".\\resources\\data\\texts\\Orwell1984.txt")
        val text = Source.fromFile(path.toFile).getLines.take(100).mkString.toUpperCase.replaceAll("[^A-Z]", "")
        println(text)

        val key = KeyFactory.createTranspositionKey("hello", LowercaseLetters)
        println(key)
        val block = new CipherDataBlock(text, UppercaseLetters)
        val result = ColumnCipher.encrypt(block, key)

        println(result.outData.mkString)
    }
}
