import kotlin.math.pow

fun main() {

    // https://adventofcode.com/2021/day/8

    fun part1(inputs: List<String>): Int {
        var digitMap = HashMap<Int, String>()
        var digitCounterMap = HashMap<Int, Int>()
        for(input in inputs){
            var splitInput = input.split("|")
            var digitOutputValues = splitInput[1].trim().split(" ")
            for(decodedNumber in digitOutputValues){
                if(decodedNumber.length == 2){
                    digitMap.putIfAbsent(1, decodedNumber)
                    digitCounterMap[1] = digitCounterMap.getOrDefault(1, 0) + 1
                } else if(decodedNumber.length == 3){
                    digitMap.putIfAbsent(7, decodedNumber)
                    digitCounterMap[7] = digitCounterMap.getOrDefault(7, 0) + 1
                } else if(decodedNumber.length == 4){
                    digitMap.putIfAbsent(4, decodedNumber)
                    digitCounterMap[4] = digitCounterMap.getOrDefault(4, 0) + 1
                } else if(decodedNumber.length == 7){
                    digitMap.putIfAbsent(8, decodedNumber)
                    digitCounterMap[8] = digitCounterMap.getOrDefault(8, 0) + 1
                }
            }
        }
        return digitCounterMap.values.sum()
    }

    fun removeFromPotentialStringsAndAddToDigitMap(
        targetInt: Int,
        decodedInt: String,
        numberSets: HashMap<Int, MutableSet<String>>,
        finalMap: HashMap<String, Int>,
        segments: MutableList<String>
    ) {
        numberSets[targetInt]?.addAll(decodedInt.toCharArray().map { it.toString() })
        finalMap[decodedInt] = targetInt
        segments.remove(decodedInt)
    }

    fun part2(inputs: List<String>): Double {
        var numberSets = HashMap<Int, MutableSet<String>>()
        var finalMap = HashMap<String, Int>()
        var finalSum: Double = 0.0
        for(i in 0..9){
            numberSets[i] = HashSet()
        }
        for(input in inputs){
            finalMap.clear()
            for (next in numberSets) {
                numberSets[next.key]?.clear()
            }
            var splitString = input.split("|")
            var digits = splitString[1].trim().split(" ")
            var segments: MutableList<String> = splitString[0].trim().split(" ").toMutableList()
            val potentialOne = segments.first { it.length == 2 }
            removeFromPotentialStringsAndAddToDigitMap(1, potentialOne, numberSets, finalMap, segments)
            val potentialFour = segments.first { it.length == 4 }
            removeFromPotentialStringsAndAddToDigitMap(4, potentialFour, numberSets, finalMap, segments)
            val potentialSeven = segments.first { it.length == 3 }
            removeFromPotentialStringsAndAddToDigitMap(7, potentialSeven, numberSets, finalMap, segments)
            val potentialEight = segments.first { it.length == 7 }
            removeFromPotentialStringsAndAddToDigitMap(8, potentialEight, numberSets, finalMap, segments)
            val potentialNine = segments.first { it.length == 6 && it.chunked(1).containsAll(numberSets[4]!!) }
            removeFromPotentialStringsAndAddToDigitMap(9, potentialNine, numberSets, finalMap, segments)
            val potentialFive = segments.first { it -> it.length == 5 && it.chunked(1).containsAll(numberSets[9]!!.filterNot { it in numberSets[1]!! })}
            removeFromPotentialStringsAndAddToDigitMap(5, potentialFive, numberSets, finalMap, segments)
            val potentialZero = segments.first { it.length == 6 && it.chunked(1).containsAll(numberSets[1]!!) }
            removeFromPotentialStringsAndAddToDigitMap(0, potentialZero, numberSets, finalMap, segments)
            val potentialSix = segments.first { it.length == 6}
            removeFromPotentialStringsAndAddToDigitMap(6, potentialSix, numberSets, finalMap, segments)
            val potentialThree = segments.first { it -> it.length == 5 && it.chunked(1).containsAll(numberSets[1]!!) }
            removeFromPotentialStringsAndAddToDigitMap(3, potentialThree, numberSets, finalMap, segments)
            val potentialTwo = segments.first { it.length == 5 }
            removeFromPotentialStringsAndAddToDigitMap(2, potentialTwo, numberSets, finalMap, segments)

            var modifier = 10.0.pow((digits.size - 1).toDouble())
            var localSum: Double = 0.0
            for(digit in digits){
                var chunkedString = digit.chunked(1)
                for (numberSet in numberSets) {
                    if (chunkedString.size == numberSet.value.size && chunkedString.containsAll(numberSet.value)){
                        localSum += (modifier * numberSet.key)
                        modifier /= 10
                        break
                    }
                }
            }
            finalSum += localSum
        }
        println(finalSum)
        return finalSum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    println(part1(testInput) == 26)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(testInput) == 61229.0)
    println(part2(input))
}
