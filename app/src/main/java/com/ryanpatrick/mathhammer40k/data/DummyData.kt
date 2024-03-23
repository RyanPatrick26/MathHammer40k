package com.ryanpatrick.mathhammer40k.data

val intercessorWeapons: MutableList<Weapon> = mutableListOf(
    Weapon(name = "Bolt Rifle", type = "Ranged", numAttacks = "2",
        attack = Attack(toHit = 3, strength = 4, ap = 1, damage = "1"),
        numPerUnit = 5, abilities = listOf(Ability.HEAVY)),
    Weapon(name = "Astartes Grenade Launcher - Frag", type = "Ranged", numAttacks = "d3",
        attack = Attack(toHit = 3, strength = 4, ap = 0, damage = "1"),
        numPerUnit = 1, abilities = listOf(Ability.BLAST)),
    Weapon(name = "Astartes Grenade Launcher - Krak", type = "Ranged", numAttacks = "1",
        attack = Attack(toHit = 3, strength = 9, ap = 2, damage = "d3"),
        numPerUnit = 1, abilities = listOf())
)
val guardsmanWeapons: MutableList<Weapon> = mutableListOf(
    Weapon(name = "Lasgun", type = "Ranged", numAttacks = "1",
        attack = Attack(toHit = 4, strength = 3, ap = 0, damage = "1"),
        numPerUnit = 9, abilities = listOf()),
    Weapon(name = "Laspistol", type = "Ranged", numAttacks = "1",
        attack = Attack(toHit = 4, strength = 3, ap = 0, damage = "1"),
        numPerUnit = 1, abilities = listOf()),
)
val terminatorWeapons: MutableList<Weapon> = mutableListOf(
    Weapon(name = "Storm Bolter", type = "Ranged", numAttacks = "2",
        attack = Attack(toHit = 3, strength = 4, ap = 0, damage = "1"),
        numPerUnit = 4, abilities = listOf(Ability.RAPID_2)),
    Weapon(name = "Assault Cannon", type = "Ranged", numAttacks = "6",
        attack = Attack(toHit = 3, strength = 6, ap = 0, damage = "1"),
        numPerUnit = 1, abilities = listOf(Ability.DEVASTATING)),
    Weapon(name = "Cyclone Missile Launcher - Frag", type = "Ranged", numAttacks = "2d6",
        attack = Attack(toHit = 3, strength = 4, ap = 0, damage = "1"),
        numPerUnit = 1, abilities = listOf(Ability.BLAST)),
    Weapon(name = "Cyclone Missile Launcher - Krak", type = "Ranged", numAttacks = "2",
        attack = Attack(toHit = 3, strength = 9, ap = 2, damage = "d6"),
        numPerUnit = 1, abilities = listOf()),
    Weapon(name = "Heavy Flamer", type = "Ranged", numAttacks = "d6",
        attack = Attack(strength = 5, ap = 1, damage = "1"),
        numPerUnit = 1, abilities = listOf(Ability.TORRENT, Ability.IGN_COVER))
)

val spaceMarineEquivalent: Profile = Profile(
    profileName = "Space Marine Equivalent",
    modelCount = 5,
    toughness = 4,
    wounds = 2,
    save = 3,
    keywords = mutableListOf(Keywords.INFANTRY),
    roles = mutableListOf(Roles.MELEE, Roles.RANGED, Roles.DEFENDER),
    weapons = intercessorWeapons
)

val guardsmanEquivalent: Profile = Profile(
    profileName = "Guardsman Equivalent",
    modelCount = 10,
    toughness = 3,
    wounds = 1,
    save = 5,
    keywords = mutableListOf(Keywords.INFANTRY),
    roles = mutableListOf(Roles.MELEE, Roles.RANGED, Roles.DEFENDER),
    weapons = guardsmanWeapons
)

val terminatorEquivalent: Profile = Profile(
    profileName = "Terminator Equivalent",
    modelCount = 5,
    toughness = 5,
    wounds = 3,
    save = 2,
    invulnerable = 4,
    keywords = mutableListOf(Keywords.INFANTRY),
    roles = mutableListOf(Roles.MELEE, Roles.RANGED, Roles.DEFENDER),
    weapons = terminatorWeapons
)

