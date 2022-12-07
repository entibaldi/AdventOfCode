package aoc.y2022

import aoc.Puzzle

fun main() {
    Puzzle7().runAndProfile()
}

class Puzzle7 : Puzzle(2022, 7) {

    override fun run() {
        val rootDir = parseFS(inputLines)
        println(computePart1(rootDir))
        println(computePart2(rootDir))
    }

    private fun parseFS(inputLines: List<String>): FsEntry.Dir {
        val rootDir = FsEntry.Dir("/")
        var currentDir = rootDir
        for (line in inputLines.drop(1)) {
            if (line.contains("\$ ls")) continue
            if (line.startsWith("\$ cd")) {
                val name = line.removePrefix("\$ cd").trim()
                if (name == "..") {
                    currentDir = currentDir.parentDir!!
                    continue
                }
                currentDir = currentDir.entries.filterIsInstance<FsEntry.Dir>().first { it.name == name }
                continue
            }
            val (first, second) = line.split(" ")
            if (first == "dir") {
                currentDir.entries.add(FsEntry.Dir(name = second, parentDir = currentDir))
            } else {
                currentDir.entries.add(FsEntry.File(name = second, size = first.toLong()))
            }
        }
        return rootDir
    }

    private fun computePart1(input: FsEntry.Dir): Long = input.dirList.filter { it.size <= 100000 }.sumOf { it.size }

    private fun computePart2(input: FsEntry.Dir): Long {
        val requiredSpace = 30000000 - (70000000 - input.size)
        return input.dirList.sortedBy { it.size }.first { it.size >= requiredSpace }.size
    }

    private sealed interface FsEntry {
        val size: Long

        data class Dir(
            val name: String,
            val parentDir: Dir? = null,
            val entries: MutableList<FsEntry> = mutableListOf()
        ) : FsEntry {
            override val size: Long
                get() = entries.sumOf { it.size }
            val dirList: List<Dir>
                get() = listOf(this) + entries.filterIsInstance<Dir>().map { it.dirList }.flatten()

            override fun toString(): String = (parentDir?.toString()?.plus("/") ?: "") + name
        }

        data class File(val name: String, override val size: Long) : FsEntry
    }
}
