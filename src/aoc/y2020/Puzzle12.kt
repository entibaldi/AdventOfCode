package aoc.y2020

import aoc.Puzzle
import kotlin.math.abs

fun main() {
    Puzzle12().runAndProfile()
}

class Puzzle12 : Puzzle(2020, 12) {

    override fun run() {
        val instructions = inputLines
            .map { it.substring(0, 1).first() to it.substring(1).toInt() }
        part1(instructions)
        part2(instructions)
    }

    private fun part1(instructions: List<Pair<Char, Int>>) {
        val state = intArrayOf(0, 0, 0)
        instructions.forEach { moveBoat(state, it) }
        println(abs(state[0]) + abs(state[1]))
    }

    private fun part2(instructions: List<Pair<Char, Int>>) {
        val boatState = intArrayOf(0, 0)
        val waypointState = intArrayOf(10, -1)
        instructions.forEach { moveBoatWithWaypoint(boatState, waypointState, it) }
        println(abs(boatState[0]) + abs(boatState[1]))
    }

    private fun moveBoatWithWaypoint(boatState: IntArray, waypointState: IntArray, move: Pair<Char, Int>) {
        val (type, amount) = move
        when (type) {
            'N' -> waypointState[1] -= amount
            'S' -> waypointState[1] += amount
            'E' -> waypointState[0] += amount
            'W' -> waypointState[0] -= amount
            'L' -> rotateWaypoint(waypointState, amount % 360)
            'R' -> rotateWaypoint(waypointState, (-amount + 360) % 360)
            'F' -> {
                boatState[0] += waypointState[0] * amount
                boatState[1] += waypointState[1] * amount
            }
        }
    }

    private fun rotateWaypoint(waypointState: IntArray, degree: Int) {
        when (degree) {
            90 -> {
                val tx = waypointState[0]
                waypointState[0] = waypointState[1]
                waypointState[1] = -tx
            }
            180 -> {
                waypointState[0] = -waypointState[0]
                waypointState[1] = -waypointState[1]
            }
            270 -> {
                val tx = waypointState[0]
                waypointState[0] = -waypointState[1]
                waypointState[1] = tx
            }
        }
    }

    private fun moveBoat(
        state: IntArray,
        move: Pair<Char, Int>
    ) {
        val (type, amount) = move
        val direction = if (type == 'F') {
            when (((state[2] % 360) + 360) % 360) {
                0 -> 'E'
                90 -> 'N'
                180 -> 'W'
                270 -> 'S'
                else -> throw RuntimeException()
            }
        } else type
        when (direction) {
            'N' -> state[1] -= amount
            'S' -> state[1] += amount
            'E' -> state[0] += amount
            'W' -> state[0] -= amount
            'L' -> state[2] += amount
            'R' -> state[2] -= amount
        }
    }

}