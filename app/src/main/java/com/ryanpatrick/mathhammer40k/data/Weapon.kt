package com.ryanpatrick.mathhammer40k.data

data class Weapon(val name: String, val type: String, val numAttacks: String, val attack: Attack,
                  val numPerUnit: Int, val abilities: List<Ability>)

data class Attack(val toHit: Int, val strength: Int, val ap: Int, val damage: String)
