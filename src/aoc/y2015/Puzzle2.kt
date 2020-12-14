package aoc.y2015

import aoc.Puzzle
import kotlin.math.min

fun main() {
    Puzzle2().runAndProfile()
}

class Puzzle2 : Puzzle(2015, 2) {

    override fun run() {
        val dimensions = inputLines.map { it.split("x").map { it.toInt() } }
        part1(dimensions)
        part2(dimensions)
    }

    private fun part1(dimensions: List<List<Int>>) {
        println(dimensions.sumBy {
            val a1 = it[0]*it[1]
            val a2 = it[1]*it[2]
            val a3 = it[0]*it[2]
            2 * a1 + 2 * a2 + 2 * a3 + min(min(a1, a2), a3)
        })
    }

    private fun part2(dimensions: List<List<Int>>) {
        println(dimensions.sumBy {
            val p1 = 2*(it[0]+it[1])
            val p2 = 2*(it[1]+it[2])
            val p3 = 2*(it[0]+it[2])
            val v = it[0]*it[1]*it[2]
            v + min(min(p1, p2), p3)
        })
    }
}