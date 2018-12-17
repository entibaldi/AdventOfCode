package com.etibaldi.adventofcode

import io.data2viz.geom.Rect
import kotlin.math.min

fun Rect.area() = width + height

inline fun <T> List<T>.forEachComparison(action: (t1: T, t2: T) -> Unit) {
    if (this.isEmpty()) {
        return
    }
    subList(0, size - 1).forEachIndexed { i, t1 ->
        subList(i + 1, size).forEach { t2 ->
            action(t1, t2)
        }
    }

    subList(0, size - 1).map {  }
}

inline fun <T, R : Any> List<T>.mapEachComparison(transform: (t1: T, t2: T) -> R): List<R> {
    val destination = mutableListOf<R>()
    forEachComparison { t1, t2 -> destination.add(transform(t1, t2)) }
    return destination
}

fun List<CharArray>.countRepetitions(amount: Int): Int {
    return count { array ->
        array.groupBy { it }.any { it.value.size == amount }
    }
}

fun extractEqualChars(a1: CharArray, a2: CharArray): CharArray {
    val equalChars = mutableListOf<Char>()
    for (i in 0 until min(a1.size, a2.size)) {
        if (a1[i] == a2[i]) equalChars.add(a1[i])
    }
    return equalChars.toCharArray()
}

inline fun <T> List<T>.dropTwoWhile(predicate: (T, T) -> Boolean): List<T> {
    val result = mutableListOf<T>()
    if (isEmpty()) return result
    var skipNext = false
    subList(0, size - 1).forEachIndexed { i, t ->
        when {
            skipNext -> skipNext = false
            predicate(t, this[i + 1]) -> skipNext = true
            else -> result.add(t)
        }
    }
    if (!skipNext) result.add(last())
    return result
}

inline fun <T, R : Comparable<R>> Iterable<T>.allMinBy(selector: (T) -> R): List<T> {
    val result = mutableListOf<T>()
    val iterator = iterator()
    if (!iterator.hasNext()) return result
    val firstElem = iterator.next()
    result.add(firstElem)
    var minValue = selector(firstElem)
    while (iterator.hasNext()) {
        val e = iterator.next()
        val v = selector(e)
        if (minValue >= v) {
            if (minValue > v) result.clear()
            result.add(e)
            minValue = v
        }
    }
    return result
}