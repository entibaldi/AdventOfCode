package com.entibaldi.adventofcode2020

import java.io.File

val passportRegexPart1 = """
    (byr:(\d+)(\s|${'$'}))|(iyr:(\d+)(\s|${'$'}))|(eyr:(\d+)(\s|${'$'}))|(hgt:(\w+)(\s|${'$'}))|(hcl:(#?\w+)(\s|${'$'}))|(ecl:(#?\w+)(\s|${'$'}))|(pid:(#?\w+)(\s|${'$'}))|(cid:(\d+)(\s|${'$'}))
""".trimIndent().toRegex()

val passportRegexPart2 = """
    (byr:(\d{4})(\s|${'$'}))|(iyr:(\d{4})(\s|${'$'}))|(eyr:(\d{4})(\s|${'$'}))|(hgt:(\d+)(cm|in)(\s|${'$'}))|(hcl:(#[0-9a-f]{6})(\s|${'$'}))|(ecl:(amb|blu|brn|gry|grn|hzl|oth)(\s|${'$'}))|(pid:(\w{9})(\s|${'$'}))|(cid:(\d+)(\s|${'$'}))
""".trimIndent().toRegex()

data class PassportPart1(
        val byr: String,
        val iyr: String,
        val eyr: String,
        val hgt: String,
        val hcl: String,
        val ecl: String,
        val pid: String,
        val cid: String
) {
    val isValid: Boolean
        get() = listOf(byr, iyr, eyr, hgt, hcl, ecl, pid).all { it.isNotBlank() }
}

data class PassportPart2(
        val byr: String,
        val iyr: String,
        val eyr: String,
        val hgt: String,
        val hgtUnit: String,
        val hcl: String,
        val ecl: String,
        val pid: String,
        val cid: String
) {
    val isValid: Boolean
        get() = listOf(byr, iyr, eyr, hgt, hgtUnit, hcl, ecl, pid).all { it.isNotBlank() } &&
                byr.toIntOrNull() in 1920..2002 &&
                iyr.toIntOrNull() in 2010..2020 &&
                eyr.toIntOrNull() in 2020..2030 &&
                (if (hgtUnit == "cm") hgt.toIntOrNull() in 150..193 else hgt.toIntOrNull() in 59..76)
}

fun main() {
    val passportData = File("res/4/input.txt").readText().split("[\\r]\\n[\\n\\r]+".toRegex())
    println("Part1 valid: ${passportData.map { it.toPassportPart1() }.count { it.isValid }}")
    println("Part2 valid: ${passportData.map { it.toPassportPart2() }.count { it.isValid }}")
}

private fun String.toPassportPart1(): PassportPart1 {
    val passportString = this.replace("\r\n", " ")
    val result: List<String> = passportRegexPart1.findAll(passportString)
            .map { it.groupValues }
            .reduce { list1, list2 -> list1.zip(list2) { s1, s2 -> s1 + s2 } }
    return PassportPart1(
            byr = result[2],
            iyr = result[5],
            eyr = result[8],
            hgt = result[11],
            hcl = result[14],
            ecl = result[17],
            pid = result[20],
            cid = result[23]
    )
}

private fun String.toPassportPart2(): PassportPart2 {
    val passportString = this.replace("\r\n", " ")
    val result: List<String> = passportRegexPart2.findAll(passportString)
            .map { it.groupValues }
            .reduce { list1, list2 -> list1.zip(list2) { s1, s2 -> s1 + s2 } }
    return PassportPart2(
            byr = result[2],
            iyr = result[5],
            eyr = result[8],
            hgt = result[11],
            hgtUnit = result[12],
            hcl = result[15],
            ecl = result[18],
            pid = result[21],
            cid = result[24]
    )
}