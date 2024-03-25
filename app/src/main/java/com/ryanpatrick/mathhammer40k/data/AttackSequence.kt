package com.ryanpatrick.mathhammer40k.data

data class AttackSequence(val weaponName: MutableList<String> = mutableListOf(),
                          val numAttacks: MutableList<Int> = mutableListOf(),
                          val numHits: MutableList<Int> = mutableListOf(),
                          val numWounds: MutableList<Int> = mutableListOf(),
                          val numFailedSaves: MutableList<Int> = mutableListOf(),
                          val damageDealt: MutableList<Int> = mutableListOf())
