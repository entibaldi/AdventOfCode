package aoc.y2022

import aoc.Puzzle

fun main() {
    Puzzle1().runAndProfile()
}

class Puzzle1 : Puzzle(2022, 1) {

    override fun run() {
        val input = inputGroups.map {
            it.lines().map { cal -> cal.toInt() }
        }
        println(computePart1(input))
        println(computePart2(input))
    }

    private fun computePart1(input: List<List<Int>>) =
        input.maxOf { it.sum() }

    private fun computePart2(input: List<List<Int>>) =
        input.map { it.sum() }.sortedDescending().take(3).sum()

}