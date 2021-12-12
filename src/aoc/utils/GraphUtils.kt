package aoc.utils

data class Vertex(val label: String) {
    override fun toString(): String = label
}

data class MutableGraph(
    override val adjVertices: MutableMap<Vertex, MutableList<Vertex>> = mutableMapOf()
) : IGraph {

    operator fun get(vertex: Vertex) = adjVertices[vertex] ?: listOf()

    fun addVertex(label: String) = adjVertices.computeIfAbsent(Vertex(label)) { mutableListOf() }

    fun addVertex(vertex: Vertex) = adjVertices.computeIfAbsent(vertex) { mutableListOf() }

    fun removeVertex(label: String) = Vertex(label).run {
        adjVertices.values.stream().forEach { it.remove(this) }
        adjVertices.remove(this)
    }

    fun addEdge(label1: String, label2: String) {
        val v1 = Vertex(label1)
        val v2 = Vertex(label2)
        addVertex(v1).add(v2)
        addVertex(v2).add(v1)
    }

    fun removeEdge(label1: String, label2: String) {
        val v1 = Vertex(label1)
        val v2 = Vertex(label2)
        adjVertices[v1]?.remove(v2)
        adjVertices[v2]?.remove(v1)
    }
}

data class Graph(override val adjVertices: Map<Vertex, List<Vertex>>) : IGraph {
    fun toMutable() = MutableGraph(
        adjVertices.mapValues { (_, v) -> v.toMutableList() }.toMutableMap()
    )
}

interface IGraph {
    val adjVertices: Map<Vertex, List<Vertex>>
}