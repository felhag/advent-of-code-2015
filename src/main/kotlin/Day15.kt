import java.lang.Integer.parseInt

fun main() {
    val input = getInputs()
    val ingredients = getIngredients(input)
    log(
        part15a(ingredients),
        part15b(ingredients)
    )
}

private fun part15a(ingredients: List<Ingredient>): Int {
    return next(ingredients.associateWith { 100 / ingredients.size }.toMutableMap())
}

private fun part15b(ingredients: List<Ingredient>): Int {
    return next(ingredients.associateWith { 100 / ingredients.size }.toMutableMap())
}

private fun next(current: Map<Ingredient, Int>): Int {
    var max: Map<Ingredient, Int> = current
    var better = false
    for (add in current.keys) {
        for (sub in current.keys) {
            if (sub == add) {
                continue
            }
            val copy = current.toMutableMap()
            copy[add] = copy[add]!! + 1
            copy[sub] = copy[sub]!! - 1

            if (sum(copy) > sum(max)) {
                println("Better: " + copy + ": " + sum(copy) + " > " + sum(max))
                max = copy
                better = true
            }
        }
    }
    return if (better) next(max) else sum(max)
}

private fun sum(current: Map<Ingredient, Int>): Int {
    return current.map { it.key.capacity * it.value }.sum() *
        current.map { it.key.durability * it.value }.sum() *
        current.map { it.key.flavor * it.value }.sum() *
        current.map { it.key.texture * it.value }.sum()
}

private fun getIngredients(lines: List<String>): List<Ingredient> {
    val regex =  Regex("(\\w*): capacity (-?\\d*), durability (-?\\d*), flavor (-?\\d*), texture (-?\\d*), calories (-?\\d*)")
    return lines.map {regex.find(it)!!.groupValues }.map { Ingredient(
        it[1],
        parseInt(it[2]),
        parseInt(it[3]),
        parseInt(it[4]),
        parseInt(it[5]),
        parseInt(it[6])
    ) }
}

private data class Ingredient(
    val name: String,
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int
)
