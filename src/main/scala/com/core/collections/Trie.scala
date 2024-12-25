package com.core.collections

import scala.compiletime.uninitialized
import scala.collection.mutable.Map
import scala.collection.parallel.CollectionConverters._
import com.core.cipherdata.CipherDataBlock

/** A Trie is a tree data structure that is used to store a dynamic set of strings. It is used in string matching
  * algorithms like Aho-Corasick.
  *
  * The Trie is built by inserting the strings one character at a time. The root node is empty, and each child node
  * represents a character in the string.
  *
  * The Trie can be used to search for a string in a text. The search is done by traversing the Trie, following the
  * characters in the text. If the search reaches a node that is not in the Trie, it backtracks to the last node that has
  * a child with the current character.
  *
  * The Trie can also be used to search for multiple strings in a text. This is done by building a failure link between
  * nodes. The failure link is a pointer to the longest suffix of the current node that is also a prefix of the Trie.
  *
  * @tparam T
  *   The type of the characters in the Trie.
  */
class TrieNode[T] {
    var children: Map[T, TrieNode[T]] = Map()
    var fail: TrieNode[T] = uninitialized
    var output: List[Iterable[T]] = List()
}

/** Companion object for the TrieNode class. */
object TrieNode {
    def buildTrie[T](phrases: Set[? <: Iterable[T]]): TrieNode[T] = {
        val root = new TrieNode[T]
        for (phrase <- phrases) {
            var node = root
            for (cell <- phrase) {
                if (!node.children.contains(cell)) {
                    node.children += (cell -> new TrieNode)
                }
                node = node.children(cell)
            }
            node.output ::= phrase
        }
        buildFailureLinks(root)
        root
    }
    private def buildFailureLinks[T](root: TrieNode[T]): Unit = {
        val queue = scala.collection.mutable.Queue[TrieNode[T]]()
        for (child <- root.children.values) {
            child.fail = root
            queue.enqueue(child)
        }

        while (queue.nonEmpty) {
            val current = queue.dequeue()
            for ((char, child) <- current.children) {
                var failNode = current.fail
                while (failNode != null && !failNode.children.contains(char)) {
                    failNode = failNode.fail
                }
                if (failNode == null) {
                    child.fail = root
                } else {
                    child.fail = failNode.children(char)
                    child.output ++= child.fail.output
                }
                queue.enqueue(child)
            }
        }
    }
    def search[T](text: Iterable[T], root: TrieNode[T]): Map[Iterable[T], Int] = {
        var node = root
        val counts = scala.collection.mutable.Map[Iterable[T], Int]().withDefaultValue(0)

        for (char <- text) {
            while (node != null && !node.children.contains(char)) {
                node = node.fail
            }
            if (node == null) {
                node = root
            } else {
                node = node.children(char)
                for (phrase <- node.output) {
                    counts(phrase) += 1
                }
            }
        }
        counts
    }
}
