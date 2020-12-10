package aoc.y2020

import aoc.Puzzle

fun main() {
    Puzzle10().runAndProfile()
}

class Puzzle10 : Puzzle(2020, 10) {
    override fun run() {
        val adapters = inputLines
                .map { it.toInt() }
                .sorted()
        val jolts = listOf(0) + adapters + (adapters.max()!! + 3)
        val diffs = jolts.windowed(2, 1) { it[1] - it[0] }
        println(diffs.count { it == 3 } * diffs.count { it == 1 })
    }
}