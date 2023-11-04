fun main() {
    val input = getInput()
    log(
        part03a(input),
        part03b(input)
    )
}

fun part03a(route: String): Int {
    return getRoute(route).distinct().size
}

fun part03b(route: String): Int {
    return route
        .withIndex()
        .groupBy { k -> k.index % 2 }
        .values
        .map { l -> l.map(IndexedValue<Char>::value).joinToString(separator = "")}
        .map(::getRoute)
        .flatten()
        .distinct()
        .size
}

private fun getRoute(route: String): MutableList<Pair<Int, Int>> {
    return route.fold(mutableListOf(Pair(0, 0)), ::addPos)
}

private fun addPos(positions: MutableList<Pair<Int, Int>>, char: Char): MutableList<Pair<Int, Int>> {
    val newPos = newPos(char, positions[positions.size - 1])
    positions.add(newPos)
    return positions
}

private fun newPos(char: Char, current: Pair<Int, Int>): Pair<Int, Int> {
    return when (char) {
        '>' -> Pair(current.first, current.second + 1)
        '<' -> Pair(current.first, current.second - 1)
        '^' -> Pair(current.first - 1, current.second)
        'v' -> Pair(current.first + 1, current.second)
        else -> {
            println("Unknown character: $char");
            current
        }
    }
}
