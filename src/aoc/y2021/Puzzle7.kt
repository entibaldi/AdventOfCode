package aoc.y2021

import aoc.Puzzle
import kotlin.math.abs

fun main() {
    Puzzle7().runAndProfile()
}

class Puzzle7 : Puzzle(2021, 7) {

    override fun run() {
        val input: List<Int> = inputText.split(",").map { it.toInt() }
        println(findOptimalDistance(input, false))
        println(findOptimalDistance(input, true))
    }

    private fun findOptimalDistance(input: List<Int>, incrementalSteps: Boolean): Pair<Int, Int> {
        var minSum = Int.MAX_VALUE
        var minValue = Int.MAX_VALUE
        for (v in input.minOrNull()!!..input.maxOrNull()!!) {
            val s = input.sumOf { value -> abs(value - v).let { if (incrementalSteps) it * (it + 1) / 2 else it } }
            if (minSum >= s) {
                minSum = s
                minValue = v
            }
        }
        return minValue to minSum
    }
}
