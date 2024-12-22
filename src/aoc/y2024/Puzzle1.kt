package aoc.y2024

import aoc.Puzzle
import java.lang.RuntimeException
import kotlin.math.abs

fun main() {
    Puzzle1().runAndProfile()
}

class Puzzle1 : Puzzle(2024, 1) {

    override fun run() {
        val ids = inputLines.map { line -> line.split("   ").map { id -> id.toInt() } }
        val group1 = ids.map { it.first() }.sorted()
        val group2 = ids.map { it.last() }.sorted()
        println(group1.withIndex().sumOf { (i, id) -> abs(group2[i] - id) })

        val group1Set = group1.toSet()
        val occurrences = mutableMapOf<Int, Int>()
        for (n in group2) {
            if (n in group1Set) {
                occurrences.compute(n) { _, v -> (v ?: 0) + 1 }
            }
        }
        println(group1.sumOf { it * (occurrences[it] ?: 0) })
    }

}