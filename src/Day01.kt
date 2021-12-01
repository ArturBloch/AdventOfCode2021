fun main() {

    // https://adventofcode.com/2021/day/1

    fun part1(input: List<String>): Int {
        var counter = 0
        var prevInput = Int.MAX_VALUE
        for (s in input) {
            val currentInput = s.toInt()
            if (currentInput > prevInput) {
                counter++
            }
            prevInput = currentInput
        }
        return counter
    }

    fun part2(input: List<String>): Int {
        var counter = 0
        var slidingWindows = IntArray(input.size + 1)
        for (i in input.indices){
            val currentMeasurement = input[i].toInt()
            slidingWindows[i] += currentMeasurement
            for (j in 1..2) {
                if(i - j >= 0) slidingWindows[i - j] += currentMeasurement
            }
            if(i >= 3 && slidingWindows[i - 2] > slidingWindows[i - 3]){
                counter++
            }
        }
        return counter
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

