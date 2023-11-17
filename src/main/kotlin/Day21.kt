import java.lang.Integer.MAX_VALUE
import java.lang.Integer.parseInt

fun main() {
    val input = getInputs().map { parseInt(it.split(": ")[1]) }
    val boss = Character(input[0], input[1], input[2])
    log(
        part21a(boss),
        part21b(boss)
    )
}

private fun part21a(boss: Character): Int {
    val weapons = weapons()
    val armors = armors()
    val rings = rings()
    var min = MAX_VALUE
    for (weapon in weapons) {
        for (armor in armors) {
            min = withRings(min, boss, rings, weapon, armor)
        }
        min = withRings(min, boss, rings, weapon)
    }
    return min
}

private fun part21b(boss: Character): Int {
    val weapons = weapons()
    val armors = armors()
    val rings = rings()
    var max = 0
    for (weapon in weapons) {
        for (armor in armors) {
            max = withRings2(max, boss, rings, weapon, armor)
        }
    }
    return max
}

private fun withRings(m: Int, boss: Character, rings: List<Item>, vararg items: Item): Int {
    var min = m
    val without = getPrice(min, boss, *items)
    if (min > without) {
        return min
    }

    for ((index, ring) in rings.withIndex()) {
        min = getPrice(min, boss, *items, ring)
        for (inner in index until rings.size) {
            min = getPrice(min, boss, *items, ring, rings[inner])
        }
    }
    return min
}

private fun getPrice(min: Int, boss: Character, vararg items: Item): Int {
    val price = items.sumOf { it.gold }
    if (price < min) {
        val player = build(*items)
        if (fight(player, boss.copy())) {
            return price
        }
    }
    return min
}

private fun withRings2(m: Int, boss: Character, rings: List<Item>, vararg items: Item): Int {
    var max = m
    for ((index, ring) in rings.withIndex()) {
        for (inner in index until rings.size) {
            max = getPrice2(max, boss, *items, ring, rings[inner])
        }
    }
    return max
}

private fun getPrice2(max: Int, boss: Character, vararg items: Item): Int {
    val price = items.sumOf { it.gold }
    if (price > max) {
        val player = build(*items)
        if (!fight(player, boss.copy())) {
            return price
        }
    }
    return max
}

private fun build(vararg items: Item): Character {
    return Character(100, items.sumOf { it.damage }, items.sumOf { it.armor })
}

private fun fight(player: Character, boss: Character): Boolean {
    var attacker = player
    var defender = boss
    while(true) {
        defender.attack(attacker.damage - defender.armor)

        if (defender.hp <= 0) {
            return attacker == player
        }

        defender = attacker.also { attacker = defender }
    }
}

private fun weapons(): List<Item> {
    return listOf(
        Item("Dagger", 8, 4, 0),
        Item("Shortsword", 10, 5, 0),
        Item("Warhammer", 25, 6, 0),
        Item("Longsword", 40, 7, 0),
        Item("Greataxe", 74, 8, 0),
    )
}

private fun armors(): List<Item> {
    return listOf(
        Item("Leather", 13, 0, 1),
        Item("Chainmail", 31, 0, 2),
        Item("Splintmail", 53, 0, 3),
        Item("Bandedmail", 75, 0, 4),
        Item("Platemail", 102, 0, 5),
    )
}

private fun rings(): List<Item> {
    return listOf(
        Item("Damage +1", 25, 1, 0),
        Item("Damage +2", 50, 2, 0),
        Item("Damage +3", 100, 3, 0),
        Item("Defense +1", 20, 0, 1),
        Item("Defense +2", 40, 0, 2),
        Item("Defense +3", 80, 0, 3),
    )
}

private data class Item(val name: String, val gold: Int, val damage: Int, val armor: Int)

private class Character(var hp: Int, val damage: Int, val armor: Int) {
    fun attack(amount: Int) {
        this.hp -= amount.coerceAtLeast(1)
    }

    fun copy(): Character {
        return Character(hp, damage, armor)
    }
}
