package aoc.utils

import java.util.*

data class DjkGraph<T: DjkNode>(val nodes: Set<T>)

abstract class DjkNode {
    var shortestPath: List<DjkNode> = listOf()
    var distance = Int.MAX_VALUE
    var adjacentNodes: Map<DjkNode, Int> = mapOf()
}

fun <T: DjkNode> DjkGraph<T>.calculateShortestPathFrom(source: DjkNode): DjkGraph<T> {
    source.distance = 0
    val settledNodes: MutableSet<DjkNode> = HashSet<DjkNode>()
    val unsettledNodes: MutableSet<DjkNode> = HashSet<DjkNode>()
    unsettledNodes.add(source)
    while (unsettledNodes.size != 0) {
        val currentNode: DjkNode? = unsettledNodes.minByOrNull { it.distance }
        if (currentNode != null) {
            unsettledNodes.remove(currentNode)
            for ((adjacentNode, edgeWeight) in currentNode.adjacentNodes.entries) {
                if (!settledNodes.contains(adjacentNode)) {
                    if (currentNode.distance + edgeWeight < adjacentNode.distance) {
                        adjacentNode.distance = currentNode.distance + edgeWeight
                        adjacentNode.shortestPath = currentNode.shortestPath + adjacentNode
                    }
                    unsettledNodes.add(adjacentNode)
                }
            }
            settledNodes.add(currentNode)
        }
    }
    return this
}