import java.lang.Integer.parseInt
import kotlin.math.floor

fun main() {
    val input = getInputs()
    val reindeers = getReindeers(input)
    log(
        part14a(reindeers, 2503),
        part14b(reindeers, 2503)
    )
}

private fun part14a(reindeers: List<Reindeer>, seconds: Int): Int {
    return reindeers.map { calcDistance(it, seconds) }.max()
}

private fun part14b(reindeers: List<Reindeer>, seconds: Int): Int {
    val score = reindeers.associateWith { 0 }.toMutableMap()
    val distance = reindeers.associateWith { 0 }.toMutableMap()
    for (second in 0 until seconds) {
        reindeers.filter { second % (it.length + it.rest) < it.length }.forEach{distance[it] = distance[it]!!.plus(it.speed)}
        val max = distance.values.max()
        distance.filterValues { it == max }.keys.forEach { score[it] = score[it]!!.plus(1) }
    }
    return score.values.max()
}

private fun calcDistance(reindeer: Reindeer, seconds: Int): Int {
    val chunk = reindeer.length + reindeer.rest
    val flight = reindeer.length * reindeer.speed
    val distance = floor((seconds.toDouble() / chunk)).toInt() * flight
    val remaining = seconds % chunk
    val x = if (remaining > reindeer.length) flight else remaining * reindeer.speed
    return x + distance
}

private fun getReindeers(lines: List<String>): List<Reindeer> {
    val regex =  Regex("(\\w*) can fly (\\d*) km/s for (\\d*) seconds, but then must rest for (\\d*) seconds.")
    return lines.map {regex.find(it)!!.groupValues }.map { Reindeer(parseInt(it[2]), parseInt(it[3]), parseInt(it[4])) }
}

private data class Reindeer(
    val speed: Int,
    val length: Int,
    val rest: Int
)
