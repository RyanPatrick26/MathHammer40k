package com.ryanpatrick.mathhammer40k

import android.util.Log
import com.ryanpatrick.mathhammer40k.data.Ability
import com.ryanpatrick.mathhammer40k.data.AttackSequence
import com.ryanpatrick.mathhammer40k.data.Profile
import com.ryanpatrick.mathhammer40k.data.Weapon
import kotlin.random.Random

class Simulator {
    companion object{
        fun runSimulation(attackerWeapons: List<Weapon>, defenderProfile: Profile): SimulationResults{
            val attackSimulation = AttackSequence(mutableListOf(),  mutableListOf(), mutableListOf(),
                mutableListOf(), mutableListOf(), mutableListOf())
            var killsList = mutableListOf<Int>()
            var modelsKilled: Int
            var testString: String
            val startTime = System.currentTimeMillis()

            attackerWeapons.forEach { weapon->
                attackSimulation.weaponName.add(weapon.name)
            }

            for(i in 1..10000) {
                //region determine number of attacks
                attackSimulation.numAttacks.addAll(determineNumAttacks(attackerWeapons))
                testString = "Attacks\n"
                for(j in 0..<attackSimulation.weaponName.count()){
                    testString += "${attackSimulation.weaponName[j]}: ${attackSimulation.numAttacks[j]} attacks\n"
                }
                //endregion

                //region determine number of hits
                attackSimulation.numHits.addAll(rollToHit(attackerWeapons, attackSimulation.numAttacks))
                testString = "Hits\n"
                for(j in 0..<attackSimulation.weaponName.count()){
                    testString += "${attackSimulation.weaponName[j]}: ${attackSimulation.numHits[j]} hits\n"
                }
                //endregion

                //region determine number of wounds
                attackSimulation.numWounds.addAll(rollToWound(attackerWeapons, attackSimulation.numHits, defenderProfile))
                testString = "Wounds\n"
                for(j in 0..<attackSimulation.weaponName.count()){
                    testString += "${attackSimulation.weaponName[j]}: ${attackSimulation.numWounds[j]} wounds\n"
                }
                //endregion

                //region determine number of failed saves
                attackSimulation.numFailedSaves.addAll(rollSave(attackerWeapons, attackSimulation.numWounds, defenderProfile))
                testString = "Failed Saves\n"
                for(j in 0..<attackSimulation.weaponName.count()){
                    testString += "${attackSimulation.weaponName[j]}: ${attackSimulation.numAttacks[j]} failed saves\n"
                }
                //endregion

                //region determine damage dealt
                val pair = determineDamage(attackerWeapons, attackSimulation.numFailedSaves, defenderProfile)
                modelsKilled = pair.first
                attackSimulation.damageDealt.addAll(pair.second)
                testString = "Kills\n"
                for(j in 0..<attackSimulation.weaponName.count()){
                    testString += "${attackSimulation.weaponName[j]}: ${attackSimulation.damageDealt[j]} kills\n"
                }
                //endregion
                killsList.add(modelsKilled)

                attackSimulation.numAttacks.clear()
                attackSimulation.numHits.clear()
                attackSimulation.numWounds.clear()
                attackSimulation.numFailedSaves.clear()
                attackSimulation.damageDealt.clear()
            }
            killsList = killsList.map {
                if(it > defenderProfile.modelCount){
                    val num = it - defenderProfile.modelCount
                    it - num
                } else{
                    it
                }
            }.toMutableList()
            val endTime = (System.currentTimeMillis() - startTime).toFloat()
            Log.i("Simulator", "it took ${endTime/1000} seconds")
            val killsMap = killsList.groupingBy { it }.eachCount()
            val expectedKills = killsMap.maxBy { it.value }.key
            val expectedPercentage = (killsMap[expectedKills]!!.toFloat()/100)

            val destroyUnitPercentage =
                if(killsMap.containsKey(defenderProfile.modelCount)) (killsMap[defenderProfile.modelCount]!!.toFloat()/100)
                else 0F

            return SimulationResults(expectedKills, expectedPercentage, destroyUnitPercentage, attackSimulation)

        }

        private fun determineNumAttacks(attackerWeapons: List<Weapon>): List<Int>{
            var numAttacks: Int
            val attacksWeaponsList = mutableListOf<Int>()
            attackerWeapons.forEach { weapon ->
                numAttacks = 0
                for(i in 1..weapon.numPerUnit) {
                    numAttacks += attackStringToInt(weapon.numAttacks, "attacks", weapon.name)
                }
                attacksWeaponsList.add(numAttacks)
            }
            return attacksWeaponsList
        }

        private fun rollToHit(attackerWeapons: List<Weapon>, numAttacksList: List<Int>): List<Int>{
            var hitRoll: Int
            var numHits: Int
            var weapon: Weapon
            val hitWeaponsList = mutableListOf<Int>()

            for(i in 0..<attackerWeapons.count()){
                numHits = 0
                weapon = attackerWeapons[i]
                if(!attackerWeapons[i].abilities.contains(Ability.TORRENT)){
                    for (j in 0..<numAttacksList[i]){
                        hitRoll = Random.nextInt(1, 7)
                        if(hitRoll >= weapon.attack.toHit!!){
                            numHits++
                        }
                    }
                }
                else{
                    numHits = numAttacksList[i]
                }
                hitWeaponsList.add(numHits)
            }

            return hitWeaponsList

        }

        private fun rollToWound(attackerWeapons: List<Weapon>, numHitsList: List<Int>, defenderProfile: Profile): List<Int>{
            var woundRoll: Int
            var numWounds: Int
            var weapon: Weapon
            val woundWeaponsList = mutableListOf<Int>()

            for(i in 0..<attackerWeapons.count()){
                numWounds = 0
                weapon = attackerWeapons[i]
                if(numHitsList[i] > 0){
                    for (j in 1..numHitsList[i]){
                        woundRoll = Random.nextInt(1, 7)
                        if(checkIfWounded(woundRoll, weapon.attack.strength, defenderProfile.toughness, 0)){
                            numWounds++
                        }
                    }
                }
                woundWeaponsList.add(numWounds)
            }


            return woundWeaponsList
        }

        private fun rollSave(attackerWeapons: List<Weapon>, numWoundsList: List<Int>, defenderProfile: Profile): List<Int> {
            var saveRoll: Int
            var numNotSaved: Int
            var weapon: Weapon
            val saveWeaponsList = mutableListOf<Int>()

            for(i in 0..<attackerWeapons.count()){
                numNotSaved = 0
                weapon = attackerWeapons[i]
                if(numWoundsList[0] > 0) {
                    for (j in 1..numWoundsList[i]) {
                        saveRoll = Random.nextInt(1, 7)
                        if (!checkIfSaved(saveRoll, weapon.attack.ap,
                                defenderProfile.save, defenderProfile.invulnerable)) {
                            numNotSaved++
                        }
                    }
                }
                saveWeaponsList.add(numNotSaved)
            }

            return saveWeaponsList
        }

        private fun determineDamage(attackerWeapons: List<Weapon>, failedSavesList: List<Int>, defenderProfile: Profile): Pair<Int, List<Int>>{
            var woundsLeft = defenderProfile.wounds
            var modelsKilled = 0
            var weapon: Weapon
            var damage: Int
            var damageDealt: Int
            val damageDealtList = mutableListOf<Int>()

            for(i in 0..<attackerWeapons.count()){
                damageDealt = 0
                weapon = attackerWeapons[i]
                for(j in 1..failedSavesList[i]) {
                    damage = attackStringToInt(weapon.attack.damage, "damage", weapon.name)
                    woundsLeft -= damage
                    if (woundsLeft <= 0) {
                        damageDealt++
                        modelsKilled++
                        woundsLeft = 0
                    }
                }
                damageDealtList.add(damageDealt)
            }
            return Pair(modelsKilled, damageDealtList)
        }

        private fun checkIfWounded(roll: Int, strength: Int, toughness: Int, modifier: Int): Boolean {
            if(roll == 6){
                return true
            }
            if(roll == 1){
                return false
            }

            if(strength == toughness && roll+modifier >= 4){
                return true
            }
            else if((strength > toughness && strength < toughness*2) && roll+modifier >= 3){
                return true
            }
            else if(strength >= toughness *2 && roll+modifier >= 2){
                return true
            }
            else if((strength < toughness && strength > toughness/2) && roll+modifier >= 5){
                return true
            }
            else if(strength <= toughness/2 && roll+modifier >= 6){
                return true
            }
            return false
        }

        private fun checkIfSaved(roll: Int, ap: Int, save: Int, invulnerable: Int): Boolean{
            if(roll == 1){
                return false
            }

            else if(roll - ap < save || roll < invulnerable){
                return false
            }
            return true
        }

        private fun attackStringToInt(string: String, from: String, weaponName: String): Int{
            var tempInt = 0

            //check to see if the provided value will require a random roll
            if (string.toIntOrNull() == null) {
                val numbersList = Regex("[0-9]+").findAll(string)
                    .map(MatchResult::value).map { it.toInt() }.toList()
                //check for d(x)
                if (numbersList.count() == 1) {
                    tempInt = Random.nextInt(1, string.filter { it.isDigit() }.toInt() + 1)
                }
                //check case for d(x) + (y)
                else if(numbersList.count() == 2 && string.contains('+')){
                    tempInt = Random.nextInt(1, numbersList[0] + 1) +
                            numbersList[1]
                }
                //check case for (x)d(y)
                else if(numbersList.count() == 2 && !string.contains('+')){
                    tempInt = 0
                    for(i in 1..numbersList[0]){
                        tempInt += Random.nextInt(1, numbersList[1] + 1)
                    }
                }
                //check cae for (x)d(y) + (z)
                else if(numbersList.count() == 3){
                    tempInt = numbersList[2]
                    for(i in 1..numbersList[0]){
                        tempInt += Random.nextInt(1, numbersList[1] + 1)
                    }
                }
            } else {
                tempInt = string.toInt()
            }
            return tempInt
        }
    }
}

data class SimulationResults(val expectedResult: Int, val expectedPercent: Float,
                             val destroyUnitPercent: Float, val attackSequence: AttackSequence)