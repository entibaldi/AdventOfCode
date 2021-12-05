package aoc.y2020

import aoc.Puzzle

fun main() {
    Puzzle5().runAndProfile()
}

class Puzzle5 : Puzzle(2020, 5) {
    override fun run() {
        val ids = inputLines.map { getSeatId(it) }
        println("Part1 result: ${ids.maxOrNull()}")
        for (it in ids) {
            if (it + 1 !in ids && it != ids.maxOrNull()) {
                println("part2 result: ${it + 1}")
            }
        }
    }

    fun getSeatId(seat: String): Int =
        getRow(seat.substring(0, 7)) * 8 + getColumn(seat.substring(7, seat.length))

    fun getRow(seat: String): Int = seat
        .replace("F", "0")
        .replace("B", "1")
        .toInt(2)

    fun getColumn(seat: String): Int = seat
        .replace("L", "0")
        .replace("R", "1")
        .toInt(2)
}


