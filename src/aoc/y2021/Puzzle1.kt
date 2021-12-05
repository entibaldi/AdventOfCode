package aoc.y2021

import aoc.Puzzle

fun main() {
    Puzzle1().runAndProfile()
}

class Puzzle1 : Puzzle(2021, 1) {

    override fun run() {
        val input = inputLines.map { it.toInt() }
        println(computePart1(input))
        println(computePart2(input))
    }

    private fun computePart1(input: List<Int>) =
        input.withIndex().count { (index, it) -> index > 0 && it > input[index-1] }

    private fun computePart2(input: List<Int>): Int {
        val window = input.windowed(size = 3, step = 1)
            .map { it.sum() }
        return window.withIndex().count { (index, it) -> index > 0 && it > window[index-1] }
    }

}