package aoc.y2021

import aoc.Puzzle
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get

fun main() {
    Puzzle9().runAndProfile()
}

class Puzzle9 : Puzzle(2021, 9) {

    override fun run() {
        val mapInts: List<List<Int>> = inputLines.map { it.toCharArray().map { c -> c.digitToInt() } }
        val height = mapInts.size
        val width = mapInts[0].size
        val map: D2Array<Int> = mk.ndarray(mapInts.flatten(), intArrayOf(height, width))
        var risk = 0
        val minimums = mutableListOf<Pair<Int, Int>>()
        for (mi in map.multiIndices) {
            val i = mi[0]
            val j = mi[1]
            val v = map[i, j]
            if ((i == 0 || map[i - 1, j] > v) &&
                (i == height - 1 || map[i + 1, j] > v) &&
                (j == 0 || map[i, j - 1] > v) &&
                (j == width - 1 || map[i, j + 1] > v)
            ) {
                risk += v + 1
                minimums += i to j
            }
        }
        println(risk)
        val basinsSizes = mutableListOf<Int>()
        for (min in minimums) {
            val basin = mutableSetOf<Pair<Int, Int>>().apply { add(min) }
            var newAdded = true
            while (newAdded) {
                val tempBasin = mutableSetOf<Pair<Int, Int>>()
                for ((i, j) in basin) {
                    val v = map[i, j]
                    if (i > 0 && map[i - 1, j] < 9 && map[i - 1, j] > v) {
                        tempBasin.add(i - 1 to j)
                    }
                    if (i < height - 1 && map[i + 1, j] < 9 && map[i + 1, j] > v) {
                        tempBasin.add(i + 1 to j)
                    }
                    if (j > 0 && map[i, j - 1] < 9 && map[i, j - 1] > v) {
                        tempBasin.add(i to j - 1)
                    }
                    if (j < width - 1 && map[i, j + 1] < 9 && map[i, j + 1] > v) {
                        tempBasin.add(i to j + 1)
                    }
                }
                newAdded = basin.addAll(tempBasin)
            }
            basinsSizes += basin.size
        }
        println(basinsSizes.sortedDescending().take(3).reduce { f, s -> f * s })
    }
}