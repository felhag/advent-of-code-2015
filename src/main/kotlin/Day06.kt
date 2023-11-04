import java.lang.Integer.parseInt
import java.util.function.BiFunction
import kotlin.math.max

fun main() {
    val input = getInputs()
    log(
        part06a(input),
        part06b(input)
    )
}

fun part06a(input: List<String>): Int {
    val grid = Array(1000) { BooleanArray(1000).toTypedArray() }
    val handler = { cur: Boolean, task: String -> if (task == "toggle") !cur else task == "turn on" }
    input.forEach {parseLine(it, grid, handler)}
    return grid.flatMap { it.asIterable() }.count { a -> a }
}

fun part06b(input: List<String>): Int {
    val grid = Array(1000) { IntArray(1000).toTypedArray() }
    val handler = { cur: Int, task: String -> if (task == "toggle") cur + 2 else if (task == "turn on") cur + 1 else max(cur - 1, 0) }
    input.forEach {parseLine(it, grid, handler)}
    return grid.flatMap { it.asIterable() }.sum()
}

private fun <T> parseLine(line: String, grid: Array<Array<T>>, handler: BiFunction<T, String, T>) {
    val values = Regex("(turn on|turn off|toggle) (\\d*),(\\d*) through (\\d*),(\\d*)").find(line)!!.groupValues

    for (row in parseInt(values[2])..parseInt(values[4])) {
        for (col in parseInt(values[3])..parseInt(values[5])) {
            grid[row][col] = handler.apply(grid[row][col], values[1])
        }
    }
}
