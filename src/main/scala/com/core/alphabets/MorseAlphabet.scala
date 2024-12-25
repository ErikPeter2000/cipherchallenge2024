package com.core.alphabets

import com.core.collections._

/** Represents the International Morse alphabet. A-Z are represented by 0-25, and 1-9, 0 are represented by 26-35.
  */
object MorseAlphabet extends BiMapAlphabet[String] {
    protected override val biMap = new BiMap[Int, String](
        0 -> ".-",
        1 -> "-...",
        2 -> "-.-.",
        3 -> "-..",
        4 -> ".",
        5 -> "..-.",
        6 -> "--.",
        7 -> "....",
        8 -> "..",
        9 -> ".---",
        10 -> "-.-",
        11 -> ".-..",
        12 -> "--",
        13 -> "-.",
        14 -> "---",
        15 -> ".--.",
        16 -> "--.-",
        17 -> ".-.",
        18 -> "...",
        19 -> "-",
        20 -> "..-",
        21 -> "...-",
        22 -> ".--",
        23 -> "-..-",
        24 -> "-.--",
        25 -> "--..",
        26 -> "-----",
        27 -> ".----",
        28 -> "..---",
        29 -> "...--",
        30 -> "....-",
        31 -> ".....",
        32 -> "-....",
        33 -> "--...",
        34 -> "---..",
        35 -> "----."
    )
}
