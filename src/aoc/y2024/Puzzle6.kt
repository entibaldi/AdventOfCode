package aoc.y2024

import aoc.Puzzle
import aoc.utils.Point
import aoc.utils.Vector
import aoc.utils.contains
import aoc.utils.plus


fun main() {
    Puzzle6().runAndProfile()
}

class Puzzle6 : Puzzle(2024, 6) {

    private lateinit var start: Vector

    override fun run() {
        val map = inputLines.map { it.toCharArray() }
        out@ for (i in map.indices) {
            for (j in map[0].indices) {
                if (map[i][j] == '^') {
                    start = Vector(Point(j, i), Point(x = 0, y = -1))
                    break@out
                }
            }
        }
        part1(map)
        part2(map)
    }

    private fun part1(map: List<CharArray>) {
        var pos = start.point
        var dir = start.dir
        val positions = mutableSetOf(start.point)
        while (true) {
            val nPos = pos + dir
            if (nPos !in map) break
            if (map[nPos.y][nPos.x] == '#') {
                dir = dir.rotateRight()
            } else {
                pos = nPos
                positions.add(pos)
            }
        }
        println(positions.size)
    }

    private fun part2(map: List<CharArray>) {
        var loops = 0
        repeat(map.size) { y ->
            repeat(map[0].size) { x ->
                if (map[y][x] == '.') {
                    map[y][x] = '#'
                    if (isLooping(map)) loops++
                    map[y][x] = '.'
                }
            }
        }
        println(loops)
    }

    private fun isLooping(
        map: List<CharArray>,
    ): Boolean {
        var pos = start.point
        var dir = start.dir
        val vectors = mutableSetOf(start)
        while (true) {
            val nPos = pos + dir
            when {
                nPos !in map -> return false
                map[nPos.y][nPos.x] == '#' -> dir = dir.rotateRight()
                else -> {
                    pos = nPos
                    val vector = Vector(pos, dir)
                    if (vector in vectors) return true
                    vectors += vector
                }
            }
        }
    }

    private fun Point.rotateRight(): Point = when (this) {
        Point(0, -1) -> Point(1, 0)
        Point(1, 0) -> Point(0, 1)
        Point(0, 1) -> Point(-1, 0)
        else -> Point(0, -1)
    }
}
