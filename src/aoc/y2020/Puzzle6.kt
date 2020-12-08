package aoc.y2020

import aoc.Puzzle

fun main() {
    Puzzle6().runAndProfile()
}

class Puzzle6 : Puzzle(2020, 6) {
    override fun run() {
        val resultPart1 = inputGroups.map { it.replace("\n", "").chars().distinct().count() }.sum()
        val resultPart2 = inputGroups.map { answers ->
            answers.split("\n")
                .map { it.trim().toCharArray().toSet() }
                .reduce { acc, hashSet -> acc.intersect(hashSet) }
                .size
        }.sum()
        println("Part1: $resultPart1")
        println("Part2: $resultPart2")
    }
}


