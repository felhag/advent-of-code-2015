import java.lang.RuntimeException

fun main() {
    val input = getInput()
    log(
        part12a(input),
        part12b(input)
    )
}

fun part12a(input: String): Int {
    val root: JSONNode = if (input[0] == '[') JSONArray() else JSONObject()
    parse(input, 1, root)
    return root.sum(null)
}

fun part12b(input: String): Int {
    val root: JSONNode = if (input[0] == '[') JSONArray() else JSONObject()
    parse(input, 1, root)
    return root.sum("\"red\"")
}

private fun parse(input: String, idx: Int, obj: JSONNode): Int {
    var i = idx
    var start = i
    while (i < input.length) {
        when (input[i]) {
            '[', '{' -> {
                val v = if (input[i] == '[') JSONArray() else JSONObject()
                obj.add(v)
                i = parse(input, i + 1, v)
                start = i
            }
            ']', '}' -> {
                val add = input.substring(start, i)
                if (add != "}" && add != "]") {
                    obj.add(add)
                }
                return i
            }
            ',', ':' -> {
                val add = input.substring(start, i)
                if (add != "}" && add != "]") {
                    obj.add(add)
                }
                start = i + 1
            }
            else -> {}
        }
        i++
    }
    return idx
}

private sealed class JSONNode {
    fun add(v: String) {
        add(JSONValue(v))
    }

    abstract fun add(v: JSONNode)
    abstract fun sum(exclude: String?): Int
}

private class JSONValue(val v: String) : JSONNode() {
    override fun add(v: JSONNode) {
        throw RuntimeException("Cant add value")
    }

    override fun sum(exclude: String?): Int {
        return if (v.startsWith("\"")) 0 else Integer.parseInt(v)
    }
}

private class JSONObject : JSONNode() {
    private val nodes = mutableMapOf<String, JSONNode>();
    private var key: JSONValue? = null

    override fun add(v: JSONNode) {
        if (key != null) {
            nodes[key!!.v] = v
            key = null
        } else if (v is JSONValue) {
            key = v
        }
    }

    override fun sum(exclude: String?): Int {
        return if (nodes.values.any { v -> v is JSONValue && v.v == exclude }) {
            0
        } else {
            nodes.values.sumOf { it.sum(exclude) }
        }
    }
}

private class JSONArray : JSONNode() {
    private val nodes = mutableListOf<JSONNode>();

    override fun add(v: JSONNode) {
        nodes.add(v)
    }

    override fun sum(exclude: String?): Int {
        return nodes.sumOf { it.sum(exclude) }
    }
}
