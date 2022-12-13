package aoc.y2022

import aoc.Puzzle
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.filter

fun main() {
    Puzzle13().runAndProfile()
}

class Puzzle13 : Puzzle(2022, 13) {

    override fun run() {
        val packets = inputGroups.mapIndexed { i, packets -> i + 1 to parsePacketPair(packets) }
        val sortedPackets = packets.filter { (_, v) -> v.first <= v.second }
        println(sortedPackets.sumOf { (i, _) -> i })

        val dividerTwo = parsePacket("[[2]]").second
        val dividerSix = parsePacket("[[6]]").second

        val allPackets = packets.map { (_, packetPair) -> listOf(packetPair.first, packetPair.second) }.flatten()
        val allPacketsPlusDividersSorted = (allPackets + dividerTwo + dividerSix).sorted()
        println(
            (allPacketsPlusDividersSorted.indexOf(dividerTwo) + 1) *
                    (allPacketsPlusDividersSorted.indexOf(dividerSix) + 1)
        )
    }

    private fun parsePacketPair(packetsString: String): Pair<PacketData, PacketData> {
        val (leftString, rightString) = packetsString.lines()
        return parsePacket(leftString).second to parsePacket(rightString).second
    }

    private fun parsePacket(packet: String): Pair<Int, PacketData> {
        if (packet[0] == '[') {
            // parsing sublist
            var i = 1
            val packets = mutableListOf<PacketData>()
            while (i < packet.lastIndex) {
                if (packet[i] == ']') break
                val (len, p) = parsePacket(packet.substring(i))
                packets += p
                i += len
                if (packet[i] == ',') i++ // skip comma
            }
            return (i + 1) to PacketData.List(packets)
        } else {
            // parsing token
            val tokenEnd = packet.indexOfFirst { !it.isDigit() }
            return tokenEnd to PacketData.Value(packet.substring(0 until tokenEnd).toInt())
        }
    }

    private sealed interface PacketData : Comparable<PacketData> {
        data class List(val data: kotlin.collections.List<PacketData>) : PacketData {
            override fun compareTo(other: PacketData): Int = when (other) {
                is List -> data.zip(other.data).map { (l, r) -> l.compareTo(r) }.firstOrNull { it != 0 }
                    ?: data.size.compareTo(other.data.size)
                is Value -> compareTo(List(listOf(other)))
            }
        }

        data class Value(val value: Int) : PacketData {
            override fun compareTo(other: PacketData): Int = when (other) {
                is List -> List(listOf(this)).compareTo(other)
                is Value -> {
                    value.compareTo(other.value)
                }
            }
        }
    }
}
