package io.github.dmytrozinkevych.sudoku.solver

class Sudoku(
    val rows: Array<Array<Int>>
) {
    val widthOfSquare = 3
    val heightOfSquare = 3
    val size = rows.size

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

    fun print() {
        println(toString())
    }
}