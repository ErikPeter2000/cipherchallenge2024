package com.core.collections

enum KeyPairOrder {
    case None, Key, KeyDesc, Value, ValueDesc
}

object IterableExtensions {
    val MAX_DISPLAY_LINES = 10

    extension [K, V](iterable: Iterable[(K, V)]) {

        /** Pretty prints the iterable of pairs in a table format. Types K and V should have a meaningful toString
          * method. The table will be split into column pairs of MAX_DISPLAY_LINES rows.
          *
          * @param order
          *   The order in which to sort the pairs.
          * @param header
          *   The header of the table. Defaults to ("Key", "Value").
          * @param title
          *   The title of the table.
          * @param padding
          *   The padding between column pairs when displayed across the page.
          * @return
          *   A string representation of the table.
          */
        def pretty(
            order: KeyPairOrder = KeyPairOrder.None,
            header: (String, String) = ("Key", "Value"),
            title: String = "",
            padding: Int = 2
        )(implicit ordKey: Ordering[K], ordValue: Ordering[V]): String = {
            var pairs = iterable.toSeq
            if pairs.isEmpty then return iterable.toString()
            order.match
                case KeyPairOrder.Key       => pairs = pairs.sortBy(_._1)
                case KeyPairOrder.KeyDesc   => pairs = pairs.sortBy(_._1).reverse
                case KeyPairOrder.Value     => pairs = pairs.sortBy(_._2)
                case KeyPairOrder.ValueDesc => pairs = pairs.sortBy(_._2).reverse
                case _                      =>

            val pairColumns = pairs.grouped(MAX_DISPLAY_LINES).toSeq
            val columnsCount = pairColumns.size
            val tallestColumnSize = pairColumns.map(_.size).max
            val maxKeyLength = Math.max(pairs.map(_._1.toString.length).max, header._1.size)
            val maxValueLength = Math.max(pairs.map(_._2.toString.length).max, header._2.size)
            val keyHeader = header._1.reverse.padTo(maxKeyLength, ' ').reverse
            val valueHeader = header._2.padTo(maxValueLength, ' ')
            val gap: Int = Math.max(padding, 0)

            val sb = new StringBuilder()
            if title.size > 0 then sb.append(s" ${title}:\n")
            (0 to columnsCount - 1).foreach(_ => {
                sb.append("\u250C") // ┌
                sb.append("─" * (maxKeyLength + 2))
                sb.append("\u252C") // ┬
                sb.append("─" * (maxValueLength + 2))
                sb.append("\u2510") // ┐
                sb.append(" " * gap)
            })
            sb.append("\n")
            (0 to columnsCount - 1).foreach(_ => {
                sb.append("\u2502 ") // │
                sb.append(keyHeader)
                sb.append(" \u2502 ")
                sb.append(valueHeader)
                sb.append(" \u2502")
                sb.append(" " * gap)
            })
            sb.append("\n")
            (0 to columnsCount - 1).foreach(_ => {
                sb.append("\u251C") // ├
                sb.append("─" * (maxKeyLength + 2))
                sb.append("\u253C") // ┼
                sb.append("─" * (maxValueLength + 2))
                sb.append("\u2524") // ┤
                sb.append(" " * gap)
            })
            sb.append("\n")

            (0 to tallestColumnSize).foreach(y => {
                (0 to columnsCount - 1).foreach(x => {
                    val column = pairColumns(x)
                    val columnSize = column.size
                    if (y < columnSize) {
                        val (key, value) = column(y)
                        sb.append("\u2502 ")
                        sb.append(key.toString.reverse.padTo(maxKeyLength, ' ').reverse)
                        sb.append(" \u2502 ")
                        sb.append(value.toString.padTo(maxValueLength, ' '))
                        sb.append(" \u2502")
                    } else if (y == columnSize) {
                        sb.append("\u2514") // └
                        sb.append("─" * (maxKeyLength + 2))
                        sb.append("\u2534") // ┴
                        sb.append("─" * (maxValueLength + 2))
                        sb.append("\u2518") // ┘
                    } else {
                        sb.append(" " * (maxKeyLength + maxValueLength + 8))
                    }
                    if (x < columnsCount - 1) {
                        sb.append(" " * gap)
                    } else {
                        sb.append("\n")
                    }
                })
            })

            sb.toString()
        }
    }
}
