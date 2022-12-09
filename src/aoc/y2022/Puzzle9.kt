package aoc.y2022

import aoc.Puzzle
import kotlin.math.abs
import kotlin.math.sign

fun main() {
    Puzzle9().runAndProfile()
}

class Puzzle9 : Puzzle(2022, 9) {

    override fun run() {
        val moves = inputLines.map {
            val (direction, amount) = it.split(" ")
            direction.first() to amount.toInt()
        }
        println(solveRopeTailPositions(moves, 2))
        println(solveRopeTailPositions(moves, 10))
    }

    private fun solveRopeTailPositions(moves: List<Pair<Char, Int>>, tailLength: Int): Int {
        val tailVisited = mutableSetOf(0.0 to 0.0)
        val x = DoubleArray(tailLength) { 0.0 }
        val y = DoubleArray(tailLength) { 0.0 }
        for ((direction, amount) in moves) {
            repeat(amount) {
                when (direction) {
                    'R' -> x[0]++
                    'U' -> y[0]--
                    'L' -> x[0]--
                    'D' -> y[0]++
                }
                for (i in 1..x.lastIndex) {
                    if (abs(x[i-1] - x[i]) > 1 || abs(y[i-1] - y[i]) > 1) {
                        x[i] += sign(x[i-1] - x[i])
                        y[i] += sign(y[i-1] - y[i])
                        if (i == x.lastIndex) tailVisited += x[i] to y[i]
                    }
                }
            }
        }
        return tailVisited.size
    }
}
