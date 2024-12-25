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

    def this(pairs: (K, V)*) = {
        this()
        pairs.foreach { case (k, v) => addMapping(k, v) }
    }

    def this(map: Iterable[(K, V)]) = {
        this()
        map.foreach { case (k, v) => addMapping(k, v) }
    }

    /** Add a mapping to the BiMap.
      *
      * If the key or value already exists in the BiMap, the old mapping is removed.
      * @param a
      *   The key.
      * @param b
      *   The value.
      * @return
      *   The modified BiMap.
      */
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

    /** Get the value associated with a key.
      *
      * @param a
      *   The key.
      * @return
      *   The value associated with the key, if it exists.
      */
    def apply(a: K): V = forwardMap(a);

    /** Get the key associated with a value.
      *
      * @param b
      *   The value.
      * @return
      *   The key associated with the value, if it exists.
      */
    def reverse(b: V): K = reverseMap(b);

    /** Get the value associated with a key.
      *
      * @param a
      *   The key.
      * @return
      *   The value associated with the key, if it exists.
      */
    def get(a: K): Option[V] = forwardMap.get(a);

    /** Get the value associated with a key.
      *
      * @param a
      *   The key.
      * @param default
      *   The default value to return if the key does not exist.
      * @return
      *   The value associated with the key, if it exists. Otherwise, the default value.
      */
    def get(a: K, default: V): V = forwardMap.getOrElse(a, default)

    /** Get the key associated with a value.
      *
      * @param b
      *   The value.
      * @return
      *   The key associated with the value, if it exists.
      */
    def getReverse(b: V): Option[K] = reverseMap.get(b)

    /** Get the key associated with a value.
      *
      * @param b
      *   The value.
      * @param default
      *   The default key to return if the value does not exist.
      * @return
      *   The key associated with the value, if it exists. Otherwise, the default key.
      */
    def getReverse(b: V, default: K): K = reverseMap.getOrElse(b, default)

    /** Update the mapping for a key. Same as `addMapping()`.
      *
      * If the key already exists in the BiMap, the old mapping is removed.
      * @param a
      *   The key.
      * @param b
      *   The value.
      */
    def update(a: K, b: V): Unit = addMapping(a, b)

    /** Remove a key-value pair from the BiMap.
      *
      * @param a
      *   The key of the pair to remove.
      * @return
      *   The value associated with the key, if it exists.
      */
    def pop(a: K) = {
        forwardMap.remove(a).flatMap { b =>
            reverseMap.remove(b)
            Some(b)
        }
    }

    /** Remove a value-key pair from the BiMap.
      *
      * @param b
      *   The value of the pair to remove.
      * @return
      *   The key associated with the value, if it exists.
      */
    def popReverse(b: V) = {
        reverseMap.remove(b).flatMap { a =>
            forwardMap.remove(a)
            Some(a)
        }
    }

    /** Remove a key-value pair from the BiMap.
      *
      * @param a
      *   The key of the pair to remove.
      * @return
      *   The modified BiMap.
      */
    def remove(a: K) = {
        pop(a)
        this
    }

    /** Remove a value-key pair from the BiMap.
      *
      * @param b
      *   The value of the pair to remove.
      * @return
      *   The modified BiMap.
      */
    def removeReverse(b: V) = {
        popReverse(b)
        this
    }

    def keys: Iterable[K] = forwardMap.keys
    def values: Iterable[V] = forwardMap.values
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

    /** Create a new BiMap by using the values of this BiMap to look up values in another BiMap.
      *
      * @param other
      *   The other BiMap.
      * @tparam T
      *   The type of the values of the other BiMap.
      * @return
      *   A new BiMap with the keys of this BiMap and the values of the other BiMap.
      */
    def mergeWithBiMap[T](other: BiMap[V, T]): BiMap[K, T] = {
        val newMap = new BiMap[K, T]
        this.iterator.foreach { case (k, v) =>
            other.get(v).foreach { t =>
                newMap.addMapping(k, t)
            }
        }
        newMap
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
