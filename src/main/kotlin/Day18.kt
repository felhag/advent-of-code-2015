fun main() {
    val input = getInputs()
    log(
        run(input, 100, false),
        run(input, 100, true)
    )
}

private fun run(input: List<String>, steps: Int, stuck: Boolean): Int {
    var grid = parseGrid(input, stuck)
//    draw(grid)
    repeat(steps) {
        grid = step(grid, stuck)
//        draw(grid)
    }
    return grid.sumOf { row -> row.count { it } }
}

private fun step(grid: Grid, stuck: Boolean): Grid {
    return grid.mapIndexed { rowIdx, row -> row.mapIndexed { colIdx, _ -> on(grid, rowIdx, colIdx, stuck) } }
}

private fun on(grid: Grid, row: Int, col: Int, stuck: Boolean): Boolean {
    if (isStuckCorner(stuck, grid.size, row, col)) {
        return true
    }
    val neighbours = grid.neighboursOn(row, col)
    return (grid[row][col] && neighbours in 2..3) || neighbours == 3
}

private fun parseGrid(lines: List<String>, stuck: Boolean): Grid {
    val size = lines.size
    return lines.mapIndexed { rowIdx, row -> row.mapIndexed { colIdx, col -> col == '#' || isStuckCorner(stuck, size, rowIdx, colIdx) } }
}

private fun isStuckCorner(stuck: Boolean, size: Int, row: Int, col: Int): Boolean {
    return stuck && (row == 0 || row == size - 1) && (col == 0 || col == size - 1)
}

private fun draw(grid: Grid) {
    grid.forEach { row ->
        row.forEach { print(if (it) '#' else '.') }
        print('\n')
    }
    println('\n')
}

private typealias Grid = List<List<Boolean>>
private fun Grid.validSpot(row: Int, col: Int): Boolean = row >= 0 && col >= 0 && row < size && col < size
private fun Grid.neighboursOn(row: Int, col: Int): Int = run {
    var on = 0
    for (r in row - 1..row + 1) {
        for (c in col - 1..col + 1) {
            if (validSpot(r, c) && (r != row || c != col) && this[r][c]) {
                on++
            }
        }
    }
    return on
}
