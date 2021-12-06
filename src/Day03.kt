import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.math.ceil

fun main() {

    // https://adventofcode.com/2021/day/3

    fun part1(input: List<String>): Int {
        var onBits = IntArray(input[0].length)
        for (s in input) {
            for (i in s.indices) {
                if (s[i] == '1') onBits[i]++
            }
        }
        return Integer.parseInt(onBits.joinToString("") {
            if (it > input.size / 2) "1"
            else "0"
        }, 2) * Integer.parseInt(onBits.joinToString("") {
            if (it > input.size / 2) "0"
            else "1"
        }, 2)
    }

    fun calculateOnBitsAtIndex(input: List<String>, index: Int): Int{
        var counter = 0
        for (s in input) {
            if (s[index] == '1') counter++
        }
        return counter
    }

    fun findOxygenGeneratorRating(input: List<String>): Int {
        var remainingList = input
        var currentIndex = 0
        while(true){
            val targetBit = if(calculateOnBitsAtIndex(remainingList, currentIndex) >= ceil(remainingList.size / 2.0)) '1' else '0'
            remainingList = remainingList.filter { e -> e[currentIndex] == targetBit }
            currentIndex++
            if(remainingList.size == 1) return Integer.parseInt(remainingList.get(0), 2)
        }
    }

    fun findCO2Scrubber(input: List<String>): Int {
        var remainingList = input
        var currentIndex = 0
        while(true){
            val targetBit = if(calculateOnBitsAtIndex(remainingList, currentIndex) >= ceil(remainingList.size / 2.0)) '0' else '1'
            remainingList = remainingList.filter { e -> e[currentIndex] == targetBit }
            currentIndex++
            if(remainingList.size == 1) return Integer.parseInt(remainingList.get(0), 2)
        }
    }

    fun part2(input: List<String>): Int {
        return findOxygenGeneratorRating(input) * findCO2Scrubber(input)
    }

    // test if implementation meets criteria from the description, like:
    var testInput = readInput("Day03_test")
    println(part1(testInput) == 198)

    var input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

