package aoc.utils

import kotlin.math.abs

data class Point(val x: Int, val y: Int, val z: Int? = null) : DjkNode() {
    infix fun distL1(other: Point) = abs(x - other.x) + abs(y - other.y)
}

data class Vector(val point: Point, val dir: Point)

operator fun Point.plus(other: Point) =
    Point(this.x + other.x, this.y + other.y, this.z?.plus(other.z ?: 0))

operator fun Point.minus(other: Point) =
    Point(this.x - other.x, this.y - other.y, this.z?.minus(other.z ?: 0))

@JvmName("collectionContains")
operator fun Collection<Collection<*>>.contains(p: Point): Boolean =
    isNotEmpty() && p.y in this.indices && p.x in this.first().indices

operator fun Collection<CharArray>.contains(p: Point): Boolean =
    isNotEmpty() && p.y in this.indices && p.x in this.first().indices

fun P(x: Int, y: Int, z: Int? = null) = Point(x, y, z)