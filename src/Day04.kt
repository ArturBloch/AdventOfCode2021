import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ceil

fun main() {

    // https://adventofcode.com/2021/day/1
    lateinit var drawnNumbers: IntArray
    lateinit var boards: MutableList<Array<Array<Int>>>


    fun printBoard(board: Array<Array<Int>>){
        for (y in board.indices){
            println()
            for(x in board[0].indices){
                print(String.format("%d ", board[y][x]))
            }
        }
    }

    fun readBoardsAndNumbers(input: List<String>) {
        drawnNumbers = input[0].split(",").map(Integer::parseInt).toIntArray()
        boards = ArrayList()
        var currentBoard = Array(5) {Array(5) {0} }
        var row = 0
        for (currentIndex in 2 until input.size){
            if(input[currentIndex].isBlank()){
                boards.add(currentBoard)
                currentBoard = Array(5) {Array(5) {0} }
                row = 0
                continue
            }
            var column = 0
            for(number in input[currentIndex].trim().split("\\s+".toRegex())){
                currentBoard[row][column] = Integer.parseInt(number)
                column++
            }
            row++
        }
        boards.add(currentBoard)
    }

    fun checkRow(board: Array<Array<Int>>, y: Int) : Boolean {
        var winningCounter = 0

        for (x in board[0].indices){
            if (board[y][x] == -1){
                winningCounter++
            }
        }
        return winningCounter == board.size
    }

    fun checkColumn(board: Array<Array<Int>>, x: Int) : Boolean {
        var winningCounter = 0
        for (y in board.indices){
            if (board[y][x] == -1){
                winningCounter++
            }
        }
        return winningCounter == board.size
    }

    fun calculateWonBoard(board: Array<Array<Int>>, drawnNumber: Int): Int {
        println(String.format("Calculating win for board after number: %d", drawnNumber))
        var totalSum = 0
        for (y in board.indices){
            for(x in board[0].indices){
                if(board[y][x] != -1){
                    totalSum += board[y][x]
                }
            }
        }
        println(totalSum)
        println(totalSum * drawnNumber)
        return totalSum * drawnNumber
    }

    fun part1(input: List<String>): Int {
        readBoardsAndNumbers(input)
        for (drawnNumber in drawnNumbers) {
            for (board in boards){
                for (y in board.indices){
                    for (x in board[0].indices){
                        if(board[y][x] == drawnNumber){
                            board[y][x] = -1
                            if(checkRow(board, y) || checkColumn(board, x)) return calculateWonBoard(board, drawnNumber)
                        }
                    }
                }
            }
        }
        throw IllegalArgumentException("No winning board")
    }

    fun part2(input: List<String>): Int {
        readBoardsAndNumbers(input)
        var winningBoardArray = BooleanArray(boards.size)
        var winningBoardCount = 0
        for (drawnNumber in drawnNumbers) {
            for (index in boards.indices){
                var currentBoard = boards[index]
                for (y in currentBoard.indices){
                    for (x in currentBoard[0].indices){
                        if(currentBoard[y][x] == drawnNumber){
                            currentBoard[y][x] = -1
                            if(!winningBoardArray[index] && (checkRow(currentBoard, y) || checkColumn(currentBoard, x))){
                                winningBoardArray[index] = true
                                winningBoardCount++
                                if(winningBoardCount == boards.size){
                                    return calculateWonBoard(currentBoard, drawnNumber)
                                }
                            }
                        }
                    }
                }
            }
        }
        throw IllegalArgumentException("No winning board")
    }

    // test if implementation meets criteria from the description, like:
    var testInput = readInput("Day04_test")
    println(part1(testInput) == 4512)

    var input = readInput("Day04")
    println(part1(input))
    println(part2(testInput) == 1924)
    println(part2(input))
}

