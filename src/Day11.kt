import java.lang.IllegalArgumentException

fun main() {

    // https://adventofcode.com/2021/day/11
    val neighbours: List<Pair<Int, Int>> =
        listOf(Pair(1, 1), Pair(-1, -1), Pair(-1, 1), Pair(1, -1), Pair(0, 1), Pair(-1, 0), Pair(1, 0), Pair(0, -1))

    fun withinBoundsPart(y: Int, x: Int, flashMap: Array<Array<Int>>): Boolean {
        return y >= 0 && y < flashMap.size && x >= 0 && x < flashMap[0].size;
    }

    fun initializeMap(inputs: List<String>): Array<Array<Int>> {
        var flashMap = Array(10) { Array(10) { 0 } }
        for (y in inputs.indices) {
            for (x in inputs[y].indices) {
                flashMap[y][x] = inputs[y][x].digitToInt()
            }
        }
        return flashMap
    }

    fun part1(inputs: List<String>): Int {
        var energyMap: Array<Array<Int>> = initializeMap(inputs)
        val simulations = 99
        var flashCounter = 0
        for (i in 0..simulations) {
            var flashMap: Array<Array<Int>> = Array(10) { Array(10) { 0 } }
            for (y in energyMap.indices) {
                for (x in energyMap[0].indices) {
                    energyMap[y][x]++
                }
            }
            while (true) {
                var stillFlashing = false
                for (y in energyMap.indices) {
                    for (x in energyMap[0].indices) {
                        if (energyMap[y][x] > 9 && flashMap[y][x] == 0) {
                            stillFlashing = true
                            flashMap[y][x] = 1
                            flashCounter++
                            for(neighbour in neighbours){
                                if(withinBoundsPart(y + neighbour.first, x + neighbour.second, flashMap)){
                                    energyMap[y+neighbour.first][x+neighbour.second]++
                                }
                            }
                        }
                    }
                }
                if (!stillFlashing){
                    for (y in energyMap.indices) {
                        for (x in energyMap[0].indices) {
                            if(flashMap[y][x] == 1) energyMap[y][x] = 0
                        }
                    }
                    break
                }
            }
        }

        return flashCounter
    }

    fun part2(inputs: List<String>): Int {
        var energyMap: Array<Array<Int>> = initializeMap(inputs)
        var waitingForSimulatedFlash = true
        var simulations = 0
        while (waitingForSimulatedFlash) {
            simulations++
            var flashMap: Array<Array<Int>> = Array(10) { Array(10) { 0 } }
            for (y in energyMap.indices) {
                for (x in energyMap[0].indices) {
                    energyMap[y][x]++
                }
            }
            var flashCounter = 0
            while (true) {
                var stillFlashing = false
                for (y in energyMap.indices) {
                    for (x in energyMap[0].indices) {
                        if (energyMap[y][x] > 9 && flashMap[y][x] == 0) {
                            stillFlashing = true
                            flashMap[y][x] = 1
                            flashCounter++
                            for(neighbour in neighbours){
                                if(withinBoundsPart(y + neighbour.first, x + neighbour.second, flashMap)){
                                    energyMap[y+neighbour.first][x+neighbour.second]++
                                }
                            }
                        }
                    }
                }
                if(flashCounter == 100) return simulations
                if (!stillFlashing){
                    for (y in energyMap.indices) {
                        for (x in energyMap[0].indices) {
                            if(flashMap[y][x] == 1) energyMap[y][x] = 0
                        }
                    }
                    break
                }
            }
        }

        throw IllegalArgumentException()
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    println(part1(testInput) == 1656)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(testInput) == 195)
    println(part2(input))
}
