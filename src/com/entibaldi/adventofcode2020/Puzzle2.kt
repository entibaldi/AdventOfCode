package com.entibaldi.adventofcode2020

import java.io.File

private val lineRegex = Regex("^(\\d+)-(\\d+) (.): (.+)\$")

private data class PwdRecord(
        val minCount: Int,
        val maxCount: Int,
        val c: Char,
        val password: String
)

fun main() {
    val input = File("res/2/input.txt").readLines().map {
        val result: List<String> = lineRegex.matchEntire(it.trim())!!.groupValues
        PwdRecord(result[1].toInt(), result[2].toInt(), result[3].toCharArray()[0], result[4])
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