fun main() {
    val input = getInput()
    log(
        part01a(input),
        part01b(input)
    )
}

fun part01a(input: String): Int {
    return input.count { it == '(' } - input.count { it == ')' }
}

fun part01b(input: String): Int {
    var floor = 0
    var position = 0
    while (floor >= 0) {
        floor += if (input[position] == '(') 1 else -1
        position++
    }
    return position
}
