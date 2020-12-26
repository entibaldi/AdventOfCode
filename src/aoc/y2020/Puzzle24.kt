package aoc.y2020

import aoc.Puzzle

fun main() {
    Puzzle24().runAndProfile()
}

class Puzzle24 : Puzzle(2020, 24) {

    // e:1 ne:2 nw:3 w:4 sw:5 se:6
    private val tiles = mutableMapOf<XY, Side>()
    private val missingTiles = mutableMapOf<XY, Side>()
    private val flippingTiles = mutableSetOf<XY>()

    private data class XY(var x: Int = 0, var y: Int = 0) {
        constructor(xy: XY) : this(xy.x, xy.y)
    }

    private data class Side(var white: Boolean = true) {
        fun flip() {
            white = !white
        }
    }

    override fun run() {
        parseInput()
        part1()
        part2()
    }

    private fun parseInput() {
        inputLines.forEach { line ->
            val xy: XY = line.replace("ne", "2")
                .replace("nw", "3")
                .replace("sw", "5")
                .replace("se", "6")
                .replace("e", "1")
                .replace("w", "4")
                .toCharArray()
                .map { it - '0' }
                .fold(XY()) { xy, move ->
                    when (move) {
                        1 -> xy.x += 2
                        2 -> {
                            xy.x += 1
                            xy.y -= 1
                        }
                        3 -> {
                            xy.x -= 1
                            xy.y -= 1
                        }
                        4 -> xy.x -= 2
                        5 -> {
                            xy.x -= 1
                            xy.y += 1
                        }
                        6 -> {
                            xy.x += 1
                            xy.y += 1
                        }
                    }
                    xy
                }
            tiles.getOrPut(xy) { Side() }.flip()
        }
    }

    private fun part1() {
        println(tiles.values.count { !it.white })
    }

    private fun part2() {
        val xy = XY()
        repeat(100) {
            tiles.keys.forEach { tileXY ->
                xy.x = tileXY.x; xy.y = tileXY.y
                adjacentTranslations.fold(xy) { acc: XY, (tx, ty) ->
                    acc.x += tx; acc.y += ty
                    if (!tiles.containsKey(acc)) missingTiles[XY(acc)] = Side()
                    acc
                }
            }
            tiles.putAll(missingTiles)
            missingTiles.clear()
            tiles.forEach { (tileXY, side) ->
                xy.x = tileXY.x; xy.y = tileXY.y
                val blackAdjacent = countBlackTiles(xy)
                if ((side.white && blackAdjacent == 2) || (!side.white && (blackAdjacent == 0 || blackAdjacent > 2))) {
                    flippingTiles.add(tileXY)
                }
            }
            flippingTiles.forEach { tiles[it]!!.flip() }
            flippingTiles.clear()
        }
        println(tiles.values.count { !it.white })
    }

    private val adjacentTranslations = listOf(2 to 0, -1 to -1, -2 to 0, -1 to 1, 1 to 1, 2 to 0)

    private fun countBlackTiles(tileXY: XY): Int = adjacentTranslations.count { (tx, ty) ->
        tileXY.x += tx; tileXY.y += ty
        tiles[tileXY]?.white == false
    }.also { tileXY.x -= 1; tileXY.y -= 1 }

}