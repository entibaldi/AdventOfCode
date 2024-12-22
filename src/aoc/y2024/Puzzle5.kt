package aoc.y2024

import aoc.Puzzle

fun main() {
    Puzzle5().runAndProfile()
}

class Puzzle5 : Puzzle(2024, 5) {

    val precedents = mutableMapOf<Int, MutableSet<Int>>()

    override fun run() {
        inputGroups[0].lines().forEach {
            val (n, beforeN) = it.split("|").map { s -> s.toInt() }
            precedents.computeIfAbsent(n) { mutableSetOf() }.add(beforeN)
        }
        part1()
        part2()
    }

    private fun part1() {
       println(inputGroups[1].lines()
           .map { it.split(",").map { s -> s.toInt() } }
           .filter { findFaultIndexes(it) == null }
           .sumOf { it[it.size / 2] })
    }

    private fun part2() {
        println(inputGroups[1].lines()
            .map { it.split(",").map { s -> s.toInt() } }
            .filter { findFaultIndexes(it) != null }
            .map { fixOrder(it) }
            .sumOf { it[it.size / 2] })
    }

    private fun fixOrder(pages: List<Int>): List<Int> {
        val fixedPages = pages.toMutableList()
        var faultIndexes: Pair<Int, Int>?
        do {
            faultIndexes = findFaultIndexes(fixedPages)?.also { (i, j) ->
                fixedPages.add(i, fixedPages.removeAt(j))
            }
        } while (faultIndexes != null)
        return fixedPages
    }

    private fun findFaultIndexes(pages: List<Int>): Pair<Int, Int>? {
        if (pages.size == 1) return null
        for (i in 1..pages.lastIndex) {
            for (j in 0 until i) {
                if (precedents.getOrDefault(pages[i], emptySet()).contains(pages[j])) return i to j
            }
        }
        return null
    }
}
