package com.entibaldi.adventofcode2020

import java.io.File
import java.util.*

fun main() {
    var patternLength = 0
    val fileLines = File("res/4/input.txt")
        .readLines()
    val maxY = fileLines.count()
    val inputMap: HashSet<Pair<Int, Int>> = fileLines
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

class SlopeCalculator(
    val map: HashSet<Pair<Int, Int>>,
    val patternX: Int,
    val maxY: Int
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