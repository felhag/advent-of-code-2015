fun main() {
    val input = getInputs()
    log(
        part05a(input),
        part05b(input)
    )
}

fun part05a(input: List<String>): Int {
    return input.filter { isNice(it) }.size
}

fun part05b(input: List<String>): Int {
    return input.filter { isNicer(it) }.size
}

var vowels = listOf('a', 'e', 'i', 'u', 'o')
var subs = listOf("ab","cd","pq","xy")

private fun isNice(line: String): Boolean {
    return line.filter(vowels::contains).length >= 3 &&
            line.filterIndexed { idx, c -> idx > 0 && c == line[idx - 1] }.isNotEmpty() &&
            subs.none{ line.contains(it) }
}

private fun isNicer(line: String): Boolean {
    return line.filterIndexed { idx, c -> idx < line.length - 1 && line.lastIndexOf("" + c + line[idx + 1]) > idx + 1 }.isNotEmpty() &&
            line.filterIndexed { idx, c -> idx > 1 && line[idx - 2] == c}.isNotEmpty()
}
