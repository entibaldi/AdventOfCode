package aoc.y2020

import aoc.Puzzle

fun main() {
    Puzzle11().runAndProfile()
}

class Puzzle11 : Puzzle(2020, 11) {
    override fun run() {
        val inputMap: Array<IntArray> = inputLines
            .map { it.toCharArray().map { c -> if (c == 'L') 1 else 0 }.toIntArray() }
            .toTypedArray()
        part1(inputMap)
        part2(inputMap)
    }

    private fun part1(inputMap: Array<IntArray>) {
        var seatMap = inputMap
        var newSeatMap = iterateSeatsPart1(seatMap)
        while (newSeatMap.withIndex().any { (i, row) -> !row.contentEquals(seatMap[i]) }) {
            seatMap = newSeatMap
            newSeatMap = iterateSeatsPart1(seatMap)
        }
        println(newSeatMap.sumBy { row -> row.count { it == 2 } })
    }

    private fun part2(inputMap: Array<IntArray>) {
        var seatMap = inputMap
        var newSeatMap = iterateSeatsPart2(seatMap)
        while (newSeatMap.withIndex().any { (i, row) -> !row.contentEquals(seatMap[i]) }) {
            seatMap = newSeatMap
            newSeatMap = iterateSeatsPart2(seatMap)
        }
        println(newSeatMap.sumBy { row -> row.count { it == 2 } })
    }

    private fun iterateSeatsPart1(seatMap: Array<IntArray>): Array<IntArray> {
        val newMap = Array(seatMap.size) { i -> seatMap[i].copyOf() }
        for ((i, row) in seatMap.withIndex()) {
            for ((j, v) in row.withIndex()) {
                val adjacentOccupied = listOfNotNull(
                    seatMap.getOrNull(i-1)?.getOrNull(j-1),
                    seatMap.getOrNull(i-1)?.getOrNull(j),
                    seatMap.getOrNull(i-1)?.getOrNull(j+1),
                    seatMap.getOrNull(i)?.getOrNull(j-1),
                    seatMap.getOrNull(i)?.getOrNull(j+1),
                    seatMap.getOrNull(i+1)?.getOrNull(j-1),
                    seatMap.getOrNull(i+1)?.getOrNull(j),
                    seatMap.getOrNull(i+1)?.getOrNull(j+1)
                ).count { it == 2 }
                if (v == 1 && adjacentOccupied == 0) {
                    newMap[i][j] = 2
                } else if (v == 2 && adjacentOccupied >= 4) {
                    newMap[i][j] = 1
                }
            }
        }
        return newMap
    }

    private fun iterateSeatsPart2(seatMap: Array<IntArray>): Array<IntArray> {
        val rowCount = seatMap.size
        val colCount = seatMap[0].size

        val seatMapByColumn: Array<IntArray> = Array(colCount) { col ->
            seatMap.map { it[col] }.toIntArray()
        }
        val bottomLeftToTopRightDiagonals = mutableListOf<IntArray>()
        val topLeftToBottomRightDiagonals = mutableListOf<IntArray>()
        for (k in 0..rowCount + colCount - 2) {
            bottomLeftToTopRightDiagonals += (0..k)
                .map { j ->
                    val i = k - j
                    if (i < rowCount && j < colCount) seatMap[i][j] else 0
                }.toIntArray()
            topLeftToBottomRightDiagonals += (0 until colCount)
                .map { j ->
                    val i = j - colCount + 1 + k
                    if (i in 0 until rowCount && j < colCount) seatMap[i][j] else 0
                }.toIntArray()
        }

        val newMap = Array(rowCount) { i -> seatMap[i].copyOf() }
        for ((i, row) in seatMap.withIndex()) {
            for ((j, v) in row.withIndex()) {
                val adjacentOccupied = calcOccupied(seatMap[i], j) +
                    calcOccupied(seatMapByColumn[j], i) +
                    calcOccupied(bottomLeftToTopRightDiagonals[i+j], j) +
                    calcOccupied(topLeftToBottomRightDiagonals[colCount - 1 - (j-i)], j)
                if (v == 1 && adjacentOccupied == 0) {
                    newMap[i][j] = 2
                } else if (v == 2 && adjacentOccupied >= 5) {
                    newMap[i][j] = 1
                }
            }
        }
        return newMap
    }

    private fun calcOccupied(seats: IntArray, index: Int): Int {
        if (seats.isEmpty()) throw IllegalArgumentException("array at index $index is empty")
        var occupied = 0
        for (i in (index+1) until seats.size) {
            if (seats[i] == 1) {
                break
            } else if (seats[i] == 2) {
                occupied++
                break
            }
        }
        for (i in (index-1) downTo 0 ) {
            if (seats[i] == 1) {
                break
            } else if (seats[i] == 2) {
                occupied++
                break
            }
        }
        return occupied
    }
}