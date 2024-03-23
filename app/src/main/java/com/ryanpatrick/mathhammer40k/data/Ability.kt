package com.ryanpatrick.mathhammer40k.data

enum class Ability{
    SUSTAINED_1, SUSTAINED_2, SUSTAINED_D3, LETHAL, DEVASTATING, HEAVY, IGN_COVER, TORRENT, TWIN_LINKED,
    LANCE, BLAST, RAPID_1, RAPID_2, RAPID_3, RAPID_4, RAPID_D63, ANTI_IN_4, ANTI_IN_3, ANTI_IN_2,
    ANTI_CHAR_4, ANTI_CHAR_3, ANTI_CHAR_2, ANTI_VEH_4, ANTI_VEH_3, ANTI_VEH_2, ANTI_FLY_4, ANTI_FLY_3, ANTI_FLY_2,
    ANTI_WALK_4, ANTI_WALK_3, ANTI_WALK_2, ANTI_MON_4, ANTI_MON_3, ANTI_MON_2, ANTI_DAE_4, ANTI_DAE_3, ANTI_DAE_2,
    ANTI_PSY_4, ANTI_PSY_3, ANTI_PSY_2, MELTA_2, MELTA_4, MELTA_6, MELTA_D3
}

val Ability.title: String get(){
    return when(this){
        Ability.SUSTAINED_1 ->  "Sustained Hits 1"
        Ability.SUSTAINED_2 -> "Sustained Hits 2"
        Ability.SUSTAINED_D3 -> "Sustained Hits d3"
        Ability.LETHAL -> "Lethal Hits"
        Ability.DEVASTATING -> "Devastating Wounds"
        Ability.HEAVY -> "Heavy"
        Ability.IGN_COVER -> "Ignores Cover"
        Ability.TORRENT -> "Torrent"
        Ability.TWIN_LINKED -> "Twin-Linked"
        Ability.LANCE -> "Lance"
        Ability.BLAST -> "Blast"
        Ability.RAPID_1 -> "Rapid Fire 1"
        Ability.RAPID_2 -> "Rapid Fire 2"
        Ability.RAPID_3 -> "Rapid Fire 3"
        Ability.RAPID_4 -> "Rapid Fire 4"
        Ability.RAPID_D63 -> "Rapid Fire d6+3"
        Ability.ANTI_IN_4 -> "Anti-Infantry 4+"
        Ability.ANTI_IN_3 -> "Anti-Infantry 3+"
        Ability.ANTI_IN_2 -> "Anti-Infantry 2+"
        Ability.ANTI_CHAR_4 -> "Anti-Character 4+"
        Ability.ANTI_CHAR_3 -> "Anti-Character 3+"
        Ability.ANTI_CHAR_2 -> "Anti-Character 2+"
        Ability.ANTI_VEH_4 -> "Anti-Vehicle 4+"
        Ability.ANTI_VEH_3 -> "Anti-Vehicle 3+"
        Ability.ANTI_VEH_2 -> "Anti-Vehicle 2+"
        Ability.ANTI_FLY_4 -> "Anti-Fly 4+"
        Ability.ANTI_FLY_3 -> "Anti-Fly 3+"
        Ability.ANTI_FLY_2 -> "Anti-Fly 2+"
        Ability.ANTI_WALK_4 -> "Anti-Walker 4+"
        Ability.ANTI_WALK_3 -> "Anti-Walker 3+"
        Ability.ANTI_WALK_2 -> "Anti-Walker 2+"
        Ability.ANTI_MON_4 -> "Anti-Monster 4+"
        Ability.ANTI_MON_3 -> "Anti-Monster 3+"
        Ability.ANTI_MON_2 -> "Anti-Monster 2+"
        Ability.ANTI_DAE_4 -> "Anti-Daemon 4+"
        Ability.ANTI_DAE_3 -> "Anti-Daemon 3+"
        Ability.ANTI_DAE_2 -> "Anti-Daemon 2+"
        Ability.ANTI_PSY_4 -> "Anti-Psyker 4+"
        Ability.ANTI_PSY_3 -> "Anti-Psyker 3+"
        Ability.ANTI_PSY_2 -> "Anti-Psyker 2+"
        Ability.MELTA_2 -> "Melta 2"
        Ability.MELTA_4 -> "Melta 4"
        Ability.MELTA_6 -> "Melta 6"
        Ability.MELTA_D3 -> "Melta d3"
    }
}
val Ability.effect: String get(){
    return when(this){
        Ability.SUSTAINED_1 ->  "Critical Hit: 1 additional hit"
        Ability.SUSTAINED_2 -> "Critical Hit: 2 additional hit"
        Ability.SUSTAINED_D3 -> "Critical Hit: d3 additional hit"
        Ability.LETHAL -> "Critical Hit: automatically wound"
        Ability.DEVASTATING -> "Critical Wound: ignore saves"
        Ability.HEAVY -> "If stationary, +1 to hit roll"
        Ability.IGN_COVER -> "Ignores Cover"
        Ability.TORRENT -> "Automatically Hit"
        Ability.TWIN_LINKED -> "Re-roll wound roll"
        Ability.LANCE -> "If unit charged, +1 to wound"
        Ability.BLAST -> "+1 attack for every 5 models in target unit"
        Ability.RAPID_1 -> "1 additional attack when at half range"
        Ability.RAPID_2 -> "2 additional attack when at half range"
        Ability.RAPID_3 -> "3 additional attack when at half range"
        Ability.RAPID_4 -> "4 additional attack when at half range"
        Ability.RAPID_D63 -> "d6+3 additional attack when at half range"
        Ability.ANTI_IN_4 -> "Unmodified wound roll of 4+ vs Infantry: critical wound"
        Ability.ANTI_IN_3 -> "Unmodified wound roll of 3+ vs Infantry: critical wound"
        Ability.ANTI_IN_2 -> "Unmodified wound roll of 2+ vs Infantry: critical wound"
        Ability.ANTI_CHAR_4 -> "Unmodified wound roll of 4+ vs Character: critical wound"
        Ability.ANTI_CHAR_3 -> "Unmodified wound roll of 3+ vs Character: critical wound"
        Ability.ANTI_CHAR_2 -> "Unmodified wound roll of 2+ vs Character: critical wound"
        Ability.ANTI_VEH_4 -> "Unmodified wound roll of 4+ vs Vehicle: critical wound"
        Ability.ANTI_VEH_3 -> "Unmodified wound roll of 3+ vs Vehicle: critical wound"
        Ability.ANTI_VEH_2 -> "Unmodified wound roll of 2+ vs Vehicle: critical wound"
        Ability.ANTI_FLY_4 -> "Unmodified wound roll of 4+ vs Fly: critical wound"
        Ability.ANTI_FLY_3 -> "Unmodified wound roll of 3+ vs Fly: critical wound"
        Ability.ANTI_FLY_2 -> "Unmodified wound roll of 2+ vs Fly: critical wound"
        Ability.ANTI_WALK_4 -> "Unmodified wound roll of 4+ vs Walker: critical wound"
        Ability.ANTI_WALK_3 -> "Unmodified wound roll of 3+ vs Walker: critical wound"
        Ability.ANTI_WALK_2 -> "Unmodified wound roll of 2+ vs Walker: critical wound"
        Ability.ANTI_MON_4 -> "Unmodified wound roll of 4+ vs Monster: critical wound"
        Ability.ANTI_MON_3 -> "Unmodified wound roll of 3+ vs Monster: critical wound"
        Ability.ANTI_MON_2 -> "Unmodified wound roll of 2+ vs Monster: critical wound"
        Ability.ANTI_DAE_4 -> "Unmodified wound roll of 4+ vs Daemon: critical wound"
        Ability.ANTI_DAE_3 -> "Unmodified wound roll of 3+ vs Daemon: critical wound"
        Ability.ANTI_DAE_2 -> "Unmodified wound roll of 2+ vs Daemon: critical wound"
        Ability.ANTI_PSY_4 -> "Unmodified wound roll of 4+ vs Psyker: critical wound"
        Ability.ANTI_PSY_3 -> "Unmodified wound roll of 4+ vs Psyker: critical wound"
        Ability.ANTI_PSY_2 -> "Unmodified wound roll of 4+ vs Psyker: critical wound"
        Ability.MELTA_2 -> "+2 damage at half range"
        Ability.MELTA_4 -> "+4 damage at half range"
        Ability.MELTA_6 -> "+6 damage at half range"
        Ability.MELTA_D3 -> "+d3 damage at half range"
    }
}
