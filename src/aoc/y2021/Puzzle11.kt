package aoc.y2021

import aoc.Puzzle
import aoc.utils.adjacentIndexes
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.any
import org.jetbrains.kotlinx.multik.ndarray.operations.plusAssign

fun main() {
    Puzzle11().runAndProfile()
}

class Puzzle11 : Puzzle(2021, 11) {

    override fun run() {
        val map: D2Array<Int> = mk.ndarray(
            inputLines.map { it.toCharArray().map { c -> c.digitToInt() } }.flatten(),
            intArrayOf(10, 10)
        )
        var totalFlashes = 0L
        var index = 0
        while (true) {
            val flashes = mutableSetOf<Pair<Int, Int>>()
            map += 1
            index++
            do {
                for ((x, y) in map.multiIndices) {
                    if (map[x, y] >= 10) {
                        flashes += x to y
                        map[x, y] = 0
                        map.adjacentIndexes(x, y)
                            .filter { (ax, ay) -> map[ax, ay] != 0 }
                            .forEach { (ax, ay) -> map[ax, ay] += 1 }
                    }
                }
            } while (map.any { it >= 10})
            if (index == 99) println(totalFlashes)
            if (flashes.size == map.size) {
                println("Sync happened at step: $index")
                return
            }
            totalFlashes += flashes.size
            flashes.clear()
        }
    }
}
