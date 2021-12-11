package aoc.y2021

import aoc.Puzzle

fun main() {
    Puzzle10().runAndProfile()
}

class Puzzle10 : Puzzle(2021, 10) {

    private val roundIllegalScore = 3
    private val squareIllegalScore = 57
    private val curlyIllegalScore = 1197
    private val angleIllegalScore = 25137

    override fun run() {
        println(inputLines.sumOf { getIllegalScore(it) })
        val completionScores = inputLines.filter { getIllegalScore(it) == 0 }
            .map { getCompletionScore(it) }
            .sorted()
        println(completionScores[completionScores.size / 2])
    }

    private fun getIllegalScore(line: String): Int {
        val queue = ArrayDeque<Int>()
        line.asSequence().forEach {
            when (it) {
                '(' -> queue.add(1)
                '[' -> queue.add(2)
                '{' -> queue.add(3)
                '<' -> queue.add(4)
                ')' -> if (queue.isEmpty() || queue.removeLast() != 1) return roundIllegalScore
                ']' -> if (queue.isEmpty() || queue.removeLast() != 2) return squareIllegalScore
                '}' -> if (queue.isEmpty() || queue.removeLast() != 3) return curlyIllegalScore
                '>' -> if (queue.isEmpty() || queue.removeLast() != 4) return angleIllegalScore
            }
        }
        return 0
    }

    private fun getCompletionScore(line: String): Long {
        val queue = ArrayDeque<Int>()
        line.asSequence().forEach {
            when (it) {
                '(' -> queue.add(1)
                '[' -> queue.add(2)
                '{' -> queue.add(3)
                '<' -> queue.add(4)
                ')' -> require( queue.removeLast() == 1) { "Illegal line" }
                ']' -> require( queue.removeLast() == 2) { "Illegal line" }
                '}' -> require( queue.removeLast() == 3) { "Illegal line" }
                '>' -> require( queue.removeLast() == 4) { "Illegal line" }
            }
        }
        return queue.reversed().fold(0L) { acc: Long, v: Int -> acc * 5 + v }
    }
}