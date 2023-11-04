import java.lang.StringBuilder

fun main() {
    val input = getInputs()
    log(
        part08a(input),
        part08b(input)
    )
}

fun part08a(input: List<String>): Int {
    val total = input.map(String::length).sum()
    val mem = input.map(::decodeLine).map(String::length).sum()
    return total - mem
}

fun part08b(input: List<String>): Int {
    val org = input.map(String::length).sum()
    val enc = input.map(::encodeLine).map(String::length).sum()
    return enc - org
}

private fun decodeLine(line: String): String {
    var i = 0
    val result = StringBuilder()
    while (i < line.length) {
        when (val char = line[i]) {
            '"' -> i++
            '\\' -> i += parseBackslash(line, i, result)
            else -> {
                result.append(char)
                i++
            }
        }
    }
    return result.toString()
}

private fun parseBackslash(line: String, idx: Int, result: StringBuilder): Int {
    return if (line[idx + 1] == '"' || line[idx + 1] == '\\') {
        result.append(line[idx + 1])
        2
    } else {
        result.append(decodeHex(line.substring(idx, idx + 4)))
        4
    }
}

private fun decodeHex(char: String): String {
    return byteArrayOf(char.substring(2).toInt(16).toByte()).toString(Charsets.ISO_8859_1)
}

private fun encodeLine(line: String): String {
    val result = StringBuilder()
    for (char in line) {
        when(char) {
            '"' -> result.append("\\\"")
            '\\' -> result.append("\\\\")
            else -> result.append(char)
        }
    }
    println("$line -> $result")
    return "\"$result\""
}
