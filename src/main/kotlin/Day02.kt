import java.lang.Integer.parseInt
import java.util.stream.IntStream

fun main() {
    val input = getInputs()
    log(
        part02a(input),
        part02b(input)
    )
}

fun part02a(presents: List<String>): Int {
    return presents.sumOf { calcPresentSize(it) }
}

fun part02b(presents: List<String>): Int {
    return presents
        .map { calcDimensions(it) }
        .sumOf { calcRibbonPresent(it) + calcRibbonBow(it) }
}

private fun calcPresentSize(present: String): Int {
    val split = calcDimensions(present)
    val s1 = split[0] * split[1]
    val s2 = split[1] * split[2]
    val s3 = split[2] * split[0]
    return (2 * s1) + (2 * s2) + (2 * s3) + IntStream.of(s1, s2, s3).min().orElse(0)
}

private fun calcDimensions(present: String): List<Int> {
    return present.split('x').map { parseInt(it) }
}

private fun calcRibbonPresent(present: List<Int>): Int {
    return present.sorted().subList(0, 2).sum() * 2
}

private fun calcRibbonBow(present: List<Int>): Int {
    return present.reduce { acc, i -> acc * i }
}
