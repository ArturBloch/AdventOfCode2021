import kotlin.collections.ArrayList

fun main() {

    // https://adventofcode.com/2021/day/6

    fun part1(input: List<String>): Int {
        var lanternFish: ArrayList<Int> = input[0].split(",").map(Integer::parseInt) as ArrayList<Int>
        val numberOfDays = 80
        for (i in 0 until numberOfDays) {
            var newFishDay = ArrayList<Int>()
            for (fish in lanternFish) {
                if(fish == 0){
                    newFishDay.add(6)
                    newFishDay.add(8)
                } else {
                    newFishDay.add(fish - 1)
                }
            }
            lanternFish = newFishDay
        }
        println(lanternFish.size)
        return lanternFish.size
    }

    fun part2(input: List<String>): Long {
        var lanternFish: ArrayList<Long> = input[0].split(",").map(String::toLong) as ArrayList<Long>
        var lanternFishMap: MutableMap<Int, Long> = HashMap()
        for(i in 0..8){
            lanternFishMap[i] = 0
        }
        for(fish in lanternFish){
            lanternFishMap[fish.toInt()] = lanternFishMap.getOrDefault(fish.toInt(), 0) + 1
        }

        val numberOfDays = 256
        for (i in 0 until numberOfDays) {
            val numberOfBreedingFish = lanternFishMap.getValue(0)
            lanternFishMap[0] = 0
            for(j in 1..8){
                lanternFishMap[j-1] = lanternFishMap.getValue(j)
            }
            lanternFishMap[6] = lanternFishMap.getValue(6) + numberOfBreedingFish
            lanternFishMap[8] = numberOfBreedingFish
        }
        return lanternFishMap.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    println(part1(testInput) == 5934)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(testInput) == 26984457539)
    println(part2(input))
}

