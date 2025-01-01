package com.core.alphabets

import com.core.collections.BiMap

/** Represents the uppercase letters of the English alphabet, excluding the letter J. A-I, K-Z (0-8, 9-24).
  *
  * This is useful for grid ciphers with only 25 letters, where the letter J is omitted.
  */
object UppercaseLettersNoJ extends BiMapAlphabet[Char] {
    protected override val biMap: BiMap[Int, Char] = new BiMap(
        0 -> 'A',
        1 -> 'B',
        2 -> 'C',
        3 -> 'D',
        4 -> 'E',
        5 -> 'F',
        6 -> 'G',
        7 -> 'H',
        8 -> 'I',
        9 -> 'K',
        10 -> 'L',
        11 -> 'M',
        12 -> 'N',
        13 -> 'O',
        14 -> 'P',
        15 -> 'Q',
        16 -> 'R',
        17 -> 'S',
        18 -> 'T',
        19 -> 'U',
        20 -> 'V',
        21 -> 'W',
        22 -> 'X',
        23 -> 'Y',
        24 -> 'Z'
    )
}
