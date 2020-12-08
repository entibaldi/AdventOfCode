package aoc.y2020

import aoc.Puzzle
import java.io.File
import java.util.*

fun main() {
    Puzzle3().runAndProfile()
}

class Puzzle3 : Puzzle(2020, 3) {
    override fun run() {
        var patternLength = 0
        val maxY = inputLines.count()
        val inputMap: HashSet<Pair<Int, Int>> = inputLines
            .mapIndexed { y, string ->
                if (patternLength == 0) patternLength = string.length
                string.toCharArray().mapIndexed { x, c ->
                    if (c == '#') x to y else null
                }
            }
            .flatten()
            .filterNotNull()
            .toHashSet()
        val calculator = SlopeCalculator(inputMap, patternLength, maxY)
        val hitPart1 = calculator.hitTrees(3, 1)
        println("part1 trees: $hitPart1")

        val part2 = listOf(
            calculator.hitTrees(1, 1),
            hitPart1,
            calculator.hitTrees(5, 1),
            calculator.hitTrees(7, 1),
            calculator.hitTrees(1, 2)
        ).fold(1L) { acc, i -> acc * i }

        println("part2: $part2")
    }
}


class SlopeCalculator(
    private val map: HashSet<Pair<Int, Int>>,
    private val patternX: Int,
    private val maxY: Int
) {
    fun hitTrees(stepX: Int, stepY: Int): Int {
        var x = 0
        var y = 0
        var count = 0
        while (y < maxY) {
            if (map.contains(x to y)) count++
            x = (x + stepX) % patternX
            y += stepY
        }
        return count
    }
}