package aoc.y2021

import aoc.Puzzle
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.api.zeros
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.set
import org.jetbrains.kotlinx.multik.ndarray.operations.toIntArray

fun main() {
    Puzzle4().runAndProfile()
}

class Puzzle4 : Puzzle(2021, 4) {

    private class Board(
        val board: D2Array<Int>,
        val markings: D2Array<Int> = mk.zeros(board.shape[0], board.shape[1])
    ) {
        val rowsMarkings: Array<IntArray>
            get() = Array(markings.shape[0]) { i -> markings[i, 0..markings.shape[1]].toIntArray() }

        val colsMarkings: Array<IntArray>
            get() = Array(markings.shape[0]) { i -> markings[0..markings.shape[0], i].toIntArray() }

        fun isWinner(): Boolean = (rowsMarkings + colsMarkings).any { row -> row.all { it == 1 } }

        fun mark(value: Int) {
            for (i in board.multiIndices) {
                if (board[i[0], i[1]] == value) markings[i[0], i[1]] = 1
            }
        }

        fun calculateScore(lastMarked: Int): Int {
            var acc = 0
            for (i in board.multiIndices) {
                if (markings[i[0], i[1]] == 0) acc += board[i[0], i[1]]
            }
            return acc * lastMarked
        }
    }

    override fun run() {
        val groups = inputGroups
        val numbers = groups[0].split(",").map { it.toInt() }
        val boards = groups.subList(1, groups.size).map { boardText ->
            boardText.split("\n", "\r\n").map { row ->
                row.split(" ").filter { it.isNotBlank() }.map { n -> n.toInt() }
            }.flatten()
        }.map { board ->
            Board(mk.ndarray(board, intArrayOf(5, 5)))
        }
        val nonWinnerBoards = boards.toMutableList()
        for (n in numbers) {
            val iterator = nonWinnerBoards.listIterator()
            while (iterator.hasNext()) {
                val board = iterator.next()
                board.mark(n)
                if (board.isWinner()) {
                    println(board.calculateScore(n))
                    iterator.remove()
                }
            }
        }
    }
}