package com.core.extensions

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
          * @param columnLimit
          *  The maximum number of column pairs to display.
          * @param roundTo
          *  The number of decimal places to round the values to. Only applicable to Double values.
          * @return
          *   A string representation of the table.
          */
        def pretty(
            order: KeyPairOrder = KeyPairOrder.None,
            header: (String, String) = ("Key", "Value"),
            title: String = "",
            padding: Int = 2,
            columnLimit: Option[Int] = None,
            roundTo: Option[Int] = None
        )(implicit ordKey: Ordering[K], ordValue: Ordering[V]): String = {
            var pairs = iterable.toSeq
            if pairs.isEmpty then return iterable.toString()
            // Sort the pairs based on the provided order parameter
            order.match
                case KeyPairOrder.Key       => pairs = pairs.sortBy(_._1)
                case KeyPairOrder.KeyDesc   => pairs = pairs.sortBy(_._1).reverse
                case KeyPairOrder.Value     => pairs = pairs.sortBy(_._2)
                case KeyPairOrder.ValueDesc => pairs = pairs.sortBy(_._2).reverse
                case _                      =>
            
            // Round the pairs if necessary
            var pairsStringIter = pairs.map { case (k, v) => (s"$k",s"$v") }
            if roundTo.isDefined && roundTo.get > 0 && pairs.head._2.isInstanceOf[Double] then {
                pairsStringIter = pairs.map { case (k, v) => (k.toString, v.asInstanceOf[Double].formatted(s"%.${roundTo.get}f")) }
            }
            val pairsString=pairsStringIter.toSeq

            // Separate the pairs into paired columns to fit the display
            val pairColumns = pairsString.grouped(MAX_DISPLAY_LINES).toSeq
            val columnsCount = if (columnLimit.isDefined) columnLimit.get min pairColumns.size else pairColumns.size
            val tallestColumnSize = pairColumns.map(_.size).max

            // Calculate the width of each column
            val columnPairWidths: Seq[(Int, Int)] = pairColumns.map { column =>
                val keyWidth = Math.max(column.map(_._1.toString.length).max, header._1.size)
                val valueWidth = Math.max(column.map(_._2.toString.length).max, header._2.size)
                (keyWidth, valueWidth)
            }

            val columnMargin: Int = Math.max(padding, 0)
            val sb = new StringBuilder()

            if title.size > 0 then sb.append(s" ${title}:\n")

            (-3 to tallestColumnSize).foreach(y => { // First 3 y are for the header
                (0 to columnsCount - 1).foreach(x => {
                    val column = pairColumns(x)
                    val columnSize = column.size
                    val keyWidth = columnPairWidths(x)._1
                    val valueWidth = columnPairWidths(x)._2
                    if (y == -3) { // Top border
                        sb.append("\u250C") // ┌
                        sb.append("─" * (keyWidth + 2))
                        sb.append("\u252C") // ┬
                        sb.append("─" * (valueWidth + 2))
                        sb.append("\u2510") // ┐
                    }
                    else if (y == -2) { // Header
                        val keyHeader = header._1.reverse.padTo(keyWidth, ' ').reverse // right-align key
                        val valueHeader = header._2.padTo(valueWidth, ' ')
                        sb.append("\u2502 ") // │
                        sb.append(keyHeader)
                        sb.append(" \u2502 ")
                        sb.append(valueHeader)
                        sb.append(" \u2502")
                    } else if (y == -1) { // Header separator
                        sb.append("\u251C") // ├
                        sb.append("─" * (keyWidth + 2))
                        sb.append("\u253C") // ┼
                        sb.append("─" * (valueWidth + 2))
                        sb.append("\u2524") // ┤
                    } else if (y < columnSize) { // Data
                        val (key, value) = column(y)
                        sb.append("\u2502 ")
                        sb.append(key.toString.reverse.padTo(keyWidth, ' ').reverse) // right-align key
                        sb.append(" \u2502 ")
                        sb.append(value.toString.padTo(valueWidth, ' '))
                        sb.append(" \u2502")
                    } else if (y == columnSize) { // Bottom border
                        sb.append("\u2514") // └
                        sb.append("─" * (keyWidth + 2))
                        sb.append("\u2534") // ┴
                        sb.append("─" * (valueWidth + 2))
                        sb.append("\u2518") // ┘
                    } else { // Empty space
                        sb.append(" " * (keyWidth + valueWidth + 8))
                    }
                    if (x < columnsCount - 1) { // Padding between column pairs
                        sb.append(" " * columnMargin)
                    } else { // Line break if last column pair
                        sb.append("\n")
                    }
                })
            })

            sb.toString()
        }
    }
}
