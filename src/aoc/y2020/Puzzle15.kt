package aoc.y2020

import aoc.Puzzle

fun main() {
    Puzzle15().runAndProfile()
}

class Puzzle15 : Puzzle(2020, 15) {

    override fun run() {
        exec(2020)
        exec(30000000)
    }

    private fun exec(until: Int) {
        val input = listOf(0,14,6,20,1,4)

        val numbers: MutableMap<Int, IntArray> = input.withIndex()
            .map { (i, n) -> n to intArrayOf(i, -1) }
            .toMap().toMutableMap()

        var lastEntry = input.last()
        for (i in input.size until until) {
            val foundLastEntry = numbers[lastEntry]!!
            val newEntry = if (foundLastEntry[1] < 0) 0 else i - 1 - foundLastEntry[1]
            val foundCurrentEntry = numbers.getOrPut(newEntry) { intArrayOf(-1, -1) }
            foundCurrentEntry[1] = foundCurrentEntry[0]
            foundCurrentEntry[0] = i
            lastEntry = newEntry
        }
        println("Final: $lastEntry")
    }
}