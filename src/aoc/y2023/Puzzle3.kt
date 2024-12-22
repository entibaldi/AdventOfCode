package aoc.y2023

import aoc.Puzzle
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import java.lang.RuntimeException

fun main() {
    Puzzle3().runAndProfile()
}

class Puzzle3 : Puzzle(2023, 3) {

    override fun run() {
        val schematic = inputLines.map { it.toCharArray() }
        val partNumbers = mutableListOf<Long>()
        val notPartNumbers = mutableListOf<Long>()
        var candidateNumber: String? = null
        val width = schematic[0].size
        val height = schematic.size
        for (j in schematic.indices) {
            for (i in schematic[j].indices) {
                val c = schematic[j][i]
                if (c.isDigit()) candidateNumber = (candidateNumber ?: "") + c
                if ((!c.isDigit() || i == width - 1) && candidateNumber != null) {
                    val l = candidateNumber.length
                    val iRange = (i - 1 - l).coerceAtLeast(0)..i
                    if ((j > 0 && iRange.any { schematic[j - 1][it].isSymbol() }) ||
                        (j < height - 1 && iRange.any { schematic[j + 1][it].isSymbol() }) ||
                        (c.isDigit() && i > l && schematic[j][i - l].isSymbol()) ||
                        (i > l + 1 && schematic[j][i - 1 - l].isSymbol()) ||
                        (i < width && schematic[j][i].isSymbol())
                    ) {
                        partNumbers.add(candidateNumber.toLong())
                    } else {
                        notPartNumbers.add(candidateNumber.toLong())
                    }
                    candidateNumber = null
                }
            }
        }
        println(partNumbers.sum())
        var gearRatio = 0L
        for (j in schematic.indices) {
            for (i in schematic[j].indices) {
                val c = schematic[j][i]
                if (c == '*') {
                    val digitsAdjacents = listOfNotNull(
                        schematic.getOrNull(j - 1)?.getOrNull(i - 1),
                        schematic.getOrNull(j - 1)?.getOrNull(i),
                        schematic.getOrNull(j - 1)?.getOrNull(i + 1),
                        schematic.getOrNull(j)?.getOrNull(i - 1),
                        schematic.getOrNull(j)?.getOrNull(i + 1),
                        schematic.getOrNull(j + 1)?.getOrNull(i - 1),
                        schematic.getOrNull(j + 1)?.getOrNull(i),
                        schematic.getOrNull(j + 1)?.getOrNull(i + 1),
                    ).filter { it.isDigit() }
                    if (digitsAdjacents.size == 2) gearRatio += digitsAdjacents.fold(1L) { acc: Long, digit: Char ->
                        acc * digit.digitToInt()
                    }
                }
            }
        }
        println(gearRatio)
    }

    private fun Char.isSymbol(): Boolean = this != '.' && !isDigit()
}