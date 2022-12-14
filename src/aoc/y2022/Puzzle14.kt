package aoc.y2022

import aoc.Puzzle
import aoc.utils.P
import aoc.utils.Point
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.count
import org.jetbrains.kotlinx.multik.ndarray.operations.indexOfFirst
import org.jetbrains.kotlinx.multik.ndarray.operations.toIntArray
import org.jetbrains.kotlinx.multik.ndarray.operations.toList
import kotlin.math.max
import kotlin.math.min

fun main() {
    Puzzle14().runAndProfile()
}

class Puzzle14 : Puzzle(2022, 14) {

    override fun run() {
        val walls: List<Pair<Point, Point>> = inputLines.map { parseWall(it) }.flatten()
        val minX = walls.minOf { (l, r) -> min(l.x, r.x) }
        val maxX = walls.maxOf { (l, r) -> max(l.x, r.x) }
        val maxY = walls.maxOf { (l, r) -> max(l.y, r.y) }
        val rows = maxY + 1
        val cols = (maxX - minX) + 1
        val cave: D2Array<Int> = mk.ndarray(List(rows * cols) { 0 }, intArrayOf(rows, cols))
        cave[0, 500 - minX] = -1 // sand hole
        for ((wallStart, wallEnds) in walls) {
            (wallStart.x..wallEnds.x).forEach { x -> cave[wallStart.y, x - minX] = 1 }
            (wallStart.y..wallEnds.y).forEach { y -> cave[y, wallStart.x - minX] = 1 }
        }

        var sandX = 500 - minX
        var sandY = 0
        while (sandX in 0 until rows) {
            // vertical fall
            sandY += cave[sandY..rows, sandX].indexOfFirst { it != 0 && it != -1 } - 1
            if (sandY < 0) break
            if (sandY < maxY && (sandX == 0 || cave[sandY + 1, sandX - 1] == 0)) {
                sandX-- // fall left
                sandY++
            } else if (sandY < maxY && (sandX == rows - 1 || cave[sandY + 1, sandX + 1] == 0)) {
                sandX++ // fall right
                sandY++
            } else {
                cave[sandY, sandX] = 2 // settle
                sandX = 500 - minX
                sandY = 0
            }
        }

        cave.print()
        // part 1
        println(cave.count { it == 2 })

        // part 2
        val extendedCave: D2Array<Int> = mk.ndarray(cave.toList() + List(2 * cols) { 0 }, intArrayOf(rows + 2, cols))
        for ((y, x) in extendedCave.multiIndices) {
            if (y > 0 && x > 0 && x < cols - 1) {
                val a1 = extendedCave[y - 1, x - 1]
                val a2 = extendedCave[y - 1, x]
                val a3 = extendedCave[y - 1, x + 1]
                if (a1 == 1 && a2 == 1 && a3 == 1) extendedCave[y, x] = 1 // add wall shadow
            }
            if (y == rows + 1) extendedCave[y, x] = 1 // add bottom wall
        }
        extendedCave.print()
        // I leave this count ugly because I can
        println((rows + 2) * (rows + 2) - extendedCave.count { it == 1 } - (2 * (rows + 2) - 1) + cols)
    }

    private fun parseWall(wallString: String): List<Pair<Point, Point>> =
        wallString.split(" -> ").windowed(2).map { (leftString, rightString) ->
            val left = parsePoint(leftString)
            val right = parsePoint(rightString)
            if (left.x > right.x || left.y > right.y) right to left else left to right
        }

    private fun parsePoint(startString: String): Point = startString.split(",")
        .let { (x, y) -> P(x.toInt(), y.toInt()) }

    private fun D2Array<Int>.print() {
        for (i in 0 until shape[0]) {
            println(this[i].toIntArray().map {
                when (it) {
                    -1 -> 'x'
                    1 -> '#'
                    2 -> 'o'
                    else -> '.'
                }
            }.joinToString(""))
        }
        println()
    }
}
