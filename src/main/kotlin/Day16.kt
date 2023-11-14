import java.lang.Integer.parseInt

fun main() {
    val input = getInputs()
    val sues = getSues(input)
    val gift = mapOf(
        Pair("children", 3),
        Pair("cats", 7),
        Pair("samoyeds", 2),
        Pair("pomeranians", 3),
        Pair("akitas", 0),
        Pair("vizslas", 0),
        Pair("goldfish", 5),
        Pair("trees", 3),
        Pair("cars", 2),
        Pair("perfumes", 1)
    )

    log(
        findSue(sues, gift) { partOneMatcher() },
        findSue(sues, gift, ::partTwoMatcher)
    )
}

private fun findSue(sues: Map<Int, Map<String, Int>>, gift: Map<String, Int>, matcher: (compound: String) -> (Int, Int) -> Boolean): Int {
    return sues.filterValues { match(it, gift, matcher) }.iterator().next().key
}

private fun match(sue: Map<String, Int>, gift: Map<String, Int>, matcher: (compound: String) -> (Int, Int) -> Boolean): Boolean {
    return gift.keys.all { !sue.contains(it) || matcher(it)(sue[it]!!, gift[it]!!) }
}

private fun partOneMatcher(): (Int, Int) -> Boolean {
    return { n1, n2 -> n1 == n2 }
}

private fun partTwoMatcher(compound: String): (Int, Int) -> Boolean {
    return when(compound) {
        "cats", "trees" -> { sue, gift -> sue > gift }
        "pomeranians", "goldfish" -> { sue, gift -> sue < gift }
        else -> { n1, n2 -> n1 == n2 }
    }
}

private fun getSues(lines: List<String>): Map<Int, Map<String, Int>> {
    val regex = Regex("Sue (\\d*): (\\w*): (\\d*), (\\w*): (\\d*), (\\w*): (\\d*)")

    return lines.map {regex.find(it)!!.groupValues }
        .associateBy({ parseInt(it[1]) }, { mapOf(
            Pair(it[2], parseInt(it[3])),
            Pair(it[4], parseInt(it[5])),
            Pair(it[6], parseInt(it[7]))
        ) })
}
