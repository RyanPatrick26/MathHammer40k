package com.ryanpatrick.mathhammer40k.data

sealed class Roles(val name: String) {
    object meleeAttacker: Roles("Attacker: Melee")
    object rangedAttacker: Roles("Attacker: Ranged")
    object defender: Roles("Defender")
}