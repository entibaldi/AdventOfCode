package aoc.y2021

import aoc.Puzzle

fun main() {
    Puzzle8().runAndProfile()
}

class Puzzle8 : Puzzle(2021, 8) {

    data class InputLine(
        val signalPatterns: List<Set<Char>>,
        val outputValues: List<Set<Char>>
    )

    override fun run() {
        val input: List<InputLine> = inputLines.map { line ->
            InputLine(
                line.split(" | ")[0].trim().split(" ").map { it.toCharArray().toSet() },
                line.split(" | ")[1].trim().split(" ").map { it.toCharArray().toSet() },
            )
        }
        println(part1(input))
        println(part2(input))
    }

    private fun part1(input: List<InputLine>): Int {
        return input.sumOf {
            it.outputValues.count { s -> s.size == 2 || s.size == 4 || s.size == 3 || s.size == 7 }
        }
    }

    val digits = mapOf(0 to "abcefg", 1 to "cf", 2 to "acdeg", 3 to "acdfg", 4 to "bcdf", 5 to "abdfg", 6 to "abdefg",
        7 to "acf", 8 to "abcdefg", 9 to "abcdfg").mapValues { (_, v) -> v.toCharArray().toSet() }

    private val possibleDigitsByCount: Map<Int, Set<Int>> = mapOf(
        2 to setOf(1), 3 to setOf(7), 4 to setOf(4), 7 to setOf(8),
        5 to setOf(2, 3, 5), 6 to setOf(0, 6, 9),
    )

    private fun part2(input: List<InputLine>): Int = input.sumOf { line ->
        val pairMaps = mutableMapOf<Set<Char>, MutableSet<Int>>().apply {
            line.signalPatterns.forEach { computeIfAbsent(it) { mutableSetOf() }.addAll(possibleDigitsByCount[it.size]!!) }
        }
        while (pairMaps.values.any { it.size > 1 }) {
            pairMaps.filterValues { it.size > 1 }.forEach { (chars, possibleValues) ->
                pairMaps.filterValues { it.size == 1 }.forEach { (otherChars, valueSingle) ->
                    possibleValues.removeIf { intValue ->
                        val commonDigits = digits[intValue]!!.intersect(digits[valueSingle.single()]!!).size
                        commonDigits != chars.intersect(otherChars).size
                    }
                }
            }
        }
        line.outputValues.map { pairMaps[it]!!.first().digitToChar() }.toCharArray().concatToString().toInt()
    }
}
