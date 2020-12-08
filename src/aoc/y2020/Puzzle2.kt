package aoc.y2020

import aoc.Puzzle

fun main() {
    Puzzle2().runAndProfile()
}

private data class PwdRecord(
    val minCount: Int,
    val maxCount: Int,
    val c: Char,
    val password: String
)

class Puzzle2 : Puzzle(2020, 2) {

    private val lineRegex = Regex("^(\\d+)-(\\d+) (.): (.+)\$")

    override fun run() {
        val input = inputLines.map {
            val result: List<String> = lineRegex.matchEntire(it.trim())!!.groupValues
            PwdRecord(result[1].toInt(), result[2].toInt(), result[3].single(), result[4])
        }
        val countValidPart1 = input.count {
            val c = it.c
            val min = it.minCount
            val max = it.maxCount
            val pwdMatchRegex = Regex("^([^$c]*$c){$min,$max}[^$c]*\$")
            pwdMatchRegex.matches(it.password)
        }
        println("Valid passwords part1: $countValidPart1")
        val countValidPart2 = input.count {
            val chars = it.password.toCharArray()
            (chars[it.minCount - 1] == it.c) != (chars[it.maxCount - 1] == it.c)
        }
        println("Valid passwords part2: $countValidPart2")
    }
}

