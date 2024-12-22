package aoc.y2023

import aoc.Puzzle
import java.lang.RuntimeException

fun main() {
    Puzzle2().runAndProfile()
}

class Puzzle2 : Puzzle(2023, 2) {

    override fun run() {
        println(inputLines.filter { isPossibleGame(it.split(": ")[1]) }
            .sumOf { it.split(":")[0].split(" ")[1].toInt() })
        println(inputLines.sumOf { powerOfMinimumSet(it.split(": ")[1]) })
    }

    private fun isPossibleGame(gameData: String): Boolean {
        return gameData.split("; ").all { isPossibleRound(it) }
    }

    private fun isPossibleRound(roundData: String): Boolean {
        val data = roundData.split(", ").associate { s ->
            s.split(" ")[1] to s.split(" ")[0].toInt()
        }
        return data.getOrDefault("red", 0) <= 12 &&
                data.getOrDefault("green", 0) <= 13 &&
                data.getOrDefault("blue", 0) <= 14
    }

    private val redRegex = "(\\d+)\\sred".toRegex()
    private val greenRegex = "(\\d+)\\sgreen".toRegex()
    private val blueRegex = "(\\d+)\\sblue".toRegex()

    private fun powerOfMinimumSet(gameData: String): Int =
        redRegex.findMinimumSet(gameData) *
                greenRegex.findMinimumSet(gameData) *
                blueRegex.findMinimumSet(gameData)

    private fun Regex.findMinimumSet(gameData: String): Int =
        findAll(gameData).maxOfOrNull { it.groupValues[1].toInt() } ?: 0
}