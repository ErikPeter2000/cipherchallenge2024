package com.core.extensions

object StringExtensions {
  extension (str: String) {
    def cipherFormat: String = {
      str.replaceAll("[^a-zA-Z]", "").toUpperCase
    }
  }
}
