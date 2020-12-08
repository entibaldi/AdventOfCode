package aoc.y2020

import aoc.Puzzle

fun main() {
    Puzzle1().runAndProfile()
}

class Puzzle1 : Puzzle(2020, 1) {

    override fun run() {
        val input = inputLines.map { it.toInt() }
        println(computePart1(input, 2020))
        println(computePart2(input))
    }

    private fun computePart1(input: List<Int>, target: Int): Int? {
        val expenses = input.fold(mutableMapOf<Int, Int>()) { map, i ->
            map[i] = map.getOrDefault(i, 0) + 1
            map
        }
        for ((cost, amount) in expenses) {
            if ((cost == target - cost && amount >= 2) || (expenses.contains(target - cost))) {
                return cost * (target - cost)
            }
        }
        return null
    }

    private fun computePart2(input: List<Int>): Any? {
        input.forEachIndexed { i, n ->
            val found = computePart1(input.subList(i, input.size), 2020 - n)
            if (found != null) {
                return found * n
            }
        }
        return null
    }
}