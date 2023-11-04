import java.lang.StringBuilder

fun main() {
    val input = getInput()
    log(
        applyTimes(input, 40),
        applyTimes(input, 50)
    )
}

fun applyTimes(input: String, times: Int): Int {
    var line = input
    repeat(times) {
        line = parse(line)
    }
    return line.length
}

private fun parse(line: String): String {
    val result = StringBuilder()
    var i = 1
    var start = 0
    var char = line[0]
    while (i < line.length) {
        val next = line[i]
        if (next == char) {
            i++
        } else {
            result.append(i - start).append(char)
            char = next
            start = i
        }
    }
    result.append(i - start).append(char)
    return result.toString()
}
