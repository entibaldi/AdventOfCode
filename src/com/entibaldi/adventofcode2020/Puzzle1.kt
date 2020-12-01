package com.entibaldi.adventofcode2020

import java.io.File
import kotlin.system.measureTimeMillis

fun main() {
    val input = File("res/1/input.txt").readLines().map { it.toInt() }
    val puzzle1Time = measureTimeMillis {
        val result1 = Puzzle1().computePart1(input, 2020)
        println("part1 result: $result1")
    }
    println("part1 time: $puzzle1Time")
    val puzzle2Time = measureTimeMillis {
        val result2 = Puzzle1().computePart2(input)
        println("part2 result: $result2")
    }
    println("part2 time: $puzzle2Time")
}

class Puzzle1 {
    fun computePart1(input: List<Int>, target: Int): Int? {
        val expenses = input.toHashSet()
        for (n in expenses) {
            if (n == target - n || expenses.contains(target - n)) {
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