import java.lang.Integer.MAX_VALUE
import java.lang.Integer.parseInt
import kotlin.math.min

fun main() {
    val input = getInputs()
    log(
        start(input, false),
        start(input, true)
    )
}

private fun start(input: List<String>, hard: Boolean): Int {
    val stats = input.map { parseInt(it.split(": ")[1]) }
    val boss = Boss(stats[0], stats[1])
    val player = Player(50, 500, 0)
    return next(boss, player, 1, hard, MAX_VALUE, mutableListOf())
}

private fun next(boss: Boss, player: Player, turn: Int, hard: Boolean, minMana: Int, active: List<ActiveSpell>): Int {
     if (minMana <= active.sumOf { it.spell.mana }) {
         return minMana
     }
    var min = minMana
    val curActive = active.filter { it.end > turn }.map { it.spell }
    val validSpells = Spell.values().filter { it.mana <= player.mana && !curActive.contains(it)}
    for (spell in validSpells) {
        val nBoss = Boss(boss.hp, boss.damage)
        val nPlayer = Player(player.hp, player.mana, player.armor)

        // player turn
        if (hard) {
            nPlayer.hp--
            if (nPlayer.hp <= 0) {
                continue
            }
        }
        handleSpells(nPlayer, nBoss, turn, active)
        val nextSpells = active + ActiveSpell(spell, turn, turn + spell.turns)
        nPlayer.mana -= spell.mana

        // boss turn
        handleSpells(nPlayer, nBoss, turn + 1, nextSpells)
        if (nBoss.hp <= 0) {
            min = min(nextSpells.sumOf { it.spell.mana }, min)
            continue
        }
        nPlayer.hp -= (nBoss.damage - nPlayer.armor).coerceAtLeast(1)
        if (nPlayer.hp <= 0) {
            continue
        }

        val n = next(nBoss, nPlayer, turn + 2, hard, min, nextSpells)
        min = min(min, n)
    }
    return min
}

private fun handleSpells(player: Player, boss: Boss, turn: Int, active: List<ActiveSpell>) {
    for (spell in active.filter { it.end >= turn }) {
        spell.spell.apply(player, boss, turn - spell.start)
    }
}

private data class ActiveSpell(var spell: Spell, val start: Int, val end: Int)

private enum class Spell(
    val mana: Int,
    val turns: Int,
    val apply: (player: Player, boss: Boss, turn: Int) -> Unit
) {
    MAGIC_MISSILE(53, 1, {_, boss, _ -> boss.hp -= 4}),
    DRAIN(73, 1, { player, boss, _ ->
        boss.hp -= 2
        player.hp += 2
    }),
    SHIELD(113, 6, { player, _, turn -> player.armor = if (turn == 6) 0 else 7 }),
    POISON(173, 6, { _, boss, _ -> boss.hp -= 3}),
    RECHARGE(229, 5, { player, _, _ -> player.mana += 101})
}

private data class Boss(var hp: Int, var damage: Int)
private data class Player(var hp: Int, var mana: Int, var armor: Int)
