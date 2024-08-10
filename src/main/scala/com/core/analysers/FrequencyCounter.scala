package com.core.analysers

import scala.collection.mutable.Map
import scala.collection.parallel.CollectionConverters._
import com.core.cipherdata.CipherDataBlock

/** Class for counting the frequency of elements in a contiguous data block.
  */
object FrequencyCounter {
    class TrieNode {
        var children: Map[Char, TrieNode] = Map()
        var fail: TrieNode = _
        var output: List[Iterable[Char]] = List()
    }
    def buildTrie(phrases: Set[_ <: Iterable[Char]]): TrieNode = {
        val root = new TrieNode
        for (phrase <- phrases) {
            var node = root
            for (char <- phrase) {
                if (!node.children.contains(char)) {
                    node.children += (char -> new TrieNode)
                }
                node = node.children(char)
            }
            node.output ::= phrase
        }
        root
    }
    def buildFailureLinks(root: TrieNode): Unit = {
        val queue = scala.collection.mutable.Queue[TrieNode]()
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
    def searchText(text: Iterable[Char], root: TrieNode): Map[Iterable[Char], Int] = {
        var node = root
        val counts = scala.collection.mutable.Map[Iterable[Char], Int]().withDefaultValue(0)

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
    def calculate(data: CipherDataBlock[Char], phrases: Set[_ <: Iterable[Char]]): Map[Iterable[Char], Int] = {
        var root = buildTrie(phrases)
        buildFailureLinks(root)
        searchText(data, root)
    }
}
