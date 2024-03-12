package com.ryanpatrick.mathhammer40k.data

data class Weapon(val name: String, val type: String, val attacks: String, val toHit: Int,
                  val strength: Int, val ap: Int, val damage: String, val numPerUnit: Int,
                  val abilities: List<Ability>)
