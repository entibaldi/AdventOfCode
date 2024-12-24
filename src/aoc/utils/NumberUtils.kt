package aoc.utils

fun Int.toDigits(base: Int = 10): List<Int> = sequence {
    var n = this@toDigits
    require(n >= 0)
    if (n == 0) yield(n)
    while (n != 0) {
        yield(n % base)
        n /= base
    }
}.toList()

fun Long.toDigits(base: Int = 10): List<Int> = sequence {
    var n = this@toDigits
    require(n >= 0)
    if (n == 0L) yield(0)
    while (n != 0L) {
        yield((n % base).toInt())
        n /= base
    }
}.toList()

fun <T> List<T>.pairTuples(): List<Pair<T,T>> = flatMapIndexed { i, a ->
    subList(i + 1, size).map { b -> a to b }
}
