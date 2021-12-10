fun main() {

    // https://adventofcode.com/2021/day/10
    var syntaxErrorMap: MutableMap<String, Int> = hashMapOf(")" to 3, "]" to 57, "}" to 1197, ">" to 25137)
    var autocompleteBonusSum: MutableMap<String, Int> = hashMapOf("(" to 1, "[" to 2, "{" to 3, "<" to 4)
    val matchingBrackets: Map<String, String> = hashMapOf("(" to ")", "[" to "]", "{" to "}", "<" to ">")

    fun part1(inputs: List<String>): Int {
        var errorSum = 0
        for(nextLine in inputs){
            val stack = ArrayDeque<String>()
            for(nextCharacter in nextLine.chunked(1)){
                if(nextCharacter in matchingBrackets.keys){
                    stack.add(nextCharacter)
                } else {
                    if(stack.isEmpty() || matchingBrackets[stack.removeLast()] != nextCharacter){
                        errorSum += syntaxErrorMap[nextCharacter]!!
                    }
                }
            }
        }
        return errorSum
    }

    fun part2(inputs: List<String>): Long {
        var allSums = mutableListOf<Long>()
        for(nextLine in inputs){
            var lineBonus = 0L
            val stack = ArrayDeque<String>()
            var errorLine = false
            for(nextCharacter in nextLine.chunked(1)){
                if(nextCharacter in matchingBrackets.keys){
                    stack.add(nextCharacter)
                } else {
                    if(stack.isEmpty() || matchingBrackets[stack.removeLast()] != nextCharacter){
                        errorLine = true
                        break
                    }
                }
            }
            if(!errorLine){
                for(character in stack.reversed()){
                    lineBonus = (lineBonus * 5L) + autocompleteBonusSum[character]!!
                }
                allSums.add(lineBonus)
            }
        }
        allSums.sortDescending()
        return allSums.removeAt(allSums.size/2)
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    println(part1(testInput) == 26397)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(testInput) == 288957L)
    println(part2(input))
}
