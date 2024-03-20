package com.ryanpatrick.mathhammer40k.data

enum class Roles{
    MELEE, RANGED, DEFENDER
}

val Roles.title: String get(){
    return when(this){
        Roles.MELEE -> "Attacker: Melee"
        Roles.RANGED -> "Attacker: Ranged"
        Roles.DEFENDER -> "Defender"
    }
}