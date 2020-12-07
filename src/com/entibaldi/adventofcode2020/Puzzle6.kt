package com.entibaldi.adventofcode2020

import java.io.File

fun main() {
    val input = File("res/6/input.txt").readText().split("\n\n")
    val resultPart1 = input.map { it.replace("\n", "").chars().distinct().count() }.sum()
    val resultPart2 = input.map { answers ->
        answers.split("\n")
            .map { it.trim().toCharArray().toSet() }
            .reduce { acc, hashSet -> acc.intersect(hashSet) }
            .size
    }.sum()
    println("Part1: $resultPart1")
    println("Part2: $resultPart2")
}


