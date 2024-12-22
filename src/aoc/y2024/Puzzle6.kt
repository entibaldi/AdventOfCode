package aoc.y2024

import aoc.Puzzle


fun main() {
    Puzzle6().runAndProfile()
}

typealias Coords = Pair<Int,Int>

class Puzzle6 : Puzzle(2024, 6) {

    override fun run() {
        val map = inputLines.map { it.toCharArray() }
        var row = 0
        var col = 0
        val dir = 0 to -1
        out@for (i in map.indices) {
            for (j in map[0].indices) {
                if (map[i][j] == '^') {
                    row = i
                    col = j
                    break@out
                }
            }
        }
        part1(map, row, col, dir)
        part2(map, startPosition = col to row, startDir = dir)
    }

    private fun part1(map: List<CharArray>, startRow: Int, startCol: Int, startDir: Coords) {
        var row = startRow
        var col = startCol
        var dir = startDir
        val positions = mutableSetOf<Coords>()
        positions.add(col to row)
        while (true) {
            val nRow = row + dir.second
            val nCol = col + dir.first
            if (nRow !in map.indices || nCol !in map[0].indices) break
            if (map[nRow][nCol] == '#') {
                dir = dir.rotateRight()
            } else {
                row = nRow
                col = nCol
                positions.add(col to row)
            }
        }
        println(positions.size)
    }

    private fun part2(map: List<CharArray>, startPosition: Coords, startDir: Coords) {
        var position = startPosition
        var dir = startDir
        val positions = mutableMapOf<Coords, Coords>()
        val obstructions = mutableSetOf<Coords>()
        positions[position] = dir
        while (true) {
            val nRow = position.second + dir.second
            val nCol = position.first + dir.first
            when {
                nRow !in map.indices || nCol !in map[0].indices -> break
                map[nRow][nCol] == '#' -> dir = dir.rotateRight()
                else -> {
                    val newPosition = nCol to nRow
                    map[nRow][nCol] = '#'
                    val rotatedDir = dir.rotateRight()
                    if (isLooping(map, position, rotatedDir, positions)) {
                        obstructions.add(newPosition)
                    }
                    position = newPosition
                    map[nRow][nCol] = '.'
                    positions[position] = dir
                }
            }
        }
        println(obstructions)
        println(obstructions.size)
    }

    private fun isLooping(
        map: List<CharArray>,
        startPosition: Coords,
        startDir: Coords,
        startPositions: Map<Coords, Coords>
    ): Boolean {
        var position = startPosition
        var dir = startDir
        val positions = startPositions.toMutableMap()
        positions[position] = dir
        while (true) {
            val nRow = position.second + dir.second
            val nCol = position.first + dir.first
            when {
                nRow !in map.indices || nCol !in map[0].indices -> return false
                map[nRow][nCol] == '#' -> dir = dir.rotateRight()
                else -> {
                    position = nCol to nRow
                    if (positions[position] == dir) return true
                    positions[position] = dir
                }
            }
        }
    }

    private fun Coords.rotateRight(): Coords = when (this) {
        0 to -1 -> 1 to 0
        1 to 0 -> 0 to 1
        0 to 1 -> -1 to 0
        else -> 0 to -1
    }
}
