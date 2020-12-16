package aoc.y2020

import aoc.Puzzle

fun main() {
    Puzzle16().runAndProfile()
}

data class Rule(
    val name: String,
    val r1: IntRange,
    val r2: IntRange
)

data class Ticket(
    val values: List<Int>
)

class Puzzle16 : Puzzle(2020, 16) {

    lateinit var rules: List<Rule>
    lateinit var myTicket: Ticket
    lateinit var otherTickets: List<Ticket>

    override fun run() {
        parseInput()
        part1()
        part2()
    }

    private fun part1() {
        println(otherTickets.sumBy {
            it.values.filter { value ->
                rules.none { rule -> value in rule.r1 || value in rule.r2 }
            }.sum()
        })
    }

    private fun part2() {
        val validTickets = otherTickets.filter {
            it.values.all { value ->
                rules.any { rule -> value in rule.r1 || value in rule.r2 }
            }
        }
        val rulesIndexes = mutableMapOf<Rule, MutableList<Int>>()
        rules.forEach { rule ->
            val possibleIndexes = rules.indices.filter { i ->
                validTickets.all { ticket -> ticket.values[i] in rule.r1 || ticket.values[i] in rule.r2 }
            }
            rulesIndexes[rule] = possibleIndexes.toMutableList()
        }
        while (rulesIndexes.values.any { it.size > 1 }) {
            rulesIndexes.filterValues { values -> values.size == 1 }
                .forEach { (rule, singleValue) ->
                    rulesIndexes.filterKeys { key -> key != rule }
                        .values.forEach { values -> values.remove(singleValue.first()) }
                }
        }
        val indexesToCount = rulesIndexes.filterKeys { rule ->
            rule in rules.filter { it.name.startsWith("departure") }
        }.values.flatten()
        println(myTicket.values.withIndex()
            .filter { (i, _) -> i in indexesToCount }
            .fold(1L) { acc, (_, v) -> acc * v })
    }

    private fun parseInput() {
        rules = inputGroups[0].lines().map { line ->
            val ranges = line.split(": ")[1].split(" or ")
                .map { rangeStr ->
                    IntRange(
                        rangeStr.split("-")[0].trim().toInt(),
                        rangeStr.split("-")[1].trim().toInt()
                    )
                }
            Rule(
                name = line.split(": ")[0],
                r1 = ranges[0],
                r2 = ranges[1]
            )
        }
        myTicket = Ticket(inputGroups[1].lines()[1].split(",").map { it.toInt() })
        otherTickets = inputGroups[2].lineSequence().drop(1).map { line ->
            Ticket(line.split(",").map { it.toInt() })
        }.toList()
    }
}