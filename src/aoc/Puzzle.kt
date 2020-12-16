package aoc

import java.io.File
import kotlin.system.measureTimeMillis

abstract class Puzzle(
    val year: Int,
    val day: Int
) {
    val file: File by lazy { File("res/$year/$day/input.txt") }

    val inputText: String by lazy { file.readText() }

    val inputLines: List<String> by lazy { file.readLines() }

    val inputGroups: List<String> by lazy { inputText.split("\n\n", "\r\n\r\n", "\r\r") }

    fun runAndProfile() {
        val time = measureTimeMillis(this::run)
        println("Puzzle($year,$day) completed in ${time}ms")
    }

    abstract fun run()
}