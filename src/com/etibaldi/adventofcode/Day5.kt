package com.etibaldi.adventofcode

import java.io.File

val polymer = File("5-input.txt").readLines()
    .reduce { acc, s -> acc + s }.toList()

val alphabet = (65..90).map { it.toChar() }

/**
 * Created by krakk on 16/12/2018.
 */
fun main(args: Array<String>) {
    part1()
    part2()
}

fun part2() {
    val minReducedSize = alphabet
        .map {
            val str = String(polymer.toCharArray())
                .replace(it+"", "", ignoreCase = true)
                .toList()
            println(String(str.toCharArray()))
            str
        }
        .map { reducePolymer(it) }
        .map {
            println(it)
            it.size
        }.min()
    println(minReducedSize)
}

fun part1() {
    println(reducePolymer(polymer).size)
}

fun reducePolymer(polymer: List<Char>) : List<Char> {
    var reduced: List<Char> = polymer
    do {
        val preSize = reduced.size
        reduced = reduced.dropTwoWhile { c1, c2 -> c1 - c2 == 32 || c1 - c2 == -32 }
    } while (reduced.size < preSize)
    return reduced
}


