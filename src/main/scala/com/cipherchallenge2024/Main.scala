package com.cipherchallenge2024

import com.cipherchallenge2024.challenges.*

import java.nio.file.Paths
import scala.io.StdIn

object Main {
    val solutionMap = Map(
        "0A" -> Challenge0A,
        "0B" -> Challenge0B,
        "1A" -> Challenge0A,
        "1B" -> ChallengeMASubstitution,
        "2A" -> ChallengeMASubstitution,
        "2B" -> ChallengeMASubstitution,
        "3A" -> ChallengeMASubstitution,
        "3B" -> ChallengePermutation,
        "4A" -> ChallengePermutation,
        "4B" -> ChallengeMASubstitution,
        "5A" -> ChallengePermutation,
        "5B" -> ChallengeVigenere,
        "6A" -> ChallengeVigenere,
        "6B" -> ChallengeVigenere,
        "7A" -> ChallengeColumn,
        "7B" -> Challenge7B,
        "8A" -> Challenge8A,
    )

    def main(args: Array[String]): Unit = {
        println("Enter a challenge key or \"exit\" to exit:")
        while (true) {
            print("> ")
            val input = StdIn.readLine().toUpperCase()
            if (input == "EXIT") {
                return
            } else if (!solutionMap.contains(input)) {
                println("Invalid challenge key")
            } else {
                val solution = solutionMap(input)
                val ciphertext = getCiphertext(input)
                val broken = solution.decrypt(ciphertext)
                println(broken)
            }
        }
    }
    
    def getCiphertext(key: String): String = {
        val path = Paths.get(".\\resources\\ciphertexts\\", "Ciphertext" + key.toUpperCase() + ".txt")
        val source = io.Source.fromFile(path.toFile, "UTF-8")
        source.getLines.mkString("\n")
    }
}
