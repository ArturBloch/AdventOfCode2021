fun main() {

    // https://adventofcode.com/2021/day/9
    val neighbours: List<Pair<Int, Int>> = listOf(Pair(0, 1), Pair(-1, 0), Pair(1, 0), Pair(0, -1))

    class HeightPoint(_x: Int, _y: Int, _value: Int) {
        val x: Int
        val y: Int
        var value: Int

        init {
            x = _x
            y = _y
            value = _value
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as HeightPoint

            if (x != other.x) return false
            if (y != other.y) return false
            if (value != other.value) return false

            return true
        }

        override fun hashCode(): Int {
            var result = x
            result = 31 * result + y
            result = 31 * result + value
            return result
        }
    }

    fun withinBoundsPart1(y: Int, x: Int, heightMap: Array<Array<Int>>): Boolean {
        return y >= 0 && y < heightMap.size && x >= 0 && x < heightMap[0].size;
    }

    fun withinBoundsPart2(y: Int, x: Int, heightMap: Array<Array<HeightPoint?>>): Boolean {
        return y >= 0 && y < heightMap.size && x >= 0 && x < heightMap[0].size;
    }

    fun part1(inputs: List<String>): Int {
        var heightMap = Array(inputs.size) { Array(inputs[0].length) { 0 } }
        for (y in inputs.indices) {
            for (x in inputs[y].indices) {
                heightMap[y][x] = Integer.parseInt(inputs[y][x].toString())
            }
        }

        var riskLevelSum = 0

        for (y in heightMap.indices) {
            for (x in heightMap[y].indices) {
                var lowPoint = true
                for (neighbour in neighbours) {
                    if (withinBoundsPart1(y + neighbour.first, x + neighbour.second, heightMap) &&
                        heightMap[y][x] >= heightMap[y + neighbour.first][x + neighbour.second]
                    ) {
                        lowPoint = false
                        break
                    }
                }
                if (lowPoint) {
                    riskLevelSum += (heightMap[y][x] + 1)
                }
            }
        }
        println(riskLevelSum)
        return riskLevelSum
    }

    fun part2(inputs: List<String>): Int {
        var heightMap = Array(inputs.size) { arrayOfNulls<HeightPoint>(inputs[0].length) }
        for (y in inputs.indices) {
            for (x in inputs[y].indices) {
                heightMap[y][x] = HeightPoint(x, y, Integer.parseInt(inputs[y][x].toString()))
            }
        }

        var riskLevelSum: MutableList<Int> = mutableListOf()
        for (y in heightMap.indices) {
            for (x in heightMap[y].indices) {
                if (heightMap[y][x]!!.value == 9) continue
                val floodFillQueue = ArrayDeque<HeightPoint>()
                val visitedBasin: MutableSet<HeightPoint> = HashSet()
                var localBasinSize = 0
                floodFillQueue.add(heightMap[y][x]!!)
                visitedBasin.add(heightMap[y][x]!!)
                while (!floodFillQueue.isEmpty()) {
                    val nextCell = floodFillQueue.removeFirst()
                    localBasinSize++
                    for (neighbour in neighbours) {
                        if (withinBoundsPart2(nextCell.y + neighbour.first, nextCell.x + neighbour.second, heightMap)) {
                            val neighbourCell = heightMap[nextCell.y + neighbour.first][nextCell.x + neighbour.second]!!
                            if (neighbourCell in visitedBasin || neighbourCell.value == 9) continue
                            floodFillQueue.add(neighbourCell)
                            visitedBasin.add(neighbourCell)
                            neighbourCell.value = 9
                        }
                    }
                }
               riskLevelSum.add(localBasinSize)
            }
        }
        riskLevelSum.sortDescending()
        return riskLevelSum.take(3).reduce{acc, i -> acc * i}
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    println(part1(testInput) == 15)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(testInput) == 1134)
    println(part2(input))
}
