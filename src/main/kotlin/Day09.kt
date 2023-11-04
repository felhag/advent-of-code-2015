import java.lang.Integer.parseInt

fun main() {
    val input = getInputs()
    log(
        calc(input, List<Int>::min),
        calc(input, List<Int>::max)
    )
}

private fun calc(input: List<String>, fnc: (list: List<Int>) -> Int): Int {
    val distances = parseLines(input)
    val cities = distances.keys.flatMap { t -> listOf(t.a, t.b) }.distinct()
    val result = cities.map { city -> calcPath(city, cities.without(city), 0, distances, fnc) }.toList()
    return fnc(result)
}

private fun calcPath(city: String, remaining: List<String>, distance: Int, distances: Map<Key, Int>, fnc: (list: List<Int>) -> Int): Int {
    if (remaining.isEmpty()) {
        return distance
    }
    return fnc(remaining.map { calcPath(
        it,
        remaining.without(it),
        distance +  distances[Key(city, it)]!!,
        distances,
        fnc)
    })
}

private fun parseLines(lines: List<String>): Map<Key, Int> {
    val regex = Regex("^(\\w*) to (\\w*) = (\\d*)\$")
    return lines.mapNotNull(regex::find)
        .map(MatchResult::groupValues)
        .associateBy({ Key(it[1], it[2]) }, { parseInt(it[3]) })
}

private fun <T> Iterable<T>.without(item: T): List<T> = filterIndexed { i, _ -> i != indexOf(item) }

class Key(val a: String, val b: String) {
    override fun hashCode(): Int {
        return a.hashCode() + b.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        return if (other !is Key) false else (other.a == a && other.b == b) || (other.b == a && other.a == b)
    }
}
