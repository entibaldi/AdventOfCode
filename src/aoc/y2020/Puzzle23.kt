package aoc.y2020

import aoc.Puzzle
import java.util.*

fun main() {
    Puzzle23().runAndProfile()
}

class Puzzle23 : Puzzle(2020, 23) {

    private class Cup(val value: Int) {
        lateinit var next: Cup

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as Cup
            if (value != other.value) return false
            return true
        }

        override fun hashCode(): Int = value

        override fun toString(): String = value.toString()
    }

    override fun run() {
        part1()
        part2()
    }

    private fun part1() {
        val input = "253149867".toCharArray().map { it - '0' }
        val indexes = initialise(input, 9)
        play(indexes, input[0], 100)
        printCups(indexes)
    }

    private fun part2() {
        val input = "253149867".toCharArray().map { it - '0' }
        val indexes = initialise(input, 1_000_000)
        play(indexes, input[0], 10_000_000)
        println(indexes[0].next.value.toLong() * indexes[0].next.next.value)
    }

    private fun initialise(input: List<Int>, size: Int): Array<Cup> {
        val inputRange = input.min()!!..input.max()!!
        val inputCups = input.map { Cup(it) }.associateBy { it.value }
        input.windowed(2, 1).forEach {
            inputCups[it[0]]!!.next = inputCups[it[1]]!!
        }
        var lastCup: Cup? = null
        return Array(size) { index ->
            val value = index + 1
            if (value in inputRange) {
                inputCups[value]!!
            } else {
                Cup(value)
            }.apply {
                if (index == input.size) {
                    inputCups[input.last()]!!.next = this
                } else if (index > input.size) {
                    lastCup!!.next = this
                }
                if (index == size - 1) {
                    val nextCup = inputCups[input.first()]!!
                    if (index == input.size - 1) {
                        inputCups[input.last()]!!.next = nextCup
                    } else {
                        this.next = nextCup
                    }
                }
                lastCup = this
            }
        }
    }

    private fun play(indexes: Array<Cup>, startCupValue: Int, rounds: Int) {
        var currentCup = indexes[startCupValue - 1]
        repeat(rounds) {
            val r1 = currentCup.next
            val r2 = currentCup.next.next
            val r3 = currentCup.next.next.next
            currentCup.next = r3.next
            var destinationCupValue = if (currentCup.value > 1) currentCup.value - 1 else indexes.size
            while (destinationCupValue == r1.value || destinationCupValue == r2.value || destinationCupValue == r3.value) {
                destinationCupValue = if (destinationCupValue > 1) destinationCupValue - 1 else indexes.size
            }
            val destinationCup = indexes[destinationCupValue - 1]
            r3.next = destinationCup.next
            destinationCup.next = r1
            currentCup = currentCup.next
        }
    }

    private fun printCups(indexes: Array<Cup>) {
        var cup = indexes[0]
        repeat(indexes.size - 1) {
            print(cup.next.value)
            cup = cup.next
        }
        println()
    }
}