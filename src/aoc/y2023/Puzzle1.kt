package aoc.y2023

import aoc.Puzzle
import java.lang.RuntimeException

fun main() {
    Puzzle1().runAndProfile()
}

class Puzzle1 : Puzzle(2023, 1) {

    override fun run() {
        val digits = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        println(inputLines.sumOf { calibrationValue(it) })
        println(inputLines.sumOf { line ->
            val s = buildString {
                for (i in line.indices) {
                    if (line[i].isDigit()) {
                        append(line[i])
                    } else {
                        for (j in digits.indices) {
                            if (line.substring(i).startsWith(digits[j])) append((j + 1).digitToChar())
                        }
                    }
                }
            }
            calibrationValue(s)
        })
    }

    private fun calibrationValue(line: String): Int {
        val digits = line.toCharArray().filter { it.isDigit() }
        return digits.first().digitToInt() * 10 + digits.last().digitToInt()
    }
}