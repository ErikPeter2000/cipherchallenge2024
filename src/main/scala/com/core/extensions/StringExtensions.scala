package com.core.extensions

object StringExtensions {
    extension (str: String) {

        /** Highlights characters in the string. Some consoles may not support this.
          *
          * @param chars
          *   The characters to highlight.
          * @param color
          *   The color to highlight the characters.
          */
        def highlight(chars: Seq[Char], color: String = Console.RED): String = {
            str.map { c =>
                if (chars.contains(c)) s"$color$c${Console.RESET}"
                else c.toString
            }.mkString
        }

        /** How closely the string matches another string.
          *
          * @param other
          *   The other string to compare to.
          * @return
          *   A value between 0 and 1, where 1 is a perfect match.
          */
        def relativeMatch(other: String): Double = {
            val length = str.length
            val matches = str.zip(other).count { case (a, b) => a == b }
            matches.toDouble / length
        }
    }
}
