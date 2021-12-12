package aoc.utils

import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get

@OptIn(ExperimentalStdlibApi::class)
fun <T> D2Array<T>.adjacents(x: Int, y: Int, diagonal: Boolean = true): List<T> =
    buildList {
        val lastRow = this@adjacents.shape[0] - 1
        val lastColumn = this@adjacents.shape[1] - 1
        if (x > 0) {
            if (y > 0 && diagonal) add(this@adjacents[x - 1, y - 1])
            add(this@adjacents[x - 1, y])
            if (y < lastColumn && diagonal) add(this@adjacents[x - 1, y + 1])
        }
        if (y > 0) add(this@adjacents[x, y - 1])
        if (y < lastColumn) add(this@adjacents[x, y + 1])
        if (x < lastRow) {
            if (y > 0 && diagonal) add(this@adjacents[x + 1, y - 1])
            add(this@adjacents[x + 1, y])
            if (y < lastColumn && diagonal) add(this@adjacents[x + 1, y + 1])
        }
    }

@OptIn(ExperimentalStdlibApi::class)
fun D2Array<*>.adjacentIndexes(x: Int, y: Int, diagonal: Boolean = true): List<Pair<Int, Int>> =
    buildList {
        val lastRow = this@adjacentIndexes.shape[0] - 1
        val lastColumn = this@adjacentIndexes.shape[1] - 1
        if (x > 0) {
            if (y > 0 && diagonal) add(x - 1 to y - 1)
            add(x - 1 to y)
            if (y < lastColumn && diagonal) add(x - 1 to y + 1)
        }
        if (y > 0) add(x to y - 1)
        if (y < lastColumn) add(x to y + 1)
        if (x < lastRow) {
            if (y > 0 && diagonal) add(x + 1 to y - 1)
            add(x + 1 to y)
            if (y < lastColumn && diagonal) add(x + 1 to y + 1)
        }
    }