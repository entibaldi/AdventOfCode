package aoc.y2020

import aoc.Puzzle

fun main() {
    Puzzle14().runAndProfile()
}

class Puzzle14 : Puzzle(2020, 14) {


    sealed class Instruction {
        class ValueMask(mask: String) : Instruction() {
            val orMask: Long = mask.split(" = ")[1].replace('X', '0').toLong(2)
            val andMask: Long = mask.split(" = ")[1].replace('X', '1').toLong(2)
        }

        class MemoryWrite(mask: String) : Instruction() {
            val address = "(\\d+)".toRegex().find(mask.split(" = ")[0])!!.groupValues[0].toLong()
            val value = mask.split(" = ")[1].toLong()
        }

        class AddressMask(val mask: String) : Instruction() {

            fun apply(address: Long): List<Long> {
                val maskBinaryString = mask.split(" = ")[1]
                val orMask: Long = maskBinaryString.replace('X', '0').toLong(2)
                val orAddress = address or orMask
                val constParts = mutableListOf<String>()
                var constPart = ""
                orAddress.toString(2)
                    .padStart(maskBinaryString.length, '0')
                    .toCharArray()
                    .forEachIndexed { i, c ->
                        if (maskBinaryString[i] == 'X') {
                            constParts.add(constPart)
                            constPart = ""
                        } else {
                            constPart += c
                        }
                        if (i == maskBinaryString.length - 1) {
                            constParts.add(constPart)
                        }
                    }
                val maxFloatingSize = maskBinaryString.count { it == 'X' }
                val maxFloatingBinary = Array(maxFloatingSize) { "1" }
                    .joinToString("").toInt(2)
                return (0..maxFloatingBinary).map { bin ->
                    val floatingParts = bin.toString(2).padStart(maxFloatingSize, '0')
                    constParts.withIndex()
                        .joinToString("") { (i, str) -> if (i in floatingParts.indices) str + floatingParts[i] else str }
                        .toLong(2)
                }
            }
        }
    }

    override fun run() {
        part1()
        part2()
    }

    private fun part1() {
        val instructions: List<Instruction> = inputLines.map {
            if (it.startsWith("mask")) {
                Instruction.ValueMask(it)
            } else {
                Instruction.MemoryWrite(it)
            }
        }
        execute(instructions)

    }

    private fun part2() {
        val instructions: List<Instruction> = inputLines.map {
            if (it.startsWith("mask")) {
                Instruction.AddressMask(it)
            } else {
                Instruction.MemoryWrite(it)
            }
        }
        execute(instructions)
    }

    private fun execute(instructions: List<Instruction>) {
        val memory = mutableMapOf<Long, Long>()
        var valueMask: Instruction.ValueMask? = null
        var addressMask: Instruction.AddressMask? = null
        instructions.forEach {
            when (it) {
                is Instruction.ValueMask -> {
                    valueMask = it
                    addressMask = null
                }
                is Instruction.MemoryWrite -> {
                    if (valueMask != null) {
                        memory[it.address] = it.value and valueMask!!.andMask or valueMask!!.orMask
                    } else if (addressMask != null) {
                        addressMask!!.apply(it.address).forEach { maskedAddress ->
                            memory[maskedAddress] = it.value
                        }
                    }
                }
                is Instruction.AddressMask -> {
                    addressMask = it
                    valueMask = null
                }
            }
        }
        println(memory.values.sum())
    }
}