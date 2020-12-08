package aoc.y2020

import aoc.Puzzle

fun main() {
    Puzzle7().runAndProfile()
}

class Puzzle7 : Puzzle(2020, 7) {
    override fun run() {
        val bagRegex = "(\\d+)\\s(\\b\\w+\\b\\s\\w+\\b)".toRegex()
        val bagsByColor = mutableMapOf<String, BagNode>()
        inputLines.forEach { line ->
            val rules = line.split(" bags contain ")
            val color = rules[0].trim()
            val colorContains = bagRegex.findAll(rules[1]).asIterable().map { it.groupValues }.map { it[2] to it[1] }
            val bagNode = bagsByColor.getOrPut(color) { BagNode(color) }
            val childrenNodes = colorContains.map { (color, amount) ->
                bagsByColor.getOrPut(color) { BagNode(color) } to amount.toInt()
            }
            childrenNodes.forEach { (child, amount) ->
                bagNode.children[child] = amount
                if (child.parents.none { it.bagColor == bagNode.bagColor }) {
                    child.parents += bagNode
                }
            }
        }
        println("Part1: ${bagsByColor["shiny gold"]?.ancestors()?.count()}")
        println("Part2: ${bagsByColor["shiny gold"]?.totalBagsCount()}")
    }
}

private class BagNode(
    val bagColor: String,
    val parents: MutableList<BagNode> = mutableListOf(),
    val children: MutableMap<BagNode, Int> = mutableMapOf()
) {
    fun contains(bagColor: String): Boolean =
        with(children.keys) {
            any { it.bagColor == bagColor } || any { it.contains(bagColor) }
        }

    fun ancestors(): Set<BagNode> = (parents + parents.flatMap { it.ancestors() }).toSet()

    fun totalBagsCount(): Int = children.entries.sumBy { (child, count) -> count * (1 + child.totalBagsCount()) }

    override fun hashCode(): Int = bagColor.hashCode()

    override fun equals(other: Any?): Boolean = (other as? BagNode)?.bagColor == bagColor

    override fun toString(): String = "Bag($bagColor, parents=${parents.map { it.bagColor }}, children=${children.entries.map { it.key.bagColor to it.value }}"
}