package com.etibaldi.adventofcode

import io.data2viz.geom.Point
import java.io.File
import kotlin.math.abs

data class IPoint(val x: Int, val y: Int) {

    fun l1DistanceTo(other: IPoint) = abs(this.x - other.x) + abs(this.y - other.y)
}

val coordinates = File("6-input.txt").readLines()
    .map { it.split(", ") }
    .map { IPoint(it[0].toInt(), it[1].toInt()) }

val maxX = coordinates.map { it.x }.max()!!
val maxY = coordinates.map { it.y }.max()!!

val lattice : Array<Array<String>> = Array(maxX + 1) { Array(maxY + 1) { "" } }

val setToExclude = mutableSetOf<Int>()

/**
 * Created by krakk on 16/12/2018.
 */
fun main(args: Array<String>) {
    coordinates.forEachIndexed { i, iPoint -> lattice[iPoint.x][iPoint.y] = "C$i" }
    for (i in 0..maxX)
        for (j in 0..maxY) {
            val p = IPoint(i, j)
            val mins = coordinates.withIndex().allMinBy { it.value.l1DistanceTo(p) }
            when {
                mins.size > 1 -> lattice[i][j] = "*"
                mins.size == 1 -> lattice[i][j] = mins.first().index.toString()
            }
        }

    for (i in 0..maxX)
        listOf(lattice[i][0], lattice[i][maxY]).forEach { v ->
            v.toIntOrNull()?.let { setToExclude.add(it) }
        }
    for (j in 0..maxY)
        listOf(lattice[0][j], lattice[maxX][j]).forEach { v ->
            v.toIntOrNull()?.let { setToExclude.add(it) }
        }

    part1()
    part2()
}

private fun part1() {
    val maxCoord = coordinates.withIndex()
        .filterNot { setToExclude.contains(it.index) }
        .map { coord ->
            coord to lattice.sumBy { v -> v.count { it == coord.index.toString() } }
        }.maxBy { it.second }
    println(maxCoord)
}

private fun part2() {
    val safeAreaLattice = lattice.mapIndexed { i, v -> v.mapIndexed { j, _ ->
        val p = IPoint(i, j)
        val totDistance = coordinates.sumBy { it.l1DistanceTo(p) }
        if (totDistance < 10000) "*" else ""
    }}
    println(safeAreaLattice.sumBy { it.count { s -> s == "*" } })
}

