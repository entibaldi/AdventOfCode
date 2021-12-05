package aoc.y2021

import aoc.Puzzle

fun main() {
    Puzzle2().runAndProfile()
}

class Puzzle2 : Puzzle(2021, 2) {

    override fun run() {
        val input = inputLines
            .map { it.split(" ") }
            .map { it[0] to it[1].toInt() }
        println(computePart1(input))
        println(computePart2(input))
    }

    private fun computePart1(input: List<Pair<String, Int>>): Int {
        var x = 0 // horizontal position
        var y = 0 // depth
        for ((direction, distance) in input) {
            when (direction) {
                "forward" -> x += distance
                "down" -> y += distance
                "up" -> y -= distance
            }
        }
        return x * y
    }

    private fun computePart2(input: List<Pair<String, Int>>): Int {
        var x = 0 // horizontal position
        var y = 0 // depth
        var a = 0 // aim
        for ((direction, distance) in input) {
            when (direction) {
                "forward" -> {
                    x += distance
                    y += a * distance
                }
                "down" -> a += distance
                "up" -> a -= distance
            }
        }
        return x * y
    }
}