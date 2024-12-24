package aoc.y2024

import aoc.Puzzle
import aoc.utils.Point
import aoc.utils.contains
import aoc.utils.pairTuples


fun main() {
    Puzzle8().runAndProfile()
}

class Puzzle8 : Puzzle(2024, 8) {

    lateinit var nodes: Map<Char, List<Point>>

    override fun run() {
        val nodes = mutableMapOf<Char, MutableList<Point>>()
        val map = inputLines.map { it.toCharArray() }
        for (y in map.indices) {
            for (x in map[0].indices) {
                val c = map[y][x]
                if (c in 'A'..'Z' || c in 'a'..'z' || c in '0'..'9') {
                    nodes.computeIfAbsent(c) { mutableListOf() } += Point(x, y)
                }
            }
        }
        this.nodes = nodes
        part1(map)
        part2(map)
    }

    private fun part1(map: List<CharArray>) {
        val antinodes = mutableSetOf<Point>()
        for (points in nodes.values) {
            for ((p1, p2) in points.pairTuples()) {
                val a1 = Point(
                    x = p1.x + 2 * (p2.x - p1.x),
                    y = p1.y + 2 * (p2.y - p1.y),
                )
                if (a1 in map) antinodes += a1
                val a2 = Point(
                    x = p2.x + 2 * (p1.x - p2.x),
                    y = p2.y + 2 * (p1.y - p2.y),
                )
                if (a2 in map) antinodes += a2
            }
        }
        println(antinodes.size)
    }

    private fun part2(map: List<CharArray>) {
        val antinodes = mutableSetOf<Point>()
        for (points in nodes.values) {
            for ((p1, p2) in points.pairTuples()) {
                var i = 2
                antinodes += p1
                antinodes += p2
                while (true) {
                    val a1 = Point(
                        x = p1.x + i * (p2.x - p1.x),
                        y = p1.y + i * (p2.y - p1.y),
                    )
                    if (a1 in map) {
                        antinodes += a1
                        i++
                    } else {
                        break
                    }
                }
                i = 2
                while (true) {
                    val a2 = Point(
                        x = p2.x + i * (p1.x - p2.x),
                        y = p2.y + i * (p1.y - p2.y),
                    )
                    if (a2 in map) {
                        antinodes += a2
                        i++
                    } else {
                        break
                    }
                }

            }
        }
        println(antinodes.size)
    }
}
