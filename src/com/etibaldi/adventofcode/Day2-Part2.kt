package com.etibaldi.adventofcode

import java.io.File

/**
 * Created by krakk on 16/12/2018.
 */
fun main(args: Array<String>) {
    val ids = File("2-input.txt")
        .readLines().map { it.toCharArray() }
    ids.forEachComparison { id1, id2 ->
        val common = extractEqualChars(id1, id2)
        if (common.size == id1.size - 1) {
            println(common)
            return
        }
    }
}
