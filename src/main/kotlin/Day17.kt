fun main() {
    val containers = getInputs().map(Integer::parseInt).sortedDescending()
    log(
        part17a(containers, 150),
        part17b(containers, 150)
    )
}

fun part17a(containers: List<Int>, target: Int): Int {
    return iterate(0, target, containers)
}

fun part17b(containers: List<Int>, target: Int): Int {
    val solutions = mutableMapOf<Int, Int>()
    iterate2(emptyList(), solutions, target, containers)
    val min = solutions.keys.min()
    return solutions[min]!!
}

private fun iterate(sum: Int, target: Int, containers: List<Int>): Int {
    return if (sum == target) {
        1
    } else if (sum > target) {
        0
    } else {
        containers.mapIndexed { idx, container -> iterate(sum + container, target, containers.from(idx + 1)) }.sum()
    }
}

private fun iterate2(sum: List<Int>, solutions: MutableMap<Int, Int>, target: Int, containers: List<Int>) {
    val calc = sum.sum()
    if (calc == target) {
        solutions[sum.size] = (solutions.getOrDefault(sum.size, 0)) + 1
    } else if (calc < target) {
        containers.forEachIndexed{ idx, container -> iterate2(sum + container, solutions, target, containers.from(idx + 1)) }
    }
}

private fun List<Int>.from(idx: Int): List<Int> = subList(idx, size)
