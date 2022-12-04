package aoc.y2022

import aoc.Puzzle

fun main() {
    Puzzle4().runAndProfile()
}

class Puzzle4 : Puzzle(2022, 4) {

    override fun run() {
        val input = inputLines
            .map {
                it.split(",").map { rangeString ->
                    val (start, end) = rangeString.split("-")
                    start.toInt()..end.toInt()
                }
            }.map { (first, second) -> first to second }
        println(computePart1(input))
        println(computePart2(input))
    }

    private fun computePart1(input: List<Pair<IntRange, IntRange>>) =
        input.count { it.first.isContainedIn(it.second) || it.second.isContainedIn(it.first) }

    private fun computePart2(input: List<Pair<IntRange, IntRange>>) =
        input.count { it.first.isOverlappingWith(it.second) }

    private fun IntRange.isContainedIn(other: IntRange): Boolean = other.contains(start) && other.contains(endInclusive)

    private fun IntRange.isOverlappingWith(other: IntRange): Boolean =
        other.isContainedIn(this) || other.contains(start) || other.contains(endInclusive)
}
