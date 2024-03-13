package com.ryanpatrick.mathhammer40k.data

val intercessorWeapons: List<Weapon> = listOf(
    Weapon(name = "Bolt Rifle", type = "Ranged", attacks = "2", toHit = 3,
        strength = 4, ap = 1, damage = "1", numPerUnit = 5,
        abilities = listOf(presetAbilities[5])),
    Weapon(name = "Astartes Grenade Launcher - Frag", type = "Ranged", attacks = "d3", toHit = 3,
        strength = 4, ap = 0, damage = "1", numPerUnit = 1,
        abilities = listOf(presetAbilities[10])),
    Weapon(name = "Astartes Grenade Launcher - Krak", type = "Ranged", attacks = "1", toHit = 3,
        strength = 9, ap = 2, damage = "d3", numPerUnit = 1,
        abilities = listOf())
)

val spaceMarineEquivalent: Profile = Profile(
    profileName = "Space Marine Equivalent",
    modelCount = 5,
    toughness = 5,
    wounds = 2,
    save = 3,
    keywords = listOf(Keywords.Infantry),
    roles = listOf(Roles.meleeAttacker, Roles.rangedAttacker, Roles.defender),
    weapons = intercessorWeapons
)
