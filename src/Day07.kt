import kotlin.math.abs

fun main() {

    // https://adventofcode.com/2021/day/7

    fun part1(input: List<String>): Int {
        var crabPositions = input[0].split(",").map(Integer::parseInt).toIntArray()
        var maxPosition = crabPositions.maxOrNull()

        var minimumSteps = Int.MAX_VALUE
        for(i in 0..maxPosition!!){
            var currentSteps = 0
            for(j in crabPositions.indices){
                currentSteps = currentSteps + abs(crabPositions[j] - i)
            }
            if(currentSteps < minimumSteps) minimumSteps = currentSteps
        }
        return minimumSteps
    }

    fun gaussSum(steps: Int): Long{
        return (steps * (steps + 1L)) / 2L
    }

    fun part2(input: List<String>): Long {
        var crabPositions = input[0].split(",").map(Integer::parseInt).toIntArray()
        var maxPosition = crabPositions.maxOrNull()

        var minimumSteps = Long.MAX_VALUE
        for(i in 0..maxPosition!!){
            var currentSteps: Long = 0
            for(j in crabPositions.indices){
                currentSteps += gaussSum(abs(crabPositions[j] - i))
            }
            if(currentSteps < minimumSteps) minimumSteps = currentSteps
        }
        return minimumSteps
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    println(part1(testInput) == 37)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(testInput) == 168L)
    println(part2(input))
}

