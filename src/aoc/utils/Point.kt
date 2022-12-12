package aoc.utils

data class Point(val x: Int, val y: Int, val z: Int? = null) : DjkNode()

fun P(x: Int, y: Int, z: Int? = null) = Point(x, y, z)