package com.entibaldi.adventofcode2020

import java.io.File

fun main() {
    val input = File("res/5/input.txt").readLines()
    val ids = input.map { getSeatId(it) }
    println("Part1 result: ${ids.max()}")
    for (it in ids) {
        if (it + 1 !in ids && it != ids.max()) {
            println("part2 result: ${it + 1}")
        }
    }
}

fun getSeatId(seat: String): Int =
        getRow(seat.substring(0, 7)) * 8 + getColumn(seat.substring(7, seat.length))

fun getRow(seat: String): Int = seat
        .replace("F", "0")
        .replace("B", "1")
        .toInt(2)

fun getColumn(seat: String): Int = seat
        .replace("L", "0")
        .replace("R", "1")
        .toInt(2)

