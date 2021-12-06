package aoc.y2021

import aoc.Puzzle
import java.util.concurrent.atomic.AtomicLong

fun main() {
    Puzzle6().runAndProfile()
}

class Puzzle6 : Puzzle(2021, 6) {

    override fun run() {
        val input: Map<Int, Int> = inputText.split(",").map { it.toInt() }
            .groupBy { it }.mapValues { it.value.count() }
        println(spawnFishes(input.mapValues { AtomicLong(it.value.toLong()) }, 80))
        println(spawnFishes(input.mapValues { AtomicLong(it.value.toLong()) }, 256))
    }

    private fun spawnFishes(input: Map<Int, AtomicLong>, days: Int): Long {
        var fishes = input
        repeat(days) {
            val newStates = mutableMapOf<Int, AtomicLong>()
            fishes.entries.stream().forEach { (fish, count) ->
                if (fish == 0) {
                    newStates.computeIfAbsent(6) { AtomicLong() }.addAndGet(count.get())
                    newStates.computeIfAbsent(8) { AtomicLong() }.addAndGet(count.get())
                } else {
                    newStates.computeIfAbsent(fish - 1) { AtomicLong() }.addAndGet(count.get())
                }
            }
            fishes = newStates
        }
        println(fishes)
        return fishes.values.sumOf { it.get() }
    }
}