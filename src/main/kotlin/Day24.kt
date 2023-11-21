import java.lang.Long.MAX_VALUE

fun main() {
    val input = getInputs().map(Integer::parseInt).sortedDescending()
    log(
        run(input, 3),
        run(input, 4)
    )
}

private fun run(packages: List<Int>, compartments: Int): Long {
    val target = packages.sum() / compartments
    return stack(target, Pair(MAX_VALUE, Int.MAX_VALUE), listOf(), packages)!!.first
}

private fun stack(target: Int, minimal: Pair<Long, Int>, current: List<Int>, packages: List<Int>): Pair<Long, Int>? {
    var min = minimal
    val sum = current.sum()
    if (sum > target) {
        return null
    } else if (sum == target) {
        val qe = current.map(Int::toLong).reduce { acc, i -> acc * i }
        if ((min.second == current.size && qe < min.first) || min.second > current.size) {
            return Pair(qe, current.size)
        }
        return null
    } else if (min.second <= current.size) {
        return null
    }

    for (p in packages) {
        val stack = stack(target, min, current + p, packages.subList(packages.indexOf(p) + 1, packages.size))
        if (stack != null) {
            min = stack
        }
    }
    return min
}
