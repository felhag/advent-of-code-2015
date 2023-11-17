import java.lang.Integer.parseInt
import kotlin.math.floor

fun main() {
    val target = parseInt(getInput())
    val part1 = part20a(target)
    val part2 = part20b(target, part1)
    log(part1, part2)
}

private fun part20a(target: Int): Int {
    var house = 1
    while (true) {
        var presents = 0
        for (elf in 1..house) {
            if (house % elf == 0) {
                presents += elf * 10
            }
        }
//        if (house % 50000 == 0) {
//            println("House $house gets $presents presents")
//        }
        if (presents >= target) {
            return house
        }

        house++
    }
}

private fun part20b(target: Int, start: Int): Int {
    var house = start
    while (true) {
        var presents = 0
        val floor = floor((house.toDouble() - 1) / 50).toInt() + 1
        for (elf in floor..house) {
            if (house % elf == 0) {
                presents += elf * 11
            }
        }
//        if (house % 50000 == 0) {
//            println("House $house gets $presents presents")
//        }
        if (presents >= target) {
            return house
        }

        house++
    }
}
