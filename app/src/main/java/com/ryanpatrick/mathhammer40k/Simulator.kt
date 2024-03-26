package com.ryanpatrick.mathhammer40k

import android.util.Log
import com.ryanpatrick.mathhammer40k.data.Ability
import com.ryanpatrick.mathhammer40k.data.AttackSequence
import com.ryanpatrick.mathhammer40k.data.GlobalEffects
import com.ryanpatrick.mathhammer40k.data.Profile
import com.ryanpatrick.mathhammer40k.data.Weapon
import kotlin.random.Random

class Simulator {
    companion object{
        private val TAG = "Simulator"
        fun runSimulation(type: String, modifiersList: List<GlobalEffects>, attackerWeapons: List<Weapon>,
                          tempAbilities: List<Ability>, defenderProfile: Profile): SimulationResults{
            val attackSimulation = AttackSequence()
            var simulationAverages = AttackSequence()
            var numAttacks: List<Int>
            var numHits: List<Int>
            var numWounds:List<Int>
            var numFailedSaves: List<Int>
            var damageDealt: List<Int>
            var killsList = mutableListOf<Int>()
            var modelsKilled: Int
            val startTime = System.currentTimeMillis()

            attackerWeapons.forEach { weapon ->
                weapon.abilities.addAll(tempAbilities.filter { !weapon.abilities.contains(it) })
            }

            attackerWeapons.forEach { weapon->
                attackSimulation.weaponName.add(weapon.name)
                simulationAverages.weaponName.add(weapon.name)
            }

            for(i in 1..10000) {
                //region determine number of attacks
                numAttacks = determineNumAttacks(attackerWeapons)
                attackSimulation.numAttacks.addAll(numAttacks)
                for(j in 0..<simulationAverages.weaponName.size){
                    if(simulationAverages.numAttacks.isEmpty()){
                        simulationAverages.numAttacks.addAll(numAttacks)
                    }
                    else{
                        simulationAverages.numAttacks[j]+=numAttacks[j]
                    }
                }
                //endregion

                //region determine number of hits
                numHits = rollToHit(attackerWeapons, attackSimulation.numAttacks)
                attackSimulation.numHits.addAll(numHits)
                for(j in 0..<simulationAverages.weaponName.size){
                    if(simulationAverages.numHits.isEmpty()){
                        simulationAverages.numHits.addAll(numHits)
                    }
                    else{
                        simulationAverages.numHits[j]+=numHits[j]
                    }
                }
                //endregion

                //region determine number of wounds
                numWounds = rollToWound(attackerWeapons, attackSimulation.numHits, defenderProfile)
                attackSimulation.numWounds.addAll(numWounds)
                for(j in 0..<simulationAverages.weaponName.size){
                    if(simulationAverages.numWounds.isEmpty()){
                        simulationAverages.numWounds.addAll(numWounds)
                    }
                    else{
                        simulationAverages.numWounds[j]+=numWounds[j]
                    }
                }
                //endregion

                //region determine number of failed saves
                numFailedSaves = rollSave(attackerWeapons, attackSimulation.numWounds, defenderProfile)
                attackSimulation.numFailedSaves.addAll(numFailedSaves)
                for(j in 0..<simulationAverages.weaponName.size){
                    if(simulationAverages.numFailedSaves.isEmpty()){
                        simulationAverages.numFailedSaves.addAll(numFailedSaves)
                    }
                    else{
                        simulationAverages.numFailedSaves[j]+=numFailedSaves[j]
                    }
                }
                //endregion

                //region determine damage dealt
                val pair = determineDamage(attackerWeapons, attackSimulation.numFailedSaves, defenderProfile)
                modelsKilled = pair.first
                damageDealt = pair.second
                attackSimulation.damageDealt.addAll(damageDealt)
                for(j in 0..<simulationAverages.weaponName.size){
                    if(simulationAverages.damageDealt.isEmpty()){
                        simulationAverages.damageDealt.addAll(damageDealt)
                    }
                    else{
                        simulationAverages.damageDealt[j]+=damageDealt[j]
                    }
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
            for(i in 0..<simulationAverages.weaponName.count()){
                simulationAverages.numAttacks[i] /= 10000
                simulationAverages.numHits[i] /= 10000
                simulationAverages.numWounds[i] /= 10000
                simulationAverages.numFailedSaves[i] /= 10000
                simulationAverages.damageDealt[i] /= 10000
            }
            val endTime = (System.currentTimeMillis() - startTime).toFloat()
            Log.i("Simulator", "it took ${endTime/1000} seconds")

            val killsMap = killsList.groupingBy { it }.eachCount()
            val expectedKills = killsMap.maxBy { it.value }.key
            var expectedPercentage = 0F
            killsMap.forEach { (i, k) ->
                if(i >= expectedKills){
                    expectedPercentage += k.toFloat()
                }
            }
            expectedPercentage /= 100F

            val destroyUnitPercentage =
                if(killsMap.containsKey(defenderProfile.modelCount)) (killsMap[defenderProfile.modelCount]!!.toFloat()/100)
                else 0F

            return SimulationResults(expectedKills, expectedPercentage, destroyUnitPercentage, simulationAverages)

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
            var criticalHit = 6
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
            var criticalWound = 6
            if(roll == criticalWound){
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