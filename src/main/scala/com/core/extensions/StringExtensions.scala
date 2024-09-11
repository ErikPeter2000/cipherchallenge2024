package com.core.extensions

object StringExtensions {
  extension (str: String) {
    def highlight(chars: Seq[Char], color: String = Console.RED): String = {
      str.map { c =>
        if (chars.contains(c)) s"$color$c${Console.RESET}"
        else c.toString
      }.mkString
    }
  }
}
