package aoc.utils

import kotlin.math.abs

data class Point(val x: Int, val y: Int, val z: Int? = null) : DjkNode() {
    infix fun distL1(other: Point) = abs(x - other.x) + abs(y - other.y)
}

fun P(x: Int, y: Int, z: Int? = null) = Point(x, y, z)