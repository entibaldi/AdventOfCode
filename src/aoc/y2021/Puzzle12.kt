package aoc.y2021

import aoc.Puzzle
import aoc.utils.MutableGraph
import aoc.utils.Vertex

fun main() {
    Puzzle12().runAndProfile()
}

class Puzzle12 : Puzzle(2021, 12) {

    private val graph = MutableGraph()

    override fun run() {
        inputLines.map { it.split("-") }
            .forEach { graph.addEdge(it[0], it[1]) }
        println(findPaths(START, END).count())
        println(findPaths(Vertex("start"), Vertex("end"), bonusCaveVisited = false).count())
    }

    private fun findPaths(
        start: Vertex,
        end: Vertex,
        visited: MutableSet<Vertex> = mutableSetOf(),
        bonusCaveVisited: Boolean = true
    ): List<List<Vertex>> {
        if (start.label.lowercase() == start.label) {
            visited.add(start)
        }
        return graph[start]
            .flatMap { adj ->
                val isVisited = visited.contains(adj)
                when {
                    isVisited && (bonusCaveVisited || adj == START || adj == END) -> listOf()
                    adj == end -> listOf(listOf(start, end))
                    else -> findPaths(adj, end, visited.toMutableSet(), bonusCaveVisited || isVisited)
                        .map { path: List<Vertex> -> listOf(start) + path }
                }
            }.filterNot { it.isEmpty() }
    }

    companion object {
        private val START = Vertex("start")
        private val END = Vertex("end")
    }
}
