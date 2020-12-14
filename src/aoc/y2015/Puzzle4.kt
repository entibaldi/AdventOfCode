package aoc.y2015

import aoc.Puzzle
import java.security.MessageDigest

fun main() {
    Puzzle4().runAndProfile()
}

class Puzzle4 : Puzzle(2015, 4) {

    val md5 = MessageDigest.getInstance("MD5")

    override fun run() {
        part1()
        part2()
    }

    private fun part1() {
        var hash: String? = null
        var i = 0
        while (hash == null || !hash.startsWith("00000")) {
            i++
            hash = md5.digest("ckczppom$i".toByteArray()).toHex()
        }
        println(i)
    }

    fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }

    private fun part2() {
        var hash: String? = null
        var i = 0
        while (hash == null || !hash.startsWith("000000")) {
            i++
            hash = md5.digest("ckczppom$i".toByteArray()).toHex()
        }
        println(i)
    }

}