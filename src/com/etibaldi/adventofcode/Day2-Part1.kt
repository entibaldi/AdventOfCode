package com.etibaldi.adventofcode

import java.io.File

/**
 * Created by krakk on 16/12/2018.
 */
fun main(args: Array<String>) {
    val ids = File("2-input.txt")
        .readLines().map { it.toCharArray() }
    val output = ids.countRepetitions(2) * ids.countRepetitions(3)
    println(output)
}


