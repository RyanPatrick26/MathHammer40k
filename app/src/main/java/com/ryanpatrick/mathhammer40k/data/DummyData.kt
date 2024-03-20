package com.ryanpatrick.mathhammer40k.data

val intercessorWeapons: MutableList<Weapon> = mutableListOf(
    Weapon(name = "Bolt Rifle", type = "Ranged", numAttacks = "2",
        attack = Attack(toHit = 3, strength = 4, ap = 1, damage = "1"),
        numPerUnit = 5, abilities = listOf(presetAbilities[5])),
    Weapon(name = "Astartes Grenade Launcher - Frag", type = "Ranged", numAttacks = "d3",
        attack = Attack(toHit = 3, strength = 4, ap = 0, damage = "1"),
        numPerUnit = 1, abilities = listOf(presetAbilities[10])),
    Weapon(name = "Astartes Grenade Launcher - Krak", type = "Ranged", numAttacks = "1",
        attack = Attack(toHit = 3, strength = 9, ap = 2, damage = "d3"),
        numPerUnit = 1, abilities = listOf())
)

val spaceMarineEquivalent: Profile = Profile(
    profileName = "Space Marine Equivalent",
    modelCount = 5,
    toughness = 5,
    wounds = 2,
    save = 3,
    keywords = mutableListOf(Keywords.INFANTRY),
    roles = mutableListOf(Roles.MELEE, Roles.RANGED, Roles.DEFENDER),
    weapons = intercessorWeapons
)

