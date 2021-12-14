import java.awt.Point
import java.util.*
import java.util.regex.Matcher
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun main() {

    // https://adventofcode.com/2021/day/13
    var foldX: Int
    var foldY: Int

    class Point(var x: Int, var y: Int) : Comparable<Point> {

        fun foldOverX(foldValueX: Int) {
            if (x <= foldValueX) return
            val difference = x - foldValueX
            x = foldValueX - difference
        }

        fun foldOverY(foldValueY: Int) {
            if (y <= foldValueY) return
            val difference = y - foldValueY
            y = foldValueY - difference
        }

        override fun toString(): String {
            return "Point(x=$x, y=$y)"
        }

        override fun compareTo(other: Point): Int {
            if (x == other.x && y == other.y) return 0
            if (x > other.x) return 1
            if (x == other.x && y >= other.y) return 1
            return -1
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Point

            if (x != other.x) return false
            if (y != other.y) return false

            return true
        }

        override fun hashCode(): Int {
            var result = x
            result = 31 * result + y
            return result
        }
    }

    fun part1(inputs: List<String>): Int {
        val dotSpots: MutableList<Point> = mutableListOf()
        val foldAlongX: MutableList<Int> = mutableListOf()
        val foldAlongY: MutableList<Int> = mutableListOf()
        for (input in inputs) {
            if (input.isEmpty()) continue
            if (input.contains(",")) {
                val dotSplit = input.split(",")
                dotSpots.add(Point(Integer.parseInt(dotSplit[0]), Integer.parseInt(dotSplit[1])));
            } else {
                val regexTest = "[yx]=(\\d+)".toPattern()
                val matcher = regexTest.matcher(input)
                if (matcher.find()) {
                    val foldValue = Integer.parseInt(matcher.group(1))
                    if (input.contains("x")) {
                        foldAlongX.add(foldValue)
                        dotSpots.forEach { e -> e.foldOverX(foldValue) }
                        System.err.println(String.format("Folding over X %d", foldValue))
                    } else {
                        foldAlongY.add(foldValue)
                        dotSpots.forEach { e -> e.foldOverY(foldValue) }
                        System.err.println(String.format("Folding over Y %d", foldValue))
                    }
                }
            }
        }
        for (dotspotter in dotSpots.toHashSet().sorted()) {
            println(dotspotter)
        }
        return dotSpots.toHashSet().size
    }

    fun part2(inputs: List<String>) {
        val dotSpots: MutableList<Point> = mutableListOf()
        val foldAlongX: MutableList<Int> = mutableListOf()
        val foldAlongY: MutableList<Int> = mutableListOf()
        for (input in inputs) {
            if (input.isEmpty()) continue
            if (input.contains(",")) {
                val dotSplit = input.split(",")
                dotSpots.add(Point(Integer.parseInt(dotSplit[0]), Integer.parseInt(dotSplit[1])));
            } else {
                val regexTest = "[yx]=(\\d+)".toPattern()
                val matcher = regexTest.matcher(input)
                if (matcher.find()) {
                    val foldValue = Integer.parseInt(matcher.group(1))
                    if (input.contains("x")) {
                        foldAlongX.add(foldValue)
                        dotSpots.forEach { e -> e.foldOverX(foldValue) }
                        System.err.println(String.format("Folding over X %d", foldValue))
                    } else {
                        foldAlongY.add(foldValue)
                        dotSpots.forEach { e -> e.foldOverY(foldValue) }
                        System.err.println(String.format("Folding over Y %d", foldValue))
                    }
                }
            }
        }
        var result = dotSpots.toHashSet().sorted()
        var maxX = result.maxOf { e -> e.x }
        var maxY = result.maxOf { e -> e.y }
        var characterMap = Array(maxY + 1) { Array(maxX + 1) { 0 } }
        for (dot in result){
            characterMap[dot.y][dot.x] = 1
        }
        for(y in characterMap.indices){
            println("")
            for(x in characterMap[0].indices){
                print(if (characterMap[y][x] == 1) "###" else "...")
            }
        }
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    println(part1(testInput) == 17)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
