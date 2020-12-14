package aoc.y2015

import aoc.Puzzle

fun main() {
    Puzzle3().runAndProfile()
}

class Puzzle3 : Puzzle(2015, 3) {

    override fun run() {
        part1()
        part2()
    }

    private fun part1() {
        val positions = hashSetOf<Pair<Int, Int>>()
        var lastPosition = 0 to 0
        positions.add(lastPosition)
        inputText.toCharArray().forEach {
            when (it) {
                '>' -> lastPosition = lastPosition.first + 1 to lastPosition.second
                '^' -> lastPosition = lastPosition.first to lastPosition.second - 1
                '<' -> lastPosition = lastPosition.first - 1 to lastPosition.second
                'v' -> lastPosition = lastPosition.first to lastPosition.second + 1
            }
            positions.add(lastPosition)
        }
        println(positions.size)
    }

    private fun part2() {
        val positions = hashSetOf<Pair<Int, Int>>()
        var lastSantaPosition = 0 to 0
        var lastBotPosition = 0 to 0
        positions.add(lastSantaPosition)
        inputText.toCharArray().forEachIndexed { index, c ->
            if (index %2 == 0) {
                when (c) {
                    '>' -> lastSantaPosition = lastSantaPosition.first + 1 to lastSantaPosition.second
                    '^' -> lastSantaPosition = lastSantaPosition.first to lastSantaPosition.second - 1
                    '<' -> lastSantaPosition = lastSantaPosition.first - 1 to lastSantaPosition.second
                    'v' -> lastSantaPosition = lastSantaPosition.first to lastSantaPosition.second + 1
                }
                positions.add(lastSantaPosition)
            } else {
                when (c) {
                    '>' -> lastBotPosition = lastBotPosition.first + 1 to lastBotPosition.second
                    '^' -> lastBotPosition = lastBotPosition.first to lastBotPosition.second - 1
                    '<' -> lastBotPosition = lastBotPosition.first - 1 to lastBotPosition.second
                    'v' -> lastBotPosition = lastBotPosition.first to lastBotPosition.second + 1
                }
                positions.add(lastBotPosition)
            }
        }
        println(positions.size)
    }

}