package aoc.y2024

import aoc.Puzzle
import aoc.utils.Point
import aoc.utils.Vector
import aoc.utils.plus
import aoc.utils.toDigits


fun main() {
    Puzzle7().runAndProfile()
}

class Puzzle7 : Puzzle(2024, 7) {

    data class Operation(
        val result: Long,
        val operands: List<Long>,
        val hasConcatenation: Boolean = false
    ) {

        fun isValid(): Boolean {
            if (operands.isEmpty()) return false
            if (operands.size == 1) return result == operands.single()
            val lastOperand = operands.last()
            val remainOperands = operands.dropLast(1)

            return isAdditionValid(lastOperand, remainOperands) ||
                    isMultiplicationValid(lastOperand, remainOperands) ||
                    isConcatenationValid(lastOperand, remainOperands)
        }

        private fun isAdditionValid(
            lastOperand: Long,
            remainOperands: List<Long>,
        ): Boolean {
            val minusResult = result - lastOperand
            return minusResult >= 0 &&
                    copy(result = minusResult, operands = remainOperands).isValid()
        }

        private fun isMultiplicationValid(
            lastOperand: Long,
            remainOperands: List<Long>,
        ): Boolean {
            val modulo = result % lastOperand
            return modulo == 0L &&
                    copy(result = result / lastOperand, operands = remainOperands).isValid()
        }

        private fun isConcatenationValid(
            lastOperand: Long,
            remainOperands: List<Long>,
        ): Boolean {
            val resultDigits = result.toDigits()
            val lastOperandDigits = lastOperand.toDigits()
            val digitsCountDiff = resultDigits.size - lastOperandDigits.size
            val canConcatenate = if (hasConcatenation && digitsCountDiff > 0) {
                lastOperandDigits.withIndex().all { (i, d) ->
                    resultDigits[i] == d
                }
            } else {
                false
            }
            if (!canConcatenate) return false
            val newResult =
                resultDigits.takeLast(digitsCountDiff).reversed().joinToString("").toLong()
            return copy(result = newResult, operands = remainOperands).isValid()
        }
    }

    lateinit var operations: List<Operation>

    override fun run() {
        part1()
        part2()
    }

    private fun loadOperations(hasConcatenation: Boolean = false) {
        operations = inputLines.map { line ->
            line.split(": ").let { (result, operands) ->
                Operation(
                    result = result.toLong(),
                    operands = operands.split(" ").map { it.toLong() },
                    hasConcatenation = hasConcatenation
                )
            }
        }
    }

    private fun part1() {
        loadOperations()
        val validOperations = operations.filter { it.isValid() }
        println(validOperations.sumOf { it.result })
    }

    private fun part2() {
        loadOperations(hasConcatenation = true)
        val validOperations = operations.filter { it.isValid() }
        println(validOperations.sumOf { it.result })
    }
}
