package io.github.dmytrozinkevych.sudoku.solver

fun main() {
    val sudoku = Sudoku(
        arrayOf(
            arrayOf(5, 3, 0,   0, 7, 0,   0, 0, 0),
            arrayOf(6, 0, 0,   1, 9, 5,   0, 0, 0),
            arrayOf(0, 9, 8,   0, 0, 0,   0, 6, 0),

            arrayOf(8, 0, 0,   0, 6, 0,   0, 0, 3),
            arrayOf(4, 0, 0,   8, 0, 3,   0, 0, 1),
            arrayOf(7, 0, 0,   0, 2, 0,   0, 0, 6),

            arrayOf(0, 6, 0,   0, 0, 0,   2, 8, 0),
            arrayOf(0, 0, 0,   4, 1, 9,   0, 0, 5),
            arrayOf(0, 0, 0,   0, 8, 0,   0, 7, 9)
        )
    )
    println("Initial sudoku:")
    sudoku.print()

    println("Solution:")
    solve(sudoku)
    sudoku.print()
}

fun solve(sudoku: Sudoku) {
    val solutionStorage = mutableMapOf<Pair<Int, Int>, List<Int>>()

    for (iteration in 1..500) {
        var updateHappened = false
        for (rowIndex in 0..<sudoku.size) {
            for (colIndex in 0..<sudoku.size) {
                val n = sudoku.rows[rowIndex][colIndex]
                if (n != 0) {
                    continue
                }
                val rowCol = Pair(rowIndex, colIndex)
                val possibleSolutions = solutionStorage
                    .computeIfAbsent(rowCol) { (1..sudoku.size).toList() }
                        .filter { sudoku.fitsInPosition(it, rowIndex, colIndex) }

                if (possibleSolutions.size == 1) {
                    sudoku.rows[rowIndex][colIndex] = possibleSolutions.first()
                    updateHappened = true
                    solutionStorage.remove(rowCol)
                } else {
                    solutionStorage[rowCol] = possibleSolutions
                }
            }
        }
        if (!updateHappened) {
            println("Took ${iteration - 1} iterations to solve")
            break
        }
    }
}

fun Sudoku.fitsInPosition(number: Int, rowNumber: Int, colNumber: Int) =
    !existsInRow(number, rowNumber)
            && !existsInColumn(number, colNumber)
            && !existsInSquare(number, rowNumber, colNumber)

fun Sudoku.existsInRow(number: Int, rowNumber: Int) =
    rows[rowNumber].contains(number)

fun Sudoku.existsInColumn(number: Int, colNumber: Int) =
    rows.any { it[colNumber] == number }

fun Sudoku.existsInSquare(number: Int, rowNumber: Int, colNumber: Int): Boolean {
    val rowNumberMin = (rowNumber / widthOfSquare) * widthOfSquare
    val rowNumberMax = rowNumberMin + widthOfSquare - 1

    val colNumberMin = (colNumber / heightOfSquare) * heightOfSquare
    val colNumberMax = colNumberMin + heightOfSquare - 1

    for (rowIndex in rowNumberMin..rowNumberMax) {
        for (colIndex in colNumberMin..colNumberMax) {
            if (number == rows[rowIndex][colIndex]) {
                return true
            }
        }
    }
    return false
}



