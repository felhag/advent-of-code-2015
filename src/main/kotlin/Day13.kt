fun main() {
    val hapiness = parseLines(getInputs())
    log(
        part13a(hapiness),
        part13b(hapiness)
    )
}

fun part13a(input: Map<Pair<String, String>, Int>): Int {
    val names = input.keys.map(Pair<String, String>::first).distinct()
    return loop(input, emptyList(), names)
}

fun part13b(input: Map<Pair<String, String>, Int>): Int {
    val names = input.keys.map(Pair<String, String>::first).distinct() + "me"
    return loop(input, emptyList(), names)
}

private fun loop(hapiness: Map<Pair<String, String>, Int>, handled: List<String>, available: List<String>): Int {
    if (available.isEmpty()) {
        return (handled + handled[0]).zipWithNext()
            .sumOf { hapiness.getOrDefault(it, 0) + hapiness.getOrDefault(Pair(it.second, it.first), 0) }
    }

    return available.map { loop(hapiness, handled + it, available.without(it)) }.max()
}

private fun parseLines(lines: List<String>): Map<Pair<String, String>, Int> {
    return lines.map { Regex("(\\w*) would (gain|lose) (\\d*) happiness units by sitting next to (\\w*)")
        .find(it)!!
        .groupValues
    }.associateBy(
        { Pair(it[1], it[4]) },
        {if (it[2] == "lose") -Integer.parseInt(it[3]) else Integer.parseInt(it[3])}
    )
}

private fun <T> Iterable<T>.without(item: T): List<T> = filterIndexed { i, _ -> i != indexOf(item) }
