package aoc.y2022

import aoc.Puzzle

fun main() {
    Puzzle6().runAndProfile()
}

class Puzzle6 : Puzzle(2022, 6) {

    override fun run() {
        val input = inputText.toCharArray().toList()
        println(computePart1(input))
        println(computePart2(input))
    }

    private fun computePart1(input: List<Char>): Int = findPacket(input, 4)

    private fun computePart2(input: List<Char>): Int = findPacket(input, 14)

    private fun findPacket(input: List<Char>, windowSize: Int): Int =
        input.windowed(size = windowSize, step = 1)
            .indexOfFirst { chars -> chars.distinct().size == windowSize } + windowSize
}
