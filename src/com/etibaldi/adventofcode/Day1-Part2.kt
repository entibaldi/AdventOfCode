package com.etibaldi.adventofcode

import java.io.File

/**
 * Created by krakk on 16/12/2018.
 */
fun main(args: Array<String>) {
    val f = File("1-input.txt")
        .readLines()
        .map { it.toInt() }
    var i = 0
    val acc = mutableListOf<Int>()
    while (true) {
        val fi = f[i % f.size] + (acc.lastOrNull() ?: 0)
        if (acc.contains(fi)) {
            println(fi)
            println("frequency found after ${acc.size} iterations")
            break
        } else {
            acc.add(fi)
        }
        i ++
    }
}