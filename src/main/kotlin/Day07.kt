import java.lang.RuntimeException

fun main() {
    val input = getInputs()
    val targets = input.map { parseLine(it) }.associateBy({ it.target }, { it })
    val partA = calc("a", targets, mutableMapOf())
    val partB = calc("a", targets, mutableMapOf(Pair("b", partA)))
    log(partA, partB)
}

private fun calc(target: String, targets: Map<String, Action>, resolved: MutableMap<String, Int>): Int {
    if (target.toIntOrNull() != null) {
        return target.toInt()
    }
    if (resolved[target] != null) {
        return resolved[target]!!
    }

    val action = targets[target] ?: throw RuntimeException("Cannot find $target")
    val result = action.command.exec(action.values.map { calc(it, targets, resolved) })
    resolved[target] = result
    return result
}

private fun parseLine(line: String): Action {
    val target = line.substringAfterLast(" -> ", line)
    val instruction = line.substringBefore(" -> ", line)
    val command = getCommand(instruction)
    val values = instruction.split(command.name).map(String::trim).filter(String::isNotBlank)
    return Action(command, target, values)
}

private fun getCommand(instruction: String): Command {
    return Command.values().firstOrNull { instruction.contains(it.name) }?: Command.STATIC
}

enum class Command {
    STATIC { override fun exec(v: List<Int>): Int = v[0]},
    AND { override fun exec(v: List<Int>): Int = v[0] and v[1] },
    OR { override fun exec(v: List<Int>): Int = v[0] or v[1]},
    LSHIFT { override fun exec(v: List<Int>): Int = v[0] shl v[1]},
    RSHIFT { override fun exec(v: List<Int>): Int = v[0] shr v[1]},
    NOT { override fun exec(v: List<Int>): Int = 65535 - v[0]};

    abstract fun exec(v: List<Int>): Int
}

data class Action(
    val command: Command,
    val target: String,
    val values: List<String>
)
