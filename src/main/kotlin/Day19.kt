fun main() {
    val input = getInputs()
    val idx = input.indexOf("")
    val target = input[idx + 1]
    val replacements = getReplacements(input.subList(0, idx))
    log(
        part19a(target, replacements),
        part19b(target, replacements)
    )
}

private fun part19a(target: String, replacements: Map<String, List<String>>): Int {
    val result = mutableSetOf<String>()
    for ((index, c) in target.withIndex()) {
        val replacement = replacements[c.toString()]
        replacement?.forEach{result.add(target.substring(0, index) + it + target.substring(index + 1, target.length))}

        if (target.length > index + 1) {
            val r2 = replacements["$c${target[index + 1]}"]
            r2?.forEach { result.add(target.substring(0, index) + it + target.substring(index + 2, target.length)) }
        }
    }
    return result.size
}

private fun part19b(target: String, replacements: Map<String, List<String>>): Int {
    val invert = replacements.entries.flatMap { r -> r.value.map { Pair(it, r.key) } }.toMap().toSortedMap(compareByDescending(String::length).then(compareBy{it}))
    return loop(target, 0, invert, mutableSetOf())
}

private fun loop(target: String, count: Int, replacements: Map<String, String>, handled: MutableSet<String>): Int {
    if (target == "e") {
        return count
    }

    for (replacement in replacements) {
        if (target.contains(replacement.key)) {
            val molecule = replace(target, replacement.key, replacement.value, target.indexOf(replacement.key))
            if (handled.contains(molecule)) {
                continue
            }
            handled.add(molecule)
            val result = loop(molecule, count + 1, replacements, handled)
            if (result > 0) {
                return result
            }
        }
    }
    return -1;
}

private fun replace(s: String, input: String, out: String, position: Int): String {
    return s.substring(0, position) + out + s.substring(position + input.length)
}

private fun getReplacements(input: List<String>): Map<String, List<String>> {
    return input.map { it.split(" => ") }.groupBy({ it[0] }, { it[1] })
}
