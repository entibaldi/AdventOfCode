package aoc.y2020

import aoc.Puzzle
import java.util.*

fun main() {
    Puzzle22().runAndProfile()
}

class Puzzle22 : Puzzle(2020, 22) {

    override fun run() {
        part1()
        part2()
    }

    private fun part1() {
        val deckA: LinkedList<Int> = LinkedList()
        val deckB: LinkedList<Int> = LinkedList()
        inputGroups[0].lines().drop(1).forEach { deckA.addLast(it.toInt()) }
        inputGroups[1].lines().drop(1).forEach { deckB.addLast(it.toInt()) }
        while (deckA.isNotEmpty() && deckB.isNotEmpty()) {
            val cardA = deckA.pop()
            val cardB = deckB.pop()
            if (cardA >= cardB) {
                deckA.addLast(cardA)
                deckA.addLast(cardB)
            } else {
                deckB.addLast(cardB)
                deckB.addLast(cardA)
            }
        }
        val winner = if (deckA.isNotEmpty()) deckA else deckB
        println(winner.reversed().withIndex().sumBy { (i, value) -> value * (i + 1) })
    }

    private fun part2() {
        val deckA: LinkedList<Int> = LinkedList()
        val deckB: LinkedList<Int> = LinkedList()
        inputGroups[0].lines().drop(1).forEach { deckA.addLast(it.toInt()) }
        inputGroups[1].lines().drop(1).forEach { deckB.addLast(it.toInt()) }
        val winner = playGame(0, deckA, deckB)
        val winnerDeck = if (winner == 0) deckA else deckB
        println(winnerDeck.reversed().withIndex().sumBy { (i, value) -> value * (i + 1) })
    }

    private fun playGame(gameId: Int, deckA: LinkedList<Int>, deckB: LinkedList<Int>): Int {
        var subGameId = gameId
        val previousRounds: MutableSet<Pair<List<Int>, List<Int>>> = mutableSetOf()
        while (deckA.isNotEmpty() && deckB.isNotEmpty()) {
            val newRound = deckA.toList() to deckB.toList()
            if (previousRounds.contains(newRound)) {
                return 0
            } else {
                previousRounds.add(newRound)
            }
            val cardA = deckA.pop()
            val cardB = deckB.pop()
            val winnerDraw = if (cardA <= deckA.size && cardB <= deckB.size) {
                playGame(++subGameId, LinkedList(deckA.subList(0, cardA)), LinkedList((deckB.subList(0, cardB))))
            } else {
                if (cardA >= cardB) 0 else 1
            }
            if (winnerDraw == 0) {
                deckA.addLast(cardA)
                deckA.addLast(cardB)
            } else {
                deckB.addLast(cardB)
                deckB.addLast(cardA)
            }
        }
        return if (deckA.isNotEmpty()) 0 else 1
    }
}