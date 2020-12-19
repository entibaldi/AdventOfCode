package aoc.y2020

import aoc.Puzzle

fun main() {
    Puzzle19().runAndProfile()
}

data class Rule19(
    val id: Int,
    val regexString: String
) {
    fun buildRegexString(rules: Map<Int, Rule19>, isPart2: Boolean): String {
        val ors = regexString.split(" | ")
        return when {
            regexString == "\"a\"" -> "a"
            regexString == "\"b\"" -> "b"
            isPart2 && id == 8 -> {
                "(${rules[42]!!.buildRegexString(rules, isPart2)})+"
            }
            isPart2 && id == 11 -> {
                val left = rules[42]!!.buildRegexString(rules, isPart2)
                val right = rules[31]!!.buildRegexString(rules, isPart2)
                "(${(1..4).joinToString("|") { i -> "($left{$i}$right{$i})" }})"
            }
            ors.size == 2 -> {
                val left = ors[0].regexFromIds(rules, isPart2)
                val right = ors[1].regexFromIds(rules, isPart2)
                "($left|$right)"
            }
            else -> "(${ors[0].regexFromIds(rules, isPart2)})"
        }
    }

    private fun String.regexFromIds(rules: Map<Int, Rule19>, isPart2: Boolean): String =
        split(" ").joinToString("") { rules[it.toInt()]!!.buildRegexString(rules, isPart2) }

}

class Puzzle19 : Puzzle(2020, 19) {

    override fun run() {
        val rules: Map<Int, Rule19> = inputGroups[0].lines().map {
            parseRule(it)
        }.associateBy { it.id }
        println(inputGroups[1].lines().count { rules[0]!!.buildRegexString(rules, false).toRegex().matches(it) })
        val part2 = inputGroups[1].lines().filter { rules[0]!!.buildRegexString(rules, true).toRegex().matches(it) }
        println(part2.size)
    }

    private fun parseRule(it: String): Rule19 = it.split(": ").let {
        Rule19(it[0].toInt(), it[1])
    }
}