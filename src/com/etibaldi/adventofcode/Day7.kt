package com.etibaldi.adventofcode

import java.io.File

data class Node(
    val id: String,
    val before: MutableList<Node> = mutableListOf(),
    var secondsWorked: Int = 0
) {
    // 'A' char = 65
    val completed
        get() = (secondsWorked - id.toCharArray().first().toInt() + 4) >= 0

    override fun equals(other: Any?): Boolean {
        return other is Node && id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return id
    }
}

data class NodeCondition(
    val before: String,
    val after: String
)

val inputRegex = Regex("Step ([A-Z]) must be finished before step ([A-Z]) can begin\\.")
val nodeConditions = File("7-input.txt").readLines()
    .map { inputRegex.matchEntire(it)!!.groupValues }
    .map { NodeCondition(it[1], it[2]) }

val nodes = mutableMapOf<String, Node>()
val completedNodes
    get() = nodes.values.filter { it.completed }
val missingNodes
    get() = nodes.values.filterNot { it.completed }
val runnableNodes
    get() = missingNodes.filter { it.before.isEmpty() && it.secondsWorked == 0 }
val runningNodes
    get() = missingNodes.filter { it.secondsWorked > 0 }

val sequence = mutableListOf<Node>()

/**
 * Created by krakk on 16/12/2018.
 */
fun main(args: Array<String>) {
    part1()
    part2()
}

fun buildConditions() {
    // we build the tree based on the conditions
    nodeConditions.forEach { c ->
        val bNode = nodes.getOrPut(c.before) { Node(c.before) }
        val aNode = nodes.getOrPut(c.after) { Node(c.after) }
        aNode.before.add(bNode)
    }
    nodes.forEach { it.value.secondsWorked = 0 }
}

private fun firstNodeToRun() = runnableNodes.sortedBy { it.id }.first()

private fun part1() {
    buildConditions()
    // find first
    while (missingNodes.isNotEmpty()) {
        val completedNode = firstNodeToRun()
        completedNode.secondsWorked = Integer.MAX_VALUE
        sequence.add(completedNode)
        missingNodes.forEach { it.before.remove(completedNode) }
    }
    println(sequence.map { it.toString() }.reduce { acc, s -> acc + s })
}

val nWorkers = 5

private fun part2() {
    buildConditions()
    var seconds = 0
    while (missingNodes.isNotEmpty()) {
        runningNodes.forEach { it.secondsWorked++ }
        while (runnableNodes.isNotEmpty() && runningNodes.size < nWorkers) {
            val runningNode = firstNodeToRun()
            runningNode.secondsWorked++
        }
        completedNodes.forEach { completed ->
            missingNodes.forEach { it.before.remove(completed) }
        }
        seconds++
    }
    println(seconds)
}

