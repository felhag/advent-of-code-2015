fun main() {
    val input = getInput()
    val password = findPassword(input)
    val next = findPassword(increment(password))
    log(password, next)
}

fun findPassword(input: String): String {
    var password = setup(input)
    while (!isValid(password)) {
        password = increment(password)
    }
    return password
}

var forbidden = listOf('i', 'o', 'l')
private fun setup(password: String): String {
    var result = StringBuilder(password)
    for (f in forbidden) {
        while(result.contains(f)) {
            result = increment(result, result.indexOf(f))
        }
    }
    return result.toString()
}

private fun increment(password: String): String {
    return increment(StringBuilder(password), 7).toString()
}

private fun increment(password: StringBuilder, idx: Int): StringBuilder {
    if (password[idx] == 'z') {
        password.setCharAt(idx, 'a')
        increment(password, idx - 1)
    } else {
        var inc = password[idx].inc()
        if (forbidden.contains(inc)) {
            inc = inc.inc()
        }
        password.setCharAt(idx, inc)
    }
    return password
}

private fun isValid(password: String): Boolean {
    return forbidden.none(password::contains) &&
            password.filterIndexed { idx, c -> idx > 0 && c == password[idx - 1] && (idx == 1 || c != password[idx - 2]) }.count() >= 2 &&
            password.filterIndexed { idx, c -> idx > 1 && password[idx - 1] == c.dec() && password[idx - 2] == c.dec().dec() }.isNotEmpty()
}
