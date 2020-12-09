package aoc.y2020

import aoc.Puzzle

fun main() {
    Puzzle9().runAndProfile()
}

class Puzzle9 : Puzzle(2020, 9) {
    override fun run() {
        val numbers = inputLines.map { it.toLong() }
        val invalid = numbers.windowed(26, 1)
            .first { window ->
                tuplePairs(window.dropLast(1))
                    .none { (a, b) -> a != b && a + b == window.last() }
            }.last()
        println(invalid)
        var foundList: List<Long> = listOf()
        outer@for (i in numbers.indices) {
            internal@for (j in (i+1)..numbers.lastIndex) {
                val partialSum = numbers.subList(i, j).sum()
                when {
                    partialSum < invalid -> continue@internal
                    partialSum > invalid -> break@internal
                    else -> {
                        foundList = numbers.subList(i, j)
                        break@outer
                    }
                }
            }
        }
        println(foundList.min()!! + foundList.max()!!)
    }

    private fun tuplePairs(inputList: List<Long>): Sequence<Pair<Long, Long>> = sequence {
        for (i in inputList.indices) {
            for (j in (i + 1)..inputList.lastIndex) {
                yield(inputList[i] to inputList[j])
            }
        }
    }
}