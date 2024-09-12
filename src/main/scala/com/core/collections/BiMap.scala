package com.core.collections

/** A bidirectional map that allows for quick lookups in both directions.
  * @tparam K
  *   The type of the keys.
  * @tparam V
  *   The type of the values.
  * @example
  *   {{{
  * val biMap = new BiMap[Char, Char](
  *    'A' -> 'Z',
  *    'B' -> 'Y',
  * )
  * biMap.get('A') // Some("Z")
  * biMap.getReverse('Y') // Some('B')
  * biMap.get('C') // None
  * biMap.get('C', 'C') // 'C'. You can specify default values.
  *   }}}
  */
class BiMap[K, V] extends Iterable[(K, V)] {

    private val forwardMap = scala.collection.mutable.Map.empty[K, V]
    private val reverseMap = scala.collection.mutable.Map.empty[V, K]

    def addMapping(a: K, b: V): BiMap[K, V] = {
    forwardMap.get(a).foreach { oldB =>
        reverseMap.remove(oldB)
    }
    reverseMap.get(b).foreach { oldA =>
        forwardMap.remove(oldA)
    }
    forwardMap(a) = b
    reverseMap(b) = a
    
    this
}

    def this(pairs: (K, V)*) = {
        this()
        pairs.foreach { case (k, v) => addMapping(k, v) }
    }

    def this(map: Iterable[(K, V)]) = {
        this()
        map.foreach { case (k, v) => addMapping(k, v) }
    }

    def apply(a: K): V = forwardMap(a);
    def reverse(b: V): K = reverseMap(b);
    def get(a: K): Option[V] = forwardMap.get(a);
    def get(a: K, default: V): V = forwardMap.getOrElse(a, default)
    def getReverse(b: V): Option[K] = reverseMap.get(b)
    def getReverse(b: V, default: K): K = reverseMap.getOrElse(b, default)
    def update(a: K, b: V): Unit = addMapping(a, b)

    def pop(a: K) = {
        forwardMap.remove(a).flatMap { b =>
            reverseMap.remove(b)
            Some(b)
        }
    }
    def popReverse(b: V) = {
        reverseMap.remove(b).flatMap { a =>
            forwardMap.remove(a)
            Some(a)
        }
    }

    def remove(a: K) = {
        pop(a)
        this
    }
    def removeReverse(b: V) = {
        popReverse(b)
        this
    }

    def keys: Iterable[K] = forwardMap.keys
    def values: Iterable[V] = forwardMap.values
    import scala.math.Ordering.ordered

    def iterator: Iterator[(K, V)] = forwardMap.iterator
    def containsKey(a: K): Boolean = forwardMap.contains(a)
    def containsValue(b: V): Boolean = reverseMap.contains(b)

    def +=(kv: (K, V)): BiMap[K, V] = {
        addMapping(kv._1, kv._2)
        this
    }
    def ++=(other: Iterable[(K, V)]): BiMap[K, V] = {
        other.iterator.foreach { case (k, v) => addMapping(k, v) }
        this
    }

    override def clone(): BiMap[K, V] = {
        val newMap = new BiMap[K, V]()
        newMap ++= this
        newMap
    }
}

object BiMap {
    def empty[K, V]: BiMap[K, V] = new BiMap[K, V]()
    def createFromSeq[V](seq: Seq[V]): BiMap[Int, V] = {
        val biMap = new BiMap[Int, V]
        seq.zipWithIndex.foreach { case (v, i) => biMap.addMapping(i, v) }
        biMap
    }
}
