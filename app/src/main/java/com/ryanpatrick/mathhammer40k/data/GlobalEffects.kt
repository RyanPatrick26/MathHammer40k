package com.ryanpatrick.mathhammer40k.data

enum class GlobalEffects {
    STATIONARY, COVER, HALF_RANGE, CHARGED
}

val GlobalEffects.title: String get(){
    return when (this){
        GlobalEffects.STATIONARY -> "Attacker Stationary"
        GlobalEffects.COVER -> "Defender in Cover"
        GlobalEffects.HALF_RANGE -> "Attacker in Half Range"
        GlobalEffects.CHARGED -> "Attacker Charged"
    }
}