package com.ryanpatrick.mathhammer40k.data

enum class Keywords{
    INFANTRY, VEHICLE, CHARACTER, WALKER, PSYKER, DAEMON, FLY, MONSTER
}
val Keywords.title: String get(){
    return when(this){
        Keywords.INFANTRY -> "INFANTRY"
        Keywords.VEHICLE -> "VEHICLE"
        Keywords.CHARACTER -> "CHARACTER"
        Keywords.WALKER -> "WALKER"
        Keywords.PSYKER -> "PSYKER"
        Keywords.DAEMON -> "DAEMON"
        Keywords.FLY -> "FLY"
        Keywords.MONSTER -> "MONSTER"
    }
}