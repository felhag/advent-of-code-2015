import java.lang.Integer.parseInt
import java.lang.RuntimeException

fun main() {
    val input = getInputs()
    log(
        run(input, 0),
        run(input, 1)
    )
}

private fun run(input: List<String>, start: Int): Int {
    var a = start
    var b = 0
    var idx = 0
    while (idx < input.size) {
        val line = input[idx]
        val split = line.split(", ")
        val command = split[0].substring(0, 3)
        val letter = split[0].substring(4)
        when (command) {
            "hlf" -> {
                if (letter == "a") {
                    a /= 2
                } else {
                    b /= 2
                }
                idx++
            }
            "tpl" -> {
                if (letter == "a") {
                    a *= 3
                } else {
                    b *= 3
                }
                idx++
            }
            "inc" -> {
                if (letter == "a") {
                    a++
                } else {
                    b++
                }
                idx++
            }
            "jmp" -> {
                idx += parseInt(letter)
            }
            "jie" -> {
                if (letter == "a" && a % 2 == 0) {
                    idx += parseInt(split[1])
                } else if(
                    letter == "b" && b % 2 == 1) {
                    idx += parseInt(split[1])
                } else {
                    idx++
                }
            }
            "jio" -> {
                if (letter == "a" && a == 1) {
                    idx += parseInt(split[1])
                } else if(letter == "b" && b == 0) {
                    idx += parseInt(split[1])
                } else {
                    idx++
                }
            }
            else -> {
                throw RuntimeException("Unknown command: $command")
            }
        }
    }
    return b
}
