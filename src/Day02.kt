import java.util.*

fun main() {

    // https://adventofcode.com/2021/day/1

    fun part1(input: List<String>): Int {
        var x = 0
        var y = 0
        for (s in input) {
            val splitString = s.split(" ")
            when(splitString[0].uppercase(Locale.getDefault())) {
                "FORWARD" -> x += splitString[1].toInt()
                "DOWN" -> y += splitString[1].toInt()
                "UP" -> y -= splitString[1].toInt()
            }
        }
        return x * y
    }

    fun part2(input: List<String>): Int {
        var x = 0
        var y = 0
        var aim = 0
        for (s in input) {
            val splitString = s.split(" ")
            when(splitString[0].uppercase(Locale.getDefault())) {
                "FORWARD" -> {
                    val value = splitString[1].toInt()
                    x += value
                    y += (aim * value)
                }
                "DOWN" -> aim += splitString[1].toInt()
                "UP" -> aim -= splitString[1].toInt()
            }
        }
        return x * y
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

