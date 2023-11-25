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

private fun part15b(ings: List<Ingredient>): Int {
    var max = 0
    for (i in 0..100) {
        for (j in 0..100-i) {
            for (k in 0..100-i-j) {
                val h = 100 - i - j - k
                val calories = ings[0].calories*i + ings[1].calories*j + ings[2].calories*k + ings[3].calories*h
                if (calories != 500) {
                    continue
                }

                val capacity = ings[0].capacity*i + ings[1].capacity*j + ings[2].capacity*k + ings[3].capacity*h
                val durability = ings[0].durability*i + ings[1].durability*j + ings[2].durability*k + ings[3].durability*h
                val flavor = ings[0].flavor*i + ings[1].flavor*j + ings[2].flavor*k + ings[3].flavor*h
                val texture = ings[0].texture*i + ings[1].texture*j + ings[2].texture*k + ings[3].texture*h
                if (listOf(capacity, durability, flavor, texture).any { it <= 0 }) {
                    continue
                }
                val score = capacity * durability * flavor * texture
                if (score > max) {
                    max = score
                }
            }
        }
    }
    return max
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
