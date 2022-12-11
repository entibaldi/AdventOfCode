package aoc.y2022

import aoc.Puzzle
import java.util.*
import kotlin.math.floor

fun main() {
    Puzzle11().runAndProfile()
}

class Puzzle11 : Puzzle(2022, 11) {

    private val numberRegex = "\\d+".toRegex()

    override fun run() {
        println(calculateMonkeyInspections(parseMonkeys(), 20, true))
        println(calculateMonkeyInspections(parseMonkeys(), 10000, false))
    }

    private fun parseMonkeys(): SortedMap<Int, Monkey> =
        inputGroups.map { parseMonkey(it) }.associateBy { it.id }.toSortedMap()

    private fun calculateMonkeyInspections(
        monkeysById: SortedMap<Int, Monkey>,
        rounds: Int,
        limitWithDivision: Boolean
    ): Long {
        val inspectCount = mutableMapOf<Int, Long>()
        val divisorLimit = monkeysById.values.map { it.testDivider }.distinct().reduce(Int::times)
        repeat(rounds) {
            for (monkey in monkeysById.values) {
                inspectCount[monkey.id] = inspectCount.getOrDefault(monkey.id, 0) + monkey.items.size
                repeat(monkey.items.size) {
                    var worryLevel = monkey.items.removeFirst()
                    val operationValue =
                        if (monkey.operationValue == "old") worryLevel else monkey.operationValue.toLong()
                    if (monkey.operation == Operation.MULTIPLY) {
                        worryLevel *= operationValue
                    } else {
                        worryLevel += operationValue
                    }
                    if (limitWithDivision) {
                        worryLevel = floor(worryLevel / 3f).toLong()
                    } else {
                        worryLevel %= divisorLimit
                    }
                    val targetMonkey =
                        if (worryLevel % monkey.testDivider == 0L) monkey.throwIfTrue else monkey.throwIfFalse
                    monkeysById[targetMonkey]!!.items.addLast(worryLevel)
                }
            }
        }
        return inspectCount.values.sortedDescending().take(2).let { it[0] * it[1] }
    }

    data class Monkey(
        val id: Int,
        val items: LinkedList<Long>,
        val operation: Operation,
        val operationValue: String,
        val testDivider: Int,
        val throwIfTrue: Int,
        val throwIfFalse: Int,
    )

    private fun parseMonkey(monkeyString: String): Monkey {
        val monkeyLines = monkeyString.lines()
        return Monkey(
            id = numberRegex.find(monkeyLines[0])!!.value.toInt(),
            items = LinkedList(numberRegex.findAll(monkeyLines[1]).map { it.value.toLong() }.toList()),
            operation = if (monkeyLines[2].contains("*")) Operation.MULTIPLY else Operation.ADD,
            operationValue = monkeyLines[2].substring(monkeyLines[2].indexOfLast { it == ' ' } + 1,
                monkeyLines[2].length),
            testDivider = numberRegex.find(monkeyLines[3])!!.value.toInt(),
            throwIfTrue = numberRegex.find(monkeyLines[4])!!.value.toInt(),
            throwIfFalse = numberRegex.find(monkeyLines[5])!!.value.toInt(),
        )
    }

    enum class Operation {
        MULTIPLY, ADD
    }
}
