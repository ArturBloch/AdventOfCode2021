import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList
import kotlin.math.ceil

fun main() {

    // https://adventofcode.com/2021/day/1

    fun extractMaximum(input: List<String>): Int {
        var MAX = 0;
        for (next in input) {
            val regex = "\\d+";
            val p: Pattern = Pattern.compile(regex);
            val m: Matcher = p.matcher(next);
            while (m.find()) {
                var num: Int = Integer.parseInt(m.group());
                if (num > MAX)
                    MAX = num;
            }
        }
        return MAX;
    }

    fun part1(input: List<String>): Int {
        var maxNumber = extractMaximum(input) + 1
        var seaMap = Array(maxNumber) { Array(maxNumber) { 0 } }
        for (line in input) {
            var data: List<String> = line.split(" -> ")
            var firstPoint = data[0].split(",")
            var x1 = Integer.parseInt(firstPoint[0])
            var y1 = Integer.parseInt(firstPoint[1])
            var secondPoint = data[1].split(",")
            var x2 = Integer.parseInt(secondPoint[0])
            var y2 = Integer.parseInt(secondPoint[1])
            if (x1 != x2 && y1 != y2) continue
            var fromY = if (y1 < y2) y1 else y2
            var toY = if (y1 > y2) y1 else y2
            var fromX = if (x1 < x2) x1 else x2
            var toX = if (x1 > x2) x1 else x2
            for (y in fromY..toY) {
                for (x in fromX..toX) {
                    seaMap[y][x]++
                }
            }
        }
        var counter = 0
        for (y in seaMap.indices) {
            for (x in seaMap[0].indices) {
                if (seaMap[y][x] >= 2) counter++
            }
        }
        println(counter)
        return counter
    }

    fun part2(input: List<String>): Int {
        var maxNumber = extractMaximum(input) + 1
        var seaMap = Array(maxNumber) { Array(maxNumber) { 0 } }
        for (line in input) {
            var data: List<String> = line.split(" -> ")
            var firstPoint = data[0].split(",")
            var x1 = Integer.parseInt(firstPoint[0])
            var y1 = Integer.parseInt(firstPoint[1])
            var secondPoint = data[1].split(",")
            var x2 = Integer.parseInt(secondPoint[0])
            var y2 = Integer.parseInt(secondPoint[1])
            var fromY = if (y1 < y2) y1 else y2
            var toY = if (y1 > y2) y1 else y2
            var fromX = if (x1 < x2) x1 else x2
            var toX = if (x1 > x2) x1 else x2
            if (x1 != x2 && y1 != y2) {
                for (i in 0..(toY - fromY)) {
                    val deltaX = if (x1 > x2) -1 else 1
                    val deltaY = if (y1 > y2) -1 else 1
                    seaMap[y1 + (i * deltaY)][x1 + (i * deltaX)]++
                }
            } else {
                for (y in fromY..toY) {
                    for (x in fromX..toX) {
                        seaMap[y][x]++
                    }
                }
            }
        }
        var counter = 0
        for (y in seaMap.indices) {
            for (x in seaMap[0].indices) {
                if (seaMap[y][x] >= 2) counter++
            }
        }
        println(counter)
        return counter
    }

    // test if implementation meets criteria from the description, like:
    var testInput = readInput("Day05_test")
    println(part1(testInput) == 5)
//
    var input = readInput("Day05")
    println(part1(input))
    println(part2(testInput) == 12)
    println(part2(input))
}

