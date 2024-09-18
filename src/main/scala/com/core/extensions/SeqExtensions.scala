package com.core.extensions

object SeqExtensions {
    extension [T](seq: Seq[T]) {
        def swap(i: Int, j: Int): Seq[T] = {
            val temp = seq(i)
            seq.updated(i, seq(j)).updated(j, temp)
        }
        def swapRandom(implicit n: Int = 1): Seq[T] = {
            var temp = seq
            for _ <- 0 until n do temp = {
                val index1 = scala.util.Random.nextInt(seq.size)
                var index2 = scala.util.Random.nextInt(seq.size)
                while index1 == index2 do index2 = scala.util.Random.nextInt(seq.size)
                temp.swap(index1, index2)
            }
            temp
        }
    }
}
