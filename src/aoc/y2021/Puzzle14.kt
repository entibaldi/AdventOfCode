package aoc.y2021

import aoc.Puzzle

fun main() {
    Puzzle14().runAndProfile()
}

class Puzzle14 : Puzzle(2021, 14) {

    private val rules = inputGroups[1].lines().map { line ->
        line.split(" -> ").let {
            it[0] to listOf(it[0][0] + it[1], it[1] + it[0][1])
        }
    }

    override fun run() {
        println(iteratePolymer(10))
        println(iteratePolymer(40))
    }

    private fun iteratePolymer(times: Int): Long {
        val polymerCounts = inputLines.first().windowed(2, 1).groupBy { it }
            .mapValues { it.value.size.toLong() }.toSortedMap()
        val letterCount =
            inputLines.first().toCharArray().groupBy { it }.mapValues { it.value.size.toLong() }.toMutableMap()
        repeat(times) {
            val tempPolymerCount = mutableMapOf<String, Long>()
            val tempLetterCount = mutableMapOf<Char, Long>()
            rules.stream()
                .filter { (match, _) -> polymerCounts.getOrZero(match) > 0L }
                .forEach { (match, insertions) ->
                    val matchCount = polymerCounts[match]!!
                    for (insertion in insertions) {
                        tempPolymerCount[insertion] = tempPolymerCount.getOrZero(insertion) + matchCount
                    }
                    tempLetterCount[insertions[0][1]] = tempLetterCount.getOrZero(insertions[0][1]) + matchCount
                    tempPolymerCount[match] = tempPolymerCount.getOrZero(match) - matchCount
                }
            for ((match, count) in tempPolymerCount) {
                polymerCounts[match] = (polymerCounts[match] ?: 0L) + count
            }
            for ((letter, count) in tempLetterCount) {
                letterCount[letter] = letterCount.getOrZero(letter) + count
            }
        }
        return letterCount.maxByOrNull { it.value }!!.value - letterCount.minByOrNull { it.value }!!.value
    }

    private fun <K> Map<K, Long>.getOrZero(key: K): Long = get(key) ?: 0L
}


