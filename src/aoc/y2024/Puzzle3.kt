package aoc.y2024

import aoc.Puzzle
import java.lang.RuntimeException
import kotlin.math.abs

fun main() {
    Puzzle3().runAndProfile()
}

class Puzzle3 : Puzzle(2024, 3) {

    override fun run() {
        part1()
        part2()
    }

    private fun part1() {
        println("""mul\((\d+),(\d+)\)""".toRegex().findAll(inputText).sumOf { match ->
            match.groupValues[1].toInt() * match.groupValues[2].toInt()
        })
    }

    private fun part2() {
        val matches = """(mul\((\d+),(\d+)\))|(do\(\))|(don't\(\))""".toRegex()
            .findAll(inputText)
        var mulEnabled = true
        var result = 0
        for (match in matches) {
            when {
                match.groupValues[1].isNotEmpty() && mulEnabled -> {
                    result += match.groupValues[2].toInt() * match.groupValues[3].toInt()
                }
                match.groupValues[4].isNotEmpty() -> {
                    mulEnabled = true
                }
                match.groupValues[5].isNotEmpty() -> {
                    mulEnabled = false
                }
            }
        }
        println(result)
    }
}