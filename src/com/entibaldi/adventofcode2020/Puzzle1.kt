package com.entibaldi.adventofcode2020

import java.io.File

fun main() {
    val input = File("res/1/input.txt").readLines().map { it.toInt() }
    println(Puzzle1().computePart1(input, 2020))
    println(Puzzle1().computePart2(input))
}

class Puzzle1 {
    fun computePart1(input: List<Int>, target: Int): Int? {
        val expenses = input.toHashSet()
        for (n in expenses) {
            if (expenses.contains(target - n)) {
                return n * (target - n)
            }
        }
        return null
    }

    fun computePart2(input: List<Int>): Any? {
        input.forEachIndexed { i, n ->
            val found = computePart1(input.subList(i, input.size), 2020 - n)
            if (found != null) {
                return found * n
            }
        }
        return null
    }
}