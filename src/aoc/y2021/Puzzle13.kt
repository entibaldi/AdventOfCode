package aoc.y2021

import aoc.Puzzle

fun main() {
    Puzzle13().runAndProfile()
}

class Puzzle13 : Puzzle(2021, 13) {

    override fun run() {
        val paper: HashSet<Pair<Int, Int>> = inputGroups[0].lines().map { line ->
            line.split(",").let { it[0].toInt() to it[1].toInt() }
        }.toHashSet()
        val folds = inputGroups[1].lines().map { line ->
            line.removePrefix("fold along ").split("=").let {
                (it[0] == "x") to it[1].toInt()
            }
        }
        var folded = paper
        folded = foldPaper(folded, folds[0])
        println(folded.size)
        folds.subList(1, folds.size).forEach {
            folded = foldPaper(folded, it)
        }
        for (j in 0..folded.maxOf { it.second }) {
            for (i in 0..folded.maxOf { it.first }) {
                print(if (folded.contains(i to j)) "#" else ".")
            }
            println()
        }
    }

    private fun foldPaper(
        paper: HashSet<Pair<Int, Int>>,
        fold: Pair<Boolean, Int>
    ): HashSet<Pair<Int, Int>> {
        val (foldX, foldIndex) = fold
        val static = paper.filter {
            if (foldX) it.first < foldIndex else it.second < foldIndex
        }
        val folded = paper.filter {
            if (foldX) it.first > foldIndex else it.second > foldIndex
        }.map {
            if (foldX) {
                (2 * foldIndex - it.first) to it.second
            } else {
                it.first to (2 * foldIndex - it.second)
            }
        }
        return (static + folded).toHashSet()
    }
}
