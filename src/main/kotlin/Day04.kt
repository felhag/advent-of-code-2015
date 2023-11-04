import java.math.BigInteger
import java.security.MessageDigest

fun main() {
    val input = getInput()
    log(
        part04a(input),
        part04b(input)
    )
}

fun part04a(input: String): Int {
    return untilStartsWith(input, "00000")
}

fun part04b(input: String): Int {
    return untilStartsWith(input, "000000")
}

private fun untilStartsWith(input: String, startsWith: String): Int {
    var i = 0
    var hash = ""
    while (!hash.startsWith(startsWith)) {
        hash = md5(input + i)
        i++
    }

    return i - 1
}

private fun md5(input:String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}
