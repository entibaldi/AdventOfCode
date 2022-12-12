package aoc.y2022

import aoc.Puzzle
import aoc.utils.*
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.operations.forEachMultiIndexed
import kotlin.collections.associateWith
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.filter
import kotlin.collections.flatten
import kotlin.collections.forEach
import kotlin.collections.indexOfFirst
import kotlin.collections.isNotEmpty
import kotlin.collections.map
import kotlin.collections.mapNotNull
import kotlin.collections.minOf
import kotlin.collections.mutableMapOf
import kotlin.collections.reversed
import kotlin.collections.set
import kotlin.collections.toSet

fun main() {
    Puzzle12().runAndProfile()
}

class Puzzle12 : Puzzle(2022, 12) {

    override fun run() {
        val startRowIndex = inputLines.indexOfFirst { it.contains("S") }
        val start = P(startRowIndex, inputLines[startRowIndex].indexOfFirst { it == 'S' })
        val endRowIndex = inputLines.indexOfFirst { it.contains("E") }
        val end = P(endRowIndex, inputLines[endRowIndex].indexOfFirst { it == 'E' })
        val (width, height) = inputLines.size to inputLines[0].length

        val map: D2Array<Int> = mk.ndarray(inputLines.map {
            it.replace("S", "a")
                .replace("E", "z")
                .toCharArray()
                .map { c -> c - 'a' }
        }.flatten(), intArrayOf(width, height))

        val nodes = mutableMapOf<Pair<Int, Int>, Point>()
        map.forEachMultiIndexed { (x, y), z -> nodes[x to y] = Point(x, y, z) }
        val graph = DjkGraph(nodes.values.toSet())
        nodes.values.forEach { p ->
            p.adjacentNodes = map.adjacentIndexes(p.x, p.y, diagonal = false)
                .mapNotNull { (x, y) -> nodes[x to y] }
                .filter { p.z!! - it.z!! < 2 }
                .associateWith { 1 }
        }
        graph.calculateShortestPathFrom(nodes[end.x to end.y]!!)
        val shortestPathFromStart = nodes[start.x to start.y]!!.shortestPath.reversed()
        println(shortestPathFromStart.size)
        val shortestPathOverall = nodes.values.filter { it.z == 0 && it.shortestPath.isNotEmpty() }
            .minOf { it.shortestPath.size }
        println(shortestPathOverall)
    }
}
