package aoc.y2022

import aoc.Puzzle

fun main() {
    Puzzle10().runAndProfile()
}

class Puzzle10 : Puzzle(2022, 10) {

    override fun run() {
        val input = inputLines
        var cycle = 0
        var x = 1
        val strength = mutableListOf<Int>()
        var print = ""

        fun incrementCycle() {
            print += if (cycle % 40 in x - 1..x + 1) "#" else "."
            cycle++
            if (cycle == 20 || (cycle + 20) % 40 == 0) {
                strength += cycle * x
            }
            if (cycle % 40 == 0) {
                print += "\n"
            }
        }

        for (line in input) {
            incrementCycle()
            if (line.startsWith("addx")) {
                incrementCycle()
                x += line.split(" ")[1].toInt()
            }
        }
        println(strength.sum())
        println(print)
    }
}
