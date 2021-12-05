package aoc.y2020

import aoc.Puzzle

fun main() {
    Puzzle13().runAndProfile()
}

class Puzzle13 : Puzzle(2020, 13) {

    override fun run() {
        val lines = inputText.split("\n")
        val startTime = lines[0].replace("\r", "").toInt()
        val buses = lines[1].split(",").map { it.toIntOrNull() }
        part1(startTime, buses)
        part2(buses)
    }

    private fun part1(startTime: Int, buses: List<Int?>) {
        val bestBus = buses.filterNotNull().minByOrNull { it - startTime % it }!!
        val timeToWait = bestBus - startTime % bestBus
        println(bestBus * timeToWait)
    }

    private fun part2(buses: List<Int?>) {
        val busesStartTimes = buses.withIndex().filter { it.value != null }.sortedByDescending { it.value }
        var deltaSearch = busesStartTimes.first().value!!.toLong()
        for (i in 1..busesStartTimes.lastIndex) {
            deltaSearch /= gcd(deltaSearch, busesStartTimes[i].value!!.toLong())
        }
        var timestamp = -busesStartTimes.first().index.toLong()
        var indexFound = 0
        while (indexFound < busesStartTimes.size - 1) {
            timestamp += deltaSearch
            timestampLoop@ for (i in (indexFound + 1)..busesStartTimes.lastIndex) {
                val (busIndex, bus) = busesStartTimes[i]
                if ((timestamp + busIndex) % bus!! == 0L) {
                    indexFound = i
                    deltaSearch *= bus
                    for (j in (i + 1)..busesStartTimes.lastIndex) {
                        deltaSearch /= gcd(deltaSearch, busesStartTimes[j].value!!.toLong())
                    }
                } else {
                    break@timestampLoop
                }
            }
        }
        println(timestamp)
    }

    fun gcd(a: Long, b: Long): Long {
        if (b == 0L) return a
        return gcd(b, a % b)
    }

}