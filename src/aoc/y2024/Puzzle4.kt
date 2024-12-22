package aoc.y2024

import aoc.Puzzle

fun main() {
    Puzzle4().runAndProfile()
}

class Puzzle4 : Puzzle(2024, 4) {

    val dirs = listOf(
        0 to 1, // right
        1 to 1, // down-right
        1 to 0, // down
        1 to -1, // down-left
        0 to -1, // left
        -1 to -1, // up-left
        -1 to 0, // up
        -1 to 1, // up-right
    )

    override fun run() {
        part1()
        part2()
    }

    private fun part1() {
        val map = inputLines.map { it.toCharArray() }
        var count = 0
        for (row in 0..map.lastIndex) {
            for (col in 0..map[0].lastIndex) {
                if (map[row][col] == 'X') {
                    for (dir in dirs) {
                        count += map.scanXmas(dir, row, col)
                    }
                }
            }
        }
        println(count)
    }

    private fun part2() {
        val map = inputLines.map { it.toCharArray() }
        var count = 0
        for (row in 0..map.lastIndex) {
            for (col in 0..map[0].lastIndex) {
                if (map[row][col] == 'A') {
                    count += map.scanX_Mas(row, col)
                }
            }
        }
        println(count)
    }

    private fun List<CharArray>.scanXmas(
        dir: Pair<Int, Int>,
        row: Int,
        col: Int
    ): Int {
        for (i in 1..3) {
            val y = row + dir.first * i
            val x = col + dir.second * i
            if (y !in 0..lastIndex || x !in 0..this[0].lastIndex) {
                return 0
            }
            when (i) {
                1 -> if (this[y][x] != 'M') return 0
                2 -> if (this[y][x] != 'A') return 0
                3 -> if (this[y][x] != 'S') return 0
            }
        }
        return 1
    }

    private fun List<CharArray>.scanX_Mas(
        row: Int,
        col: Int
    ): Int {
        if (row !in 1 until lastIndex || col !in 1 until lastIndex) return 0
        if ((this[row - 1][col - 1] == 'M' && this[row + 1][col + 1] == 'S') ||
            (this[row - 1][col - 1] == 'S' && this[row + 1][col + 1] == 'M')
        ) {
            if (this[row - 1][col + 1] == 'M' && this[row + 1][col - 1] == 'S') return 1
            if (this[row - 1][col + 1] == 'S' && this[row + 1][col - 1] == 'M') return 1
        }
        return 0
    }
}
