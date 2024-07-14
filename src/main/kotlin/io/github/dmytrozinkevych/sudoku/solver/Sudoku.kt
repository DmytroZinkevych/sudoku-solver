package io.github.dmytrozinkevych.sudoku.solver

class Sudoku(
    val rows: Array<Array<Int>>
) {
    private val widthOfSquare = 3
    private val heightOfSquare = 3
    val size = rows.size

    fun getSquareRowRange(rowNumber: Int): IntRange {
        val rowNumberMin = (rowNumber / widthOfSquare) * widthOfSquare
        val rowNumberMax = rowNumberMin + widthOfSquare - 1
        return rowNumberMin..rowNumberMax
    }

    fun getSquareColRange(colNumber: Int): IntRange {
        val colNumberMin = (colNumber / heightOfSquare) * heightOfSquare
        val colNumberMax = colNumberMin + heightOfSquare - 1
        return colNumberMin..colNumberMax
    }

    fun isValid(): Boolean {
        val sudoku = this
        val rowsAreValid = sudoku.rows
            .asSequence()
            .map { it.toSet() }
            .all { it.size == sudoku.size && it.min() == 1 && it.max() == sudoku.size }

        val colsAreValid = (0..<sudoku.size)
            .asSequence()
            .map { col ->
                sudoku.rows
                    .asSequence()
                    .map { row -> row[col] }
                    .toSet()
            }
            .all { it.size == sudoku.size && it.min() == 1 && it.max() == sudoku.size }

        val squaresAreValid = (0..<sudoku.size step widthOfSquare)
            .asSequence()
            .flatMap { row ->
                (0..<sudoku.size step heightOfSquare)
                    .asSequence()
                    .map { col -> Pair(row, col) }
            }
            .map { rowCol ->
                (getSquareRowRange(rowCol.first))
                    .asSequence()
                    .flatMap { row ->
                        getSquareColRange(rowCol.second)
                            .asSequence()
                            .map { col -> sudoku.rows[row][col] }
                    }
                    .toSet()
            }
            .all { it.size == sudoku.size && it.min() == 1 && it.max() == sudoku.size }

        return rowsAreValid && colsAreValid && squaresAreValid
    }

    fun print() {
        println(toString())
    }

    override fun toString() =
        buildString {
            for (rowNumber in rows.indices) {
                if (rowNumber % heightOfSquare == 0) {
                    append(generateRowSeparator())
                }
                for (colNumber in rows[rowNumber].indices) {
                    if (colNumber % widthOfSquare == 0) {
                        append("| ")
                    }
                    append(formatNumber(rows[rowNumber][colNumber]))
                }
                appendLine("|")
                if (rowNumber == rows[rowNumber].size - 1) {
                    append(generateRowSeparator())
                }
            }
        }

    private fun generateRowSeparator() =
        StringBuilder().also {
            val rowSize = this.size
            for (colNumber in 1..rowSize) {
                if (colNumber % widthOfSquare == 1) {
                    it.append("+-")
                }
                it.append("--")
                if (colNumber == rowSize) {
                    it.appendLine("+")
                }
            }
        }

    private fun formatNumber(num: Int) =
        if (num == 0) {
            "_ "
        } else {
            "$num "
        }
}