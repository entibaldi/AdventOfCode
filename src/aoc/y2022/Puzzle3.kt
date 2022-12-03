package aoc.y2022

import aoc.Puzzle

fun main() {
    Puzzle3().runAndProfile()
}

class Puzzle3 : Puzzle(2022, 3) {

    override fun run() {
        val input = inputLines.map { it.toCharArray() }
        println(computePart1(input))
        println(computePart2(input))
    }

    private fun computePart1(input: List<CharArray>) =
        input.map {
            it.copyOfRange(0, it.size / 2).toSet() to it.copyOfRange(it.size / 2, it.size).toSet()
        }.sumOf { (comp1, comp2) -> (comp1 intersect comp2).sumOf { it.priority() } }

    private fun computePart2(input: List<CharArray>) =
        input.chunked(3)
            .map { group -> group.map { it.toSet() } }
            .sumOf { (it[0] intersect it[1] intersect it[2]).sumOf { c -> c.priority() } }

    private fun Char.priority() = if (isLowerCase()) (this - 'a') + 1 else (this - 'A') + 27
}

