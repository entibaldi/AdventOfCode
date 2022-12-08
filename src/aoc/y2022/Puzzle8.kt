package aoc.y2022

import aoc.Puzzle
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.*
import org.jetbrains.kotlinx.multik.ndarray.operations.indexOfFirst
import org.jetbrains.kotlinx.multik.ndarray.operations.reversed

fun main() {
    Puzzle8().runAndProfile()
}

class Puzzle8 : Puzzle(2022, 8) {

    override fun run() {
        val digitsMap = inputLines.map { it.toCharArray().map { c -> c.digitToInt() } }
        val height = digitsMap.size
        val width = digitsMap[0].size
        val f: D2Array<Int> = mk.ndarray(digitsMap.flatten(), intArrayOf(height, width))

        val visibleCoordinates = hashSetOf(
            0 to 0, 0 to width - 1, height - 1 to 0, height - 1 to width - 1
        )

        var lastVisible: Pair<Int, Int> = 0 to 0

        fun addVisible(visible: Pair<Int, Int>) {
            visibleCoordinates += visible
            lastVisible = visible
        }

        // horizontal scan
        for (i in 1..height - 2) {
            addVisible(i to 0)
            for (j in 1..width - 2) {
                if (f[i, j] > f[lastVisible]) addVisible(i to j)
            }
            addVisible(i to height - 1)
            for (j in (1..width - 2).reversed()) {
                if (f[i, j] > f[lastVisible]) addVisible(i to j)
            }
        }
        // vertical scan
        for (j in 1..width - 2) {
            addVisible(0 to j)
            for (i in 1..height - 2) {
                if (f[i, j] > f[lastVisible]) addVisible(i to j)
            }
            addVisible(height - 1 to j)
            for (i in (1..height - 2).reversed()) {
                if (f[i, j] > f[lastVisible]) addVisible(i to j)
            }
        }
        println(visibleCoordinates.size)
        println(visibleCoordinates.maxOf { (i, j) -> f.getScenicScore(i, j) })
    }

    private operator fun D2Array<Int>.get(coords: Pair<Int, Int>): Int = this[coords.first, coords.second]

    private fun D2Array<Int>.getScenicScore(i: Int, j: Int): Int {
        val v = this[i, j]
        val x1 = calculateVisible(this[i + 1..shape[0], j], v)
        val x2 = calculateVisible(this[0..i, j].reversed(), v)
        val y1 = calculateVisible(this[i, j + 1..shape[1]], v)
        val y2 = calculateVisible(this[i, 0..j].reversed(), v)
        return x1 * x2 * y1 * y2
    }

    private fun calculateVisible(trees: MultiArray<Int, D1>, value: Int): Int =
        trees.indexOfFirst { it >= value }.takeIf { it >= 0 }?.plus(1) ?: trees.size
}



