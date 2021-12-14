import java.lang.IllegalArgumentException
import java.util.*
import java.util.stream.Collectors.counting
import kotlin.math.ceil


fun main() {

    // https://adventofcode.com/2021/day/14


    fun part1(inputs: List<String>): Int {
        var startingPolymer = inputs[0]
        var polymerBuilder = StringBuilder(startingPolymer)
        var currentPair = StringBuilder()
        var polymerMap:  MutableMap <String,String> = hashMapOf()
        for(i in 2 until inputs.size){
            val currentInput = inputs[i].split(" -> ")
            polymerMap[currentInput[0]] = currentInput[1]
        }
        println(polymerMap)
        for(i in 0..9){
            var fromIndex = 0
            var insertCounter = 0
            var newPolymer = StringBuilder(startingPolymer)
            while(fromIndex < startingPolymer.length - 1) {
                var pair = startingPolymer.substring(fromIndex, fromIndex + 2)
                if (polymerMap.contains(pair)) {
                    newPolymer.insert(fromIndex + insertCounter + 1, polymerMap[pair])
                    insertCounter++
                }
                fromIndex++
            }
            startingPolymer = newPolymer.toString()
        }

        val charsMap = mutableMapOf<Char, Int>()

        startingPolymer.forEach{
            charsMap[it] = charsMap.getOrDefault(it, 0) + 1
        }

        print(charsMap)
        println(charsMap.maxOf { it.value } - charsMap.minOf { it.value })
        return charsMap.maxOf { it.value } - charsMap.minOf { it.value }
    }

    fun part2(inputs: List<String>): Long {
        var startingPolymer = inputs[0]
        var pairMapCounter: MutableMap<String, Long> = hashMapOf()
        var polymerMap:  MutableMap <String, String> = hashMapOf()
        for(i in 2 until inputs.size){
            val currentInput = inputs[i].split(" -> ")
            polymerMap[currentInput[0]] = currentInput[1]
        }

        for(i in 0 until startingPolymer.length - 1){
            pairMapCounter.putIfAbsent(startingPolymer.substring(i, i + 2), 0L)
            pairMapCounter[startingPolymer.substring(i, i + 2)] = pairMapCounter.getOrDefault(startingPolymer.substring(i, i + 2), 0L) + 1
        }

        for(i in 0..39){
            var freshMapCounter: MutableMap<String, Long> = hashMapOf()
            for (mutableEntry in pairMapCounter) {
                if(!polymerMap.contains(mutableEntry.key)) throw IllegalArgumentException("WTF HAPPENED HERE")
                var counter = pairMapCounter[mutableEntry.key]!!
                pairMapCounter[mutableEntry.key] = 0
                var firstPair = mutableEntry.key[0] + polymerMap[mutableEntry.key]!!
                var secondPair = polymerMap[mutableEntry.key]!! + mutableEntry.key[1]
                freshMapCounter[firstPair] = freshMapCounter.getOrDefault(firstPair, 0L) + counter
                freshMapCounter[secondPair] = freshMapCounter.getOrDefault(secondPair, 0L) + counter
            }
            freshMapCounter.forEach {entry -> pairMapCounter[entry.key] = entry.value}
        }

        val charsMap = mutableMapOf<Char, Long>()

        pairMapCounter.forEach{
            charsMap[it.key[0]] = charsMap.getOrDefault(it.key[0], 0) + it.value
            charsMap[it.key[1]] = charsMap.getOrDefault(it.key[1], 0) + it.value
        }

        charsMap[startingPolymer[0]] = charsMap.getOrDefault(startingPolymer[0], 0) + 1
        // add corners like they'd be in 2 groups
        charsMap[startingPolymer[startingPolymer.length - 1]] = charsMap.getOrDefault(startingPolymer[startingPolymer.length - 1], 0) + 1
        var result = ceil(charsMap.maxOf { it.value } / 2.0 ) - ceil(charsMap.minOf { it.value } / 2.0)
        println("${charsMap.maxOf { it.value }} and ${charsMap.minOf { it.value } }")
        return result.toLong()
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    println(part1(testInput) == 1588)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(testInput) == 2188189693529)
    println(part2(input))
}