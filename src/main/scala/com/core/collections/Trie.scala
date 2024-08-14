package com.core.collections

import scala.compiletime.uninitialized
import scala.collection.mutable.Map
import scala.collection.parallel.CollectionConverters._
import com.core.cipherdata.CipherDataBlock

class TrieNode[T] {
    var children: Map[T, TrieNode[T]] = Map()
    var fail: TrieNode[T] = uninitialized
    var output: List[Iterable[T]] = List()
}
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
