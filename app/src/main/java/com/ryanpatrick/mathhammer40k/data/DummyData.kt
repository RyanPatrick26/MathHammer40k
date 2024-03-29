package com.ryanpatrick.mathhammer40k.data

val intercessorWeapons: MutableList<Weapon> = mutableListOf(
    Weapon(name = "Bolt Rifle", type = "Ranged", numAttacks = "2",
        attack = Attack(toHit = 3, strength = 4, ap = 1, damage = "1"),
        numPerUnit = 5, abilities = mutableListOf(Ability.HEAVY)),
    Weapon(name = "Astartes Grenade Launcher - Frag", type = "Ranged", numAttacks = "d3",
        attack = Attack(toHit = 3, strength = 4, ap = 0, damage = "1"),
        numPerUnit = 1, abilities = mutableListOf(Ability.BLAST)),
    Weapon(name = "Astartes Grenade Launcher - Krak", type = "Ranged", numAttacks = "1",
        attack = Attack(toHit = 3, strength = 9, ap = 2, damage = "d3"),
        numPerUnit = 1, abilities = mutableListOf()),
    Weapon(name = "Close Combat Weapon", type = "Melee", numAttacks = "3",
        attack = Attack(toHit = 3, strength = 4, ap = 0, damage = "1"),
        numPerUnit = 5, abilities = mutableListOf()),
    Weapon(name = "Power Weapon", type = "Melee", numAttacks = "4",
        attack = Attack(toHit = 3, strength = 5, ap = 2, damage = "1"),
        numPerUnit = 1, abilities = mutableListOf()),
    Weapon(name = "Power Fist", type = "Melee", numAttacks = "3",
        attack = Attack(toHit = 3, strength = 8, ap = 2, damage = "2"),
        numPerUnit = 1, abilities = mutableListOf()),
    Weapon(name = "Thunder Hammer", type = "Melee", numAttacks = "3",
        attack = Attack(toHit = 4, strength = 8, ap = 2, damage = "2"),
        numPerUnit = 1, abilities = mutableListOf(Ability.DEVASTATING)),
    Weapon(name = "Astartes Chainsword", type = "Melee", numAttacks = "5",
        attack = Attack(toHit = 3, strength = 4, ap = 1, damage = "1"),
        numPerUnit = 1, abilities = mutableListOf()),
)
val guardsmanWeapons: MutableList<Weapon> = mutableListOf(
    Weapon(name = "Lasgun", type = "Ranged", numAttacks = "1",
        attack = Attack(toHit = 4, strength = 3, ap = 0, damage = "1"),
        numPerUnit = 9, abilities = mutableListOf()),
    Weapon(name = "Laspistol", type = "Ranged", numAttacks = "1",
        attack = Attack(toHit = 4, strength = 3, ap = 0, damage = "1"),
        numPerUnit = 1, abilities = mutableListOf()),
    Weapon(name = "Close Combat Weapon", type = "Melee", numAttacks = "1",
        attack = Attack(toHit = 4, strength = 3, ap = 0, damage = "1"),
        numPerUnit = 9, abilities = mutableListOf()),
    Weapon(name = "Power Weapon", type = "Melee", numAttacks = "2",
        attack = Attack(toHit = 4, strength = 4, ap = 2, damage = "1"),
        numPerUnit = 1, abilities = mutableListOf()),
    Weapon(name = "Chainsword", type = "Melee", numAttacks = "3",
        attack = Attack(toHit = 4, strength = 3, ap = 0, damage = "1"),
        numPerUnit = 1, abilities = mutableListOf()),
)
val terminatorWeapons: MutableList<Weapon> = mutableListOf(
    Weapon(name = "Storm Bolter", type = "Ranged", numAttacks = "2",
        attack = Attack(toHit = 3, strength = 4, ap = 0, damage = "1"),
        numPerUnit = 4, abilities = mutableListOf(Ability.RAPID_2)),
    Weapon(name = "Assault Cannon", type = "Ranged", numAttacks = "6",
        attack = Attack(toHit = 3, strength = 6, ap = 0, damage = "1"),
        numPerUnit = 1, abilities = mutableListOf(Ability.DEVASTATING)),
    Weapon(name = "Cyclone Missile Launcher - Frag", type = "Ranged", numAttacks = "2d6",
        attack = Attack(toHit = 3, strength = 4, ap = 0, damage = "1"),
        numPerUnit = 1, abilities = mutableListOf(Ability.BLAST)),
    Weapon(name = "Cyclone Missile Launcher - Krak", type = "Ranged", numAttacks = "2",
        attack = Attack(toHit = 3, strength = 9, ap = 2, damage = "d6"),
        numPerUnit = 1, abilities = mutableListOf()),
    Weapon(name = "Heavy Flamer", type = "Ranged", numAttacks = "d6",
        attack = Attack(strength = 5, ap = 1, damage = "1"),
        numPerUnit = 1, abilities = mutableListOf(Ability.TORRENT, Ability.IGN_COVER)),
    Weapon(name = "Chainfist", type = "Melee", numAttacks = "3",
        attack = Attack(toHit = 4, strength = 8, ap = 2, damage = "2"),
        numPerUnit = 1, abilities = mutableListOf(Ability.ANTI_VEH_3)),
    Weapon(name = "Powerfist", type = "Melee", numAttacks = "2",
        attack = Attack(toHit = 3, strength = 8, ap = 2, damage = "2"),
        numPerUnit = 4, abilities = mutableListOf()),
    Weapon(name = "Power Weapon", type = "Melee", numAttacks = "4",
        attack = Attack(toHit = 3, strength = 5, ap = 2, damage = "1"),
        numPerUnit = 1, abilities = mutableListOf()),
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

