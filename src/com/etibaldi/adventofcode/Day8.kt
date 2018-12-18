package com.etibaldi.adventofcode

import java.io.File
import kotlin.math.abs

data class Node(
    val nChild: Int,
    val nMetadata: Int,
    val children: List<Node>,
    val metadata: List<Int>
) {
    fun getMetadataLength(): Int = nMetadata + children.sumBy { it.getMetadataLength() }
    fun getMetadataCount(): Int = metadata.sum() + children.sumBy { it.getMetadataCount() }
    fun getValue(): Int =
        if (children.isEmpty()) metadata.sum()
        else metadata.sumBy { children.getOrNull(it - 1)?.getValue() ?: 0}
}

val inputNumbers = File("8-input.txt")
    .readLines().reduce { acc, s -> acc + s }.split(" ").map { it.toInt() }

lateinit var root: Node

/**
 * Created by krakk on 16/12/2018.
 */
fun main(args: Array<String>) {
    root = parseNode(0).first
    part1()
    part2()
}

fun parseNode(iStart: Int): Pair<Node, Int> {
    val nChild = inputNumbers[iStart]
    val nMedatada = inputNumbers[iStart + 1]
    val children = mutableListOf<Node>()
    var i = 0
    var lastNodeStart = iStart + 2
    while (i < nChild) {
        val (child, nextIndex) = parseNode(lastNodeStart)
        lastNodeStart = nextIndex
        children.add(child)
        i++
    }
    val metadata = (0 until nMedatada).map { inputNumbers[lastNodeStart + it] }
    return Node(nChild, nMedatada, children, metadata) to (lastNodeStart + nMedatada)
}

private fun part1() {
    println(root.getMetadataCount())
}

private fun part2() {
    println(root.getValue())
}

