package aoc.y2020

import aoc.Puzzle

fun main() {
    Puzzle25().runAndProfile()
}

class Puzzle25 : Puzzle(2020, 25) {

    override fun run() {
        val cardLoops = decodeLoops(CARD_PUB)
        println(encode(DOOR_PUB, cardLoops))
    }

    private fun decodeLoops(target: Long): Int {
        var loop = 0
        var value = 1L
        while (value != target) {
            loop++
            value = (value * 7) % 20201227
        }
        return loop
    }

    private fun encode(input: Long, loops: Int): Long {
        var value = 1L
        repeat(loops) {
            value = (value * input) % 20201227
        }
        return value
    }

    companion object {
        private const val CARD_PUB: Long = 15335876
        private const val DOOR_PUB: Long = 15086442
    }
}