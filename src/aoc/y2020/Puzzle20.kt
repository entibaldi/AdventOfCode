package aoc.y2020

import aoc.Puzzle
import kotlin.math.roundToInt
import kotlin.math.sqrt

fun main() {
    Puzzle20().runAndProfile()
}

class Tile(
    val id: Int,
    val pixels: List<Boolean>
) {
    val size = sqrt(pixels.size.toDouble()).roundToInt()

    val borders: List<List<Boolean>> = listOf(
        pixels.subList(0, size).toList(), // top
        (1..size).map { pixels[it * size - 1] }, // right
        pixels.subList(size * (size - 1), size * size).reversed(), // bottom
        ((size - 1) downTo 0).map { pixels[it * size] } // left
    )

    fun countSeaMonsters(): Int {
        val monsterLength = 20
        return (1 until size - 1).sumBy { i ->
            (0 until size - monsterLength).count { j ->
                pixels[size * i + j] &&
                    pixels[size * (i + 1) + (j + 1)] &&
                    pixels[size * (i + 1) + (j + 4)] &&
                    pixels[size * i + (j + 5)] &&
                    pixels[size * i + (j + 6)] &&
                    pixels[size * (i + 1) + (j + 7)] &&
                    pixels[size * (i + 1) + (j + 10)] &&
                    pixels[size * i + (j + 11)] &&
                    pixels[size * i + (j + 12)] &&
                    pixels[size * (i + 1) + (j + 13)] &&
                    pixels[size * (i + 1) + (j + 16)] &&
                    pixels[size * i + (j + 17)] &&
                    pixels[size * i + (j + 18)] &&
                    pixels[size * (i - 1) + (j + 18)] &&
                    pixels[size * i + (j + 19)]
            }
        }
    }

    fun transformations(): Sequence<Tile> = sequence {
        var transformed = this@Tile
        yield(transformed)
        repeat(4) { times ->
            transformed = transformed.rotate()
            if (times < 3) yield(transformed)
        }
        // flip X
        transformed = transformed.flipX()
        yield(transformed)
        repeat(3) {
            transformed = transformed.rotate()
            yield(transformed)
        }
    }

    private fun rotate(): Tile {
        val rotatedPixels = MutableList(size * size) { false }
        for (i in 0..9) {
            for (j in 0..9) {
                rotatedPixels[size * j + (size - 1 - i)] = pixels[size * i + j]
            }
        }
        return Tile(id, rotatedPixels)
    }

    private fun flipX(): Tile {
        val flippedPixels = MutableList(size * size) { false }
        for (i in 0..9) {
            for (j in 0..9) {
                flippedPixels[size * i + (size - 1 - j)] = pixels[size * i + j]
            }
        }
        return Tile(id, flippedPixels)
    }

    override fun toString(): String {
        return pixels.chunked(10).map { list -> list.map { if (it) '#' else '.' }.joinToString("") }.joinToString("\n")
    }
}

class Puzzle20 : Puzzle(2020, 20) {

    lateinit var inputTiles: List<Tile>
    lateinit var inputTilesById: Map<Int, Tile>
    lateinit var remainingTiles: MutableMap<Int, Tile>
    lateinit var board: List<MutableList<Tile?>>

    override fun run() {
        inputTiles = inputGroups.map { tileString ->
            val id = "\\d+".toRegex().find(tileString.lines().first())!!.groupValues[0].toInt()
            val pixels = tileString.lines().drop(1).map { it.toCharArray().map { c -> c == '#' } }
                .flatten()
            Tile(id, pixels)
        }
        inputTilesById = inputTiles.associateBy { it.id }
        remainingTiles = inputTilesById.toMutableMap()
        val boardSize = sqrt(inputTiles.size.toDouble()).roundToInt()
        board = List(boardSize) { MutableList<Tile?>(boardSize) { null } }

        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                val tilesRemaining = remainingTiles.values.asSequence().map(Tile::transformations).flatten()
                val found = if (i == 0) {
                    // first row
                    if (j == 0) {
                        // find candidate for top-left corner
                        tilesRemaining.first { t ->
                            tilesRemaining.run {
                                noMatches(t.id, t.borders[0].reversed()) &&
                                    noMatches(t.id, t.borders[3].reversed())
                            }
                        }
                    } else {
                        val matchLeft = board[0][j - 1]!!.borders[1].reversed()
                        tilesRemaining.first { t -> matchLeft == t.borders[3] }
                    }
                } else {
                    val matchUp = (board[i - 1][j] ?: continue).borders[2].reversed()
                    tilesRemaining.firstOrNull { t -> matchUp == t.borders[0] }
                }
                if (found != null) {
                    board[i][j] = found
                    remainingTiles.remove(found.id)
                }
            }
        }

        println(board[0][0]!!.id.toLong() * board[0][boardSize - 1]!!.id * board[boardSize - 1][0]!!.id * board[boardSize - 1][boardSize - 1]!!.id)

        val gluedBoard = board.map { boardRow ->
            val tileSize = 10
            (1 until tileSize - 1).map { i ->
                boardRow.map { tile -> tile!!.pixels.subList(i * tileSize + 1, (i + 1) * tileSize - 1) }.flatten()
            }
        }.flatten().flatten()
        val gluedTile = Tile(1, gluedBoard)
        println(gluedTile.pixels.count { it } - 15 * gluedTile.countSeaMonsters())

    }

    private fun Sequence<Tile>.noMatches(id: Int, border: List<Boolean>): Boolean =
        none { otherTile ->
            otherTile.id != id && otherTile.transformations().any { otTransf -> otTransf.borders.any { it == border } }
        }

    private fun Sequence<Tile>.matches(id: Int, border: List<Boolean>): Boolean =
        any { otherTile ->
            otherTile.id != id && otherTile.transformations().any { otTransf -> otTransf.borders.any { it == border } }
        }
}