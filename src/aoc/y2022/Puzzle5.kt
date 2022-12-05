package aoc.y2022

import aoc.Puzzle
import java.util.LinkedList
import java.util.TreeMap

fun main() {
    Puzzle5().runAndProfile()
}

class Puzzle5 : Puzzle(2022, 5) {

    override fun run() {
        val (stacksString, movesString) = inputGroups
        val stacks = mutableMapOf<Int, LinkedList<Char>>()
        stacksString.lines()
            .forEach { stackLine ->
                "(\\[\\w]\\s?)|(\\s{3,4})".toRegex().findAll(stackLine).forEachIndexed { index, matchResult ->
                    if (matchResult.value.contains("[")) {
                        stacks.getOrPut(index + 1) { LinkedList() }.addFirst(matchResult.value[1])
                    }
                }
            }
        val moves = movesString.lines().map {
            val ints = "\\d+".toRegex().findAll(it).map { intString -> intString.value.toInt() }.toList()
            Triple(ints[0], ints[1], ints[2])
        }
        println(computePart1(TreeMap(stacks.mapValues { LinkedList(it.value) }), moves))
        println(computePart2(TreeMap(stacks.mapValues { LinkedList(it.value) }), moves))
    }

    private fun computePart1(stacks: TreeMap<Int, LinkedList<Char>>, moves: List<Triple<Int, Int, Int>>): String {
        for ((count, from, to) in moves) {
            repeat(count) {
                stacks.getOrPut(to) { LinkedList() }.addLast(
                    stacks[from]!!.removeLast()
                )
            }
        }
        return stacks.values.map { it.last }.joinToString("")
    }

    private fun computePart2(stacks: TreeMap<Int, LinkedList<Char>>, moves: List<Triple<Int, Int, Int>>): String {
        for ((count, from, to) in moves) {
            stacks.getOrPut(to) { LinkedList() }.addAll(
                buildList {
                    repeat(count) {
                        add(stacks[from]!!.removeLast())
                    }
                }.reversed()
            )
        }
        return stacks.values.map { it.last }.joinToString("")
    }
}
