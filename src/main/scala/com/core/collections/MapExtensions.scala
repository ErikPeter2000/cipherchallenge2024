package com.core.collections

object MapExtensions {
    extension [K, V](map: Map[K, V]) {
        def pretty = {
            map.map { case (k, v) => s"  $k: $v" }.mkString("{\n", ", \n", "\n}")
        }
    }
}
