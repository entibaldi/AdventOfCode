package aoc.y2021

import aoc.Puzzle
import java.util.concurrent.atomic.LongAdder
import kotlin.math.max
import kotlin.math.min

fun main() {
    Puzzle5().runAndProfile()
}

class Puzzle5 : Puzzle(2021, 5) {

    override fun run() {
        val input = inputLines.map { line ->
            val l = line.split(" -> ")
                .map { coords -> coords.split(",").let { it[0].toInt() to it[1].toInt() } }
            l[0] to l[1]
        }
        println(countDangers(input, false))
        println(countDangers(input, true))
    }

    private fun countDangers(input: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>, countDiagonals: Boolean): Int {
        val dangers = mutableMapOf<Pair<Int, Int>, LongAdder>()
        input.stream().forEach { v ->
            if (v.first.first == v.second.first) { // same x
                for (i in min(v.first.second, v.second.second)..max(v.first.second, v.second.second)) {
                    dangers.computeIfAbsent(v.first.first to i) { LongAdder() }.increment()
                }
            } else if (v.first.second == v.second.second) { // same y
                for (i in min(v.first.first, v.second.first)..max(v.first.first, v.second.first)) {
                    dangers.computeIfAbsent(i to v.second.second) { LongAdder() }.increment()
                }
            } else if (countDiagonals) {
                val xDir = if (v.first.first < v.second.first) 1 else -1
                val yDir = if (v.first.second < v.second.second) 1 else -1
                for (i in 0..max(v.first.second, v.second.second) - min(v.first.second, v.second.second)) {
                    val x = (v.first.first + xDir * i)
                    val y = (v.first.second + yDir * i)
                    dangers.computeIfAbsent(x to y) { LongAdder() }.increment()
                }
            }
        }
        return dangers.count { (_, c) -> c.sum() >= 2 }
    }
}