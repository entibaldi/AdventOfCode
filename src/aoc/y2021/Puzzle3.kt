package aoc.y2021

import aoc.Puzzle
import java.util.concurrent.atomic.LongAdder

fun main() {
    Puzzle3().runAndProfile()
}

class Puzzle3 : Puzzle(2021, 3) {

    override fun run() {
        val input = inputLines.map { it.toCharArray() }
        println(computePart1(input))
        println(computePart2(input))
    }

    private fun computePart1(input: List<CharArray>): Int {
        val positiveCounts = mutableMapOf<Int, LongAdder>()
        input.stream()
            .forEach { chars ->
                chars.forEachIndexed { i, c ->
                    if (c == '1') positiveCounts.computeIfAbsent(i) { LongAdder() }.increment()
                }
            }
        val gamma = positiveCounts.entries
            .sortedBy { it.key }
            .map { (_, count) -> if (count.sum() > (input.size / 2)) '1' else '0' }
            .toCharArray().concatToString()
            .toInt(2)
        return gamma * (gamma xor 0xfff)
    }

    private fun computePart2(input: List<CharArray>): Int {
        val oxygenGeneratorRating = findRating(0, input, true).concatToString().toInt(2)
        val co2ScrubberRating = findRating(0, input, false).concatToString().toInt(2)
        return oxygenGeneratorRating * co2ScrubberRating
    }

    private fun findRating(index: Int, input: List<CharArray>, mostCommon: Boolean): CharArray {
        val keep1 = if (mostCommon) {
            input.count { it[index] == '1' } >= input.size / 2f
        } else {
            input.count { it[index] == '1' } < input.size / 2f
        }
        val filtered = input.filter {
            if (keep1) it[index] == '1' else it[index] == '0'
        }
        return if (filtered.size == 1) return filtered.single() else findRating(index + 1, filtered, mostCommon)
    }
}