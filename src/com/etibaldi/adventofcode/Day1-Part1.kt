package com.etibaldi.adventofcode

import java.io.File

/**
 * Created by krakk on 16/12/2018.
 */
fun main(args: Array<String>) {
    val out = File("1-input.txt")
        .readLines()
        .map { it.toInt() }
        .sum()
    println(out)
}