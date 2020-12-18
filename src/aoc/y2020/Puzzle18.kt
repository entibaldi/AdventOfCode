package aoc.y2020

import aoc.Puzzle

fun main() {
    Puzzle18().runAndProfile()
}

class Puzzle18 : Puzzle(2020, 18) {

    val parenthesisEval = "(\\([0-9+*\\s]+\\))".toRegex()
    val additionEval = "([0-9]+\\s\\+\\s[0-9]+)".toRegex()

    override fun run() {
        println(inputLines.map { it.evaluate(false) }.sum())
        println(inputLines.map { it.evaluate(true) }.sum())
    }

    private fun String.evaluate(additionFirst: Boolean): Long {
        var expression = this
        while (true) {
            val replace = parenthesisEval.replace(expression) { matchResult ->
                matchResult.groupValues[0]
                        .replace("(","")
                        .replace(")","")
                        .evaluateWithoutParenthesis(additionFirst)
                        .toString()
            }
            if (replace == expression) {
                break
            } else {
                expression = replace
            }
        }
        return expression.evaluateWithoutParenthesis(additionFirst)
    }

    private fun String.evaluateWithoutParenthesis(additionFirst: Boolean): Long {
        var expression = this
        if (additionFirst) {
            while (true) {
                val replace = additionEval.replace(expression) { matchResult ->
                    matchResult.groupValues[0].evaluateEager().toString()
                }
                if (replace == expression) {
                    break
                } else {
                    expression = replace
                }
            }
        }
        return expression.evaluateEager()
    }

    private fun String.evaluateEager(): Long {
        val terms = trim().split(" ")
        var initial = terms.first().toLong()
        terms.drop(1).windowed(2,2).forEach {
            if (it[0] == "+") initial += it[1].toLong() else initial *= it[1].toLong()
        }
        return initial
    }
}