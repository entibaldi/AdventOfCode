package aoc.y2024

import aoc.Puzzle
import java.lang.RuntimeException
import kotlin.math.abs

fun main() {
    Puzzle2().runAndProfile()
}

class Puzzle2 : Puzzle(2024, 2) {

    override fun run() {
        val reports = inputLines.map { line -> line.split(" ").map { id -> id.toInt() } }
        println(reports.count { isSafeReport(it) })
        println(reports.count { isSafeReport(it, hasDampener = true) })
        for (r in reports) {
            println("$r -> isSafe: ${isSafeReport(r, hasDampener = true)}")
        }
    }

    private fun isSafeReport(report: List<Int>, hasDampener: Boolean = false): Boolean {
        var previous: Int = -1
        var dir = 0
        val safeRange = 1..3
        var passed = true
        for (current in report) {
            if (dir > 0 && current < previous) {
                passed = false
                break
            }
            if (dir < 0 && current > previous) {
                passed = false
                break
            }
            if (previous >= 0 && abs(current - previous) !in safeRange) {
                passed = false
                break
            }
            if (dir == 0 && previous >= 0) dir = current - previous
            previous = current
        }
        if (!passed && hasDampener) {
            passed = report.indices.map { i ->
                isSafeReport(report.subList(0, i) + report.subList(i + 1, report.size))
            }.any { it }
        }
        return passed
    }

}