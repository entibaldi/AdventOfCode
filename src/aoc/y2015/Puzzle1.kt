package aoc.y2015

import aoc.Puzzle

fun main() {
    Puzzle1().runAndProfile()
}

class Puzzle1 : Puzzle(2015, 1) {

    override fun run() {
        val input = inputText
        part1(input)
        part2(input)
    }

    private fun part1(input: String) {
        println(input.chars().map { c -> if (c == '('.toInt()) 1 else -1 }.sum())
    }

    private fun part2(input: String) {
        var floor = 0
        for ((i, c) in input.toCharArray().withIndex()) {
            if (c == '(') floor += 1 else floor -= 1
            if (floor == -1) {
                println(i + 1)
                return
            }
        }
    }
}