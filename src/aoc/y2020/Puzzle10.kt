package aoc.y2020

import aoc.Puzzle

fun main() {
    Puzzle10().runAndProfile()
}

class Puzzle10 : Puzzle(2020, 10) {
    override fun run() {
        val jolts = listOf(0) + inputLines
                .map { it.toInt() }
                .sorted()
        val diffs = jolts.windowed(2, 1) { it[1] - it[0] }
        println((diffs.count { it == 3 } + 1) * diffs.count { it == 1 })
        val perms = longArrayOf(1, 0, 0, 0)
        diffs.forEach { diff ->
            perms.copyInto(perms, diff, 0, 4 - diff)
            perms.fill(0, 0, diff)
            perms[0] += perms.sum()
        }
        println(perms[0])
    }
}