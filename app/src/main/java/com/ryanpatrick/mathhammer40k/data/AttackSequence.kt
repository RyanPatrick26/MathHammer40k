package com.ryanpatrick.mathhammer40k.data

data class AttackSequence(val weaponName: MutableList<String>, val numAttacks: MutableList<Int>,
                          val numHits: MutableList<Int>, val numWounds: MutableList<Int>,
                          val numFailedSaves: MutableList<Int>, val damageDealt: MutableList<Int>)
