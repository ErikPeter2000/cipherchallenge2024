package com.core.analysers

import com.core.cipherdata.CipherDataBlock
import com.core.collections.TrieNode

import scala.collection.mutable.Map

/** Class for counting the frequency of specific elements in a contiguous data block.
  *
  * For example, for counting the frequency of bigrams in a text block.
  */
object FrequencyCounter {

    /** Calculate the frequency of phrases in the data block using the Aho-Corasick algorithm.
      *
      * @param data
      * @param phrases
      * @return
      */
    def calculate(data: CipherDataBlock[Char], phrases: Set[? <: Iterable[Char]]): Map[Iterable[Char], Int] = {
        var root = TrieNode.buildTrie(phrases)
        TrieNode.search(data, root)
    }

    /** Calculate the frequency of phrases in the data block using the Aho-Corasick algorithm.
      *
      * @param data
      * @param phrasesTrie
      * @return
      */
    def calculate(data: CipherDataBlock[Char], phrasesTrie: TrieNode[Char]): Map[Iterable[Char], Int] = {
        TrieNode.search(data, phrasesTrie)
    }
}
