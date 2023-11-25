fun main() {
    val re = Regex("(\\d+)")
    val values = re.findAll(getInput()).map{ it.value }.map(Integer::parseInt).toList()
    log(
        part25a(values[0] - 1, values[1] - 1),
        "-"
    )
}

private fun part25a(targetRow: Int, targetCol: Int): Long {
    var next = 20151125L
    var idx = 1
    var row = 0
    var col = 0
    val grid = mutableListOf(mutableListOf<Long>())

    while (true) {
        if (row == targetRow && col == targetCol) {
            return next
        }

        grid[row].add(next)
        next = next * 252533 % 33554393
        idx++

        if (row == 0) {
            grid.add(mutableListOf())
            row = grid.size - 1
            col = 0
        } else {
            row--
            col++
        }
        idx++
    }
}

private fun draw(grid: MutableList<MutableList<Long>>) {
    val size = 9
    println("   |" + List(grid[0].size) { idx -> (idx + 1).toString() }.joinToString{ " " + it.padEnd(size - 1) })
    println("---+" + grid[0].joinToString { "-".repeat(9) + "+" })
    for ((idx, row) in grid.withIndex()) {
        println(" ${idx + 1} | " + row.joinToString { it.toString().padStart(size) })
    }
}

