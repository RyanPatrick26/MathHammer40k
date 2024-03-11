package com.ryanpatrick.mathhammer40k.data

sealed  class Keywords(val name: String, val title: String){
    object Infantry: Keywords("infantry", "INFANTRY")
    object Vehicle: Keywords("vehicle", "VEHICLE")
    object Character: Keywords("character", "CHARACTER")
    object Walker: Keywords("walker", "WALKER")
    object Psyker: Keywords("psyker", "PSYKER")
    object Daemon: Keywords("daemon", "DAEMON")
    object Fly: Keywords("fly", "FLY")
    object Monster: Keywords("monster", "MONSTER")
}