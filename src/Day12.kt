import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun main() {

    // https://adventofcode.com/2021/day/12
    var caveMap: MutableMap<String, MutableList<String>> = HashMap()

    fun recursivePathPart1(pathSoFar: List<String>, currentCave: String, pathCounter: Int): Int {
        var currentPathCounter = pathCounter
        val currentPath = pathSoFar.toMutableList()
        currentPath.add(currentCave)
        if (currentCave == "end") return 1
        val viablePaths = caveMap[currentCave]?.filter { cave ->
            cave != "start" && cave.uppercase(Locale.getDefault()) == cave || !currentPath.contains(cave)
        }
        if (viablePaths == null || viablePaths.isEmpty()) return 0
        for (nextCave in viablePaths) {
            currentPathCounter += recursivePathPart1(currentPath, nextCave, 0)
        }

        return currentPathCounter
    }

    fun recursivePathPart2(pathSoFar: List<String>, currentCave: String, repeatedCave: Boolean, pathCounter: Int): Int {
        var currentPathCounter = pathCounter
        val currentPath = pathSoFar.toMutableList()
        currentPath.add(currentCave)
        if (currentCave == "end") return 1
        val viablePaths = caveMap[currentCave]?.filter { cave ->
            cave != "start" && (cave.uppercase(Locale.getDefault()) == cave || !currentPath.contains(cave) || !repeatedCave)
        }
        if (viablePaths == null || viablePaths.isEmpty()) return 0
        for (nextCave in viablePaths) {
            if(nextCave.matches("[a-z]+".toRegex()) && pathSoFar.contains(nextCave)){
                currentPathCounter += recursivePathPart2(currentPath, nextCave, true,0)
            } else{
                currentPathCounter += recursivePathPart2(currentPath, nextCave, repeatedCave, 0)
            }
        }

        return currentPathCounter
    }

    fun readCaveMap(inputs: List<String>) {
        caveMap = HashMap()
        for (input in inputs) {
            val splitInput = input.split("-")
            caveMap.putIfAbsent(splitInput[0], ArrayList())
            caveMap.putIfAbsent(splitInput[1], ArrayList())
            caveMap[splitInput[0]]?.add(splitInput[1])
            caveMap[splitInput[1]]?.add(splitInput[0])
        }
    }

    fun part1(inputs: List<String>): Int {
        readCaveMap(inputs)
        var result = recursivePathPart1(ArrayList(), "start", 0)
        println(result)
        return result
    }

    fun part2(inputs: List<String>): Int {
        readCaveMap(inputs)
        var result = recursivePathPart2(ArrayList(), "start", false, 0)
        println(result)
        return result
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    println(part1(testInput) == 10)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(testInput) == 36)
    println(part2(input))
}
