package aoc.y2020

import aoc.Puzzle

fun main() {
    Puzzle17().runAndProfile()
}

class Puzzle17 : Puzzle(2020, 17) {

    val turns = 6
    val boardSize = 8 + 2 * turns
    val initialIndex = turns


    inner class Part1 {

        private val board: Array<Array<BooleanArray>> =
            Array(boardSize) { Array(boardSize) { Array(boardSize) { false }.toBooleanArray() } }

        fun run() {
            // read input
            inputLines.map { it.toCharArray().map { c -> c == '#' } }
                .forEachIndexed { x, row ->
                    row.forEachIndexed { y, value ->
                        board[initialIndex + x][initialIndex + y][initialIndex] = value
                    }
                }
            val changes = mutableMapOf<Triple<Int, Int, Int>, Boolean>()
            for (i in 1..turns) {
                board.forEachElement { x, y, z, active ->
                    val activeNeighbors = countActiveNeighbours(x, y, z)
                    if (active && activeNeighbors !in 2..3) {
                        changes[x to y to z] = false
                    } else if (!active && activeNeighbors == 3) {
                        changes[x to y to z] = true
                    }
                }
                changes.forEach { (x, y, z), value ->
                    board[x][y][z] = value
                }
                changes.clear()
            }
            var totalActive = 0
            board.forEachElement { _, _, _, active -> if (active) totalActive++ }
            println(totalActive)
        }

        private fun countActiveNeighbours(x: Int, y: Int, z: Int): Int {
            var active = 0
            val indices = 0 until boardSize
            for (xi in x - 1..x + 1) {
                for (yi in y - 1..y + 1) {
                    for (zi in z - 1..z + 1) {
                        if (xi in indices && yi in indices && zi in indices &&
                            (xi != x || yi != y || zi != z) &&
                            board[xi][yi][zi]
                        ) {
                            active++
                        }
                    }
                }
            }
            return active
        }


        private fun Array<Array<BooleanArray>>.forEachElement(function: (x: Int, y: Int, z: Int, value: Boolean) -> Unit) {
            forEachIndexed { x, yArray ->
                yArray.forEachIndexed { y, zArray ->
                    zArray.forEachIndexed { z, value ->
                        function(x, y, z, value)
                    }
                }
            }
        }
    }


    inner class Part2 {

        private val board: Array<Array<Array<BooleanArray>>> =
            Array(boardSize) { Array(boardSize) { Array(boardSize) { Array(boardSize) { false }.toBooleanArray() } } }

        fun run() {
            // read input
            inputLines.map { it.toCharArray().map { c -> c == '#' } }
                .forEachIndexed { x, row ->
                    row.forEachIndexed { y, value ->
                        board[initialIndex + x][initialIndex + y][initialIndex][initialIndex] = value
                    }
                }
            val changes = mutableMapOf<Part2EntryIndexes<Int, Int, Int, Int>, Boolean>()
            for (i in 1..turns) {
                board.forEachElement { x, y, z, w, active ->
                    val activeNeighbors = countActiveNeighbours(x, y, z, w)
                    if (active && activeNeighbors !in 2..3) {
                        changes[x to y to z to w] = false
                    } else if (!active && activeNeighbors == 3) {
                        changes[x to y to z to w] = true
                    }
                }
                changes.forEach { (x, y, z, w), value ->
                    board[x][y][z][w] = value
                }
                changes.clear()
            }
            var totalActive = 0
            board.forEachElement { _, _, _, _, active -> if (active) totalActive++ }
            println(totalActive)
        }

        private fun countActiveNeighbours(x: Int, y: Int, z: Int, w: Int): Int {
            var active = 0
            val indices = 0 until boardSize
            for (xi in x - 1..x + 1) {
                for (yi in y - 1..y + 1) {
                    for (zi in z - 1..z + 1) {
                        for (wi in w - 1..w + 1) {
                            if (xi in indices && yi in indices && zi in indices && wi in indices &&
                                (xi != x || yi != y || zi != z || wi != w) &&
                                board[xi][yi][zi][wi]
                            ) {
                                active++
                            }
                        }

                    }
                }
            }
            return active
        }

        private infix fun <A, B, P : Pair<A, B>, C> P.to(that: C): Triple<A, B, C> = Triple(first, second, that)

        private fun Array<Array<Array<BooleanArray>>>.forEachElement(function: (x: Int, y: Int, z: Int, w: Int, value: Boolean) -> Unit) {
            forEachIndexed { x, yArray ->
                yArray.forEachIndexed { y, zArray ->
                    zArray.forEachIndexed { z, wArray ->
                        wArray.forEachIndexed { w, value -> function(x, y, z, w, value) }
                    }
                }
            }
        }
    }

    override fun run() {
        Part1().run()
        Part2().run()
    }

    data class Part2EntryIndexes<out A, out B, out C, out D>(val x: A, val y: B, val z: C, val w: D)

    private infix fun <A, B, P : Pair<A, B>, C> P.to(that: C): Triple<A, B, C> = Triple(first, second, that)
    private infix fun <A, B, C, T : Triple<A, B, C>, D> T.to(that: D): Part2EntryIndexes<A, B, C, D> =
        Part2EntryIndexes(first, second, third, that)
}


