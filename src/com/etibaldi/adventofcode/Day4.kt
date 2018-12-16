package com.etibaldi.adventofcode

import java.io.File

val asleepByGuardByMinute: MutableMap<Int, MutableMap<Int, Int>> = mutableMapOf()

/**
 * Created by krakk on 16/12/2018.
 */
fun main(args: Array<String>) {
    val guardEvents = File("4-input.txt")
        .readLines().map { it.toGuardEvent() }.sortedBy { it.date }
    var lastGuardId = GuardEvent.ID_UNKNOWN

    val assignedEvents = guardEvents.mapIndexed { i: Int, event: GuardEvent ->
        if (event.type == EventType.BEGIN_SHIFT) {
            lastGuardId = event.guardId
            event
        } else {
            event.copy(guardId = lastGuardId)
        }
    }


    var activeGuard = GuardEvent.ID_UNKNOWN
    var awake = true
    var asleepMin = 0
    var activeDay = ""
    assignedEvents.forEach {
        if (it.dayOfYear != activeDay) {
            // day has changed
            // there was guard asleep
            if (activeGuard > 0 && !awake) {
                addAsleepMinutesForGuard(activeGuard, asleepMin, 60)
                asleepMin = 0
            }
            activeDay = it.dayOfYear
        }
        when {
            it.type == EventType.BEGIN_SHIFT -> {
                activeGuard = it.guardId
            }
            it.type == EventType.ASLEEP -> {
                awake = false
                asleepMin = it.minutes
            }
            it.type == EventType.AWAKE -> {
                awake = true
                addAsleepMinutesForGuard(activeGuard, asleepMin, it.minutes)
            }
        }
    }

    val mostAsleepGuard = asleepByGuardByMinute.maxBy { it.value.values.sum() }!!.key
    val mostAsleepMinute = asleepByGuardByMinute[mostAsleepGuard]!!.maxBy { it.value }!!.key

    println(mostAsleepGuard * mostAsleepMinute)

    val mostAsleepGuardOfAnyMinute = asleepByGuardByMinute.maxBy { guardByMinutes ->
        guardByMinutes.value.maxBy { it.value }!!.value }!!.key
    val mostAsleepMinuteOverall = asleepByGuardByMinute[mostAsleepGuardOfAnyMinute]!!.maxBy { it.value }!!.key
    println(mostAsleepGuardOfAnyMinute * mostAsleepMinuteOverall)


}

fun addAsleepMinutesForGuard(activeGuard: Int, fromIncl: Int, toExcl: Int) {
    val minutesMap = asleepByGuardByMinute.getOrPut(activeGuard) { mutableMapOf() }
    for (i in fromIncl until toExcl)
        minutesMap.compute(i) { _, v -> (v ?: 0) + 1}
}

enum class EventType {
    BEGIN_SHIFT,
    AWAKE,
    ASLEEP
}

data class GuardEvent(
    val date: String,
    val type: EventType,
    val guardId: Int = ID_UNKNOWN
) {
    val dayOfYear: String = date.split(" ")[0]
    val hours: Int = regexDate.matchEntire(date)!!.groupValues[4].toInt()
    val minutes: Int = regexDate.matchEntire(date)!!.groupValues[5].toInt()

    companion object {
        const val ID_UNKNOWN = -1
        private val regexDate = Regex("(\\d*)-(\\d*)-(\\d*) (\\d*):(\\d*)")
    }
}

val guardRegex = Regex("\\[(.*)] (.*)")
val shiftRegex = Regex("Guard #(\\d*) begins shift")

private fun String.toGuardEvent(): GuardEvent {
    val groups = guardRegex.matchEntire(this)!!.groupValues
    val eventType = when {
        groups[2].contains("begins shift") -> EventType.BEGIN_SHIFT
        groups[2].contains("falls asleep") -> EventType.ASLEEP
        else -> EventType.AWAKE
    }
    return GuardEvent(
        groups[1],
        eventType,
        if (eventType == EventType.BEGIN_SHIFT)
            shiftRegex.matchEntire(groups[2])!!.groupValues[1].toInt()
        else GuardEvent.ID_UNKNOWN
    )
}
