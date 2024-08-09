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
import com.core.analysers.FrequencyCounter

object Main {
    def job(args: Array[String]): Unit = {
        val path = Paths.get(".\\resources\\data\\texts\\Orwell1984.txt")
        val text = Source.fromFile(path.toFile).take(2000).mkString.toUpperCase.replaceAll("[^A-Z]", "")
        
        val toCount = Set(
            "THE", "AND", "THAT", "HAVE", "FOR", "YOU", "WITH", "SAY", "THIS", "THEY", "FROM", "BUT", "WILL", "WHAT", "ABOUT", "WHEN", "MAKE", "LIKE", "TIME", "JUST", "KNOW", "TAKE", "PEOPLE", "INTO", "YEAR", "YOUR", "GOOD", "SOME", "COULD", "THEM", "THAN", "THEN", "LOOK", "ONLY", "COME", "OVER", "THINK", "ALSO", "BACK", "AFTER", "WORK", "FIRST", "WELL", "EVEN", "WANT", "BECAUSE", "ANYTHING", "THROUGH", "MUCH", "GREAT", "BEFORE", "MUST", "SAME", "LONG", "SUCH", "LITTLE", "WHERE", "PART", "AGAIN", "ANOTHER", "FIND", "STILL", "NEW", "PLACE", "WANT", "GIVE", "THINGS", "LIFE", "WAY", "DAY", "VERY"
        )

        println(text.length)

        val data = new CipherDataBlock(text, UppercaseLetters)
        val stringData = data.mkString
        val startTime = System.nanoTime()
        val result = FrequencyCounter.calculate(stringData, toCount)
        val endTime = System.nanoTime()
        println(result)
        println(s"Elapsed time: ${(endTime - startTime) / 1000000}ms")
    }

    def main(args: Array[String]): Unit = {
        job(args)
    }
}
