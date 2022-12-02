package aoc.y2022

import aoc.Puzzle

fun main() {
    Puzzle2().runAndProfile()
}

class Puzzle2 : Puzzle(2022, 2) {

    override fun run() {
        println(computePart1())
        println(computePart2())
    }

    private fun computePart1() = inputLines.map { it.parseRoundAsPart1() }.sumOf { it.score }

    private fun computePart2() = inputLines.map { it.parseRoundAsPart2() }.sumOf { it.score }

    private fun String.parseRoundAsPart1(): Round = split(" ")
        .let { (c1, c2) ->
            val opponentHand = when (c1) {
                "A" -> Hand.ROCK
                "B" -> Hand.PAPER
                "C" -> Hand.SCISSORS
                else -> throw IllegalArgumentException("Wrong format for $c1")
            }
            val myHand = when (c2) {
                "X" -> Hand.ROCK
                "Y" -> Hand.PAPER
                "Z" -> Hand.SCISSORS
                else -> throw IllegalArgumentException("Wrong format for $c1")
            }
            Round(opponentHand, myHand)
        }

    private fun String.parseRoundAsPart2(): Round = split(" ")
        .let { (c1, c2) ->
            val opponentHand = when (c1) {
                "A" -> Hand.ROCK
                "B" -> Hand.PAPER
                "C" -> Hand.SCISSORS
                else -> throw IllegalArgumentException("Wrong format for $c1")
            }
            val myHand = when (c2) {
                "X" -> when (opponentHand) {
                    Hand.ROCK -> Hand.SCISSORS
                    Hand.PAPER -> Hand.ROCK
                    Hand.SCISSORS -> Hand.PAPER
                }
                "Y" -> opponentHand
                "Z" -> when (opponentHand) {
                    Hand.ROCK -> Hand.PAPER
                    Hand.PAPER -> Hand.SCISSORS
                    Hand.SCISSORS -> Hand.ROCK
                }
                else -> throw IllegalArgumentException("Wrong format for $c1")
            }
            Round(opponentHand, myHand)
        }

    data class Round(val opponentHand: Hand, val myHand: Hand) {
        val score = myHand.scoreAgainst(opponentHand) + myHand.value
    }

    enum class Hand(val value: Int) {
        ROCK(1), PAPER(2), SCISSORS(3);

        fun scoreAgainst(other: Hand) = when (other) {
            ROCK -> when (this) {
                ROCK -> 3
                PAPER -> 6
                SCISSORS -> 0
            }
            PAPER -> when (this) {
                ROCK -> 0
                PAPER -> 3
                SCISSORS -> 6
            }
            SCISSORS -> when (this) {
                ROCK -> 6
                PAPER -> 0
                SCISSORS -> 3
            }
        }
    }
}