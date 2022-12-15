package aoc.y2022

import aoc.Puzzle
import aoc.utils.P
import aoc.utils.Point

fun main() {
    Puzzle15().runAndProfile()
}

class Puzzle15 : Puzzle(2022, 15) {

    private val numberRegex = "-?\\d+".toRegex()

    override fun run() {
        val sensors = inputLines.map {
            val (sX, sY, bX, bY) = numberRegex.findAll(it).toList().map { match -> match.value.toInt() }
            Sensor(P(sX, sY), P(bX, bY))
        }.sortedBy { it.sensor.x }
        part1(sensors)
        part2(sensors)
    }

    private fun part1(sensors: List<Sensor>) {
        val beacons = sensors.map { it.beacon }.toSet()
        val minX = sensors.minOf { it.sensor.x - it.distance }
        val maxX = sensors.maxOf { it.sensor.x + it.distance }
        val y = 2_000_000
        val busyLocations = (minX..maxX).count { x ->
            val pIt = P(x, y)
            !beacons.contains(pIt) && sensors.any { (p, b) -> (p distL1 pIt) <= (p distL1 b) }
        }
        println(busyLocations)
    }

    private fun part2(sensors: List<Sensor>) {
        val max = 4_000_000
        val result = (0..max).firstNotNullOf { y ->
            var x = 0
            while (x <= max) {
                val rangeDiff = sensors.maxOf { it.distance - (P(x,y) distL1 it.sensor) }
                if (rangeDiff < 0) return@firstNotNullOf x * 4_000_000L + y
                x += rangeDiff + 1
            }
            null
        }
        println(result)
    }

    data class Sensor(val sensor: Point, val beacon: Point) {
        val distance = sensor distL1 beacon
    }
}
