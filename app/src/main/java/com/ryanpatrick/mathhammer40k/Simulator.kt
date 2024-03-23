package com.ryanpatrick.mathhammer40k

import android.util.Log
import com.ryanpatrick.mathhammer40k.data.Ability
import com.ryanpatrick.mathhammer40k.data.AttackSequence
import com.ryanpatrick.mathhammer40k.data.Profile
import com.ryanpatrick.mathhammer40k.data.Weapon
import kotlin.random.Random

class Simulator {
    companion object{
        fun runSimulation(attackerWeapons: List<Weapon>, defenderProfile: Profile){
            val attackSimulation = AttackSequence(mutableListOf(),  mutableListOf(), mutableListOf(),
                mutableListOf(), mutableListOf(), mutableListOf())
            var modelsKilled: Int
            var testString = "Attacks\n"
            attackerWeapons.forEach { weapon->
                attackSimulation.weaponName.add(weapon.name)
            }
            //region determine number of attacks
            attackSimulation.numAttacks.addAll(determineNumAttacks(attackerWeapons))
            for(i in 0..<attackSimulation.weaponName.count()){
                testString += "${attackSimulation.weaponName[i]}: ${attackSimulation.numAttacks[i]} attacks"
                if(i < attackSimulation.weaponName.count()-1){
                    testString += "\n"
                }
            }
            Log.i("Simulator", testString)
            //endregion

            //region determine number of hits
            attackSimulation.numHits.addAll(rollToHit(attackerWeapons, attackSimulation.numAttacks))
            testString = "Hits\n"
            for(i in attackSimulation.weaponName.indices){
                testString += "${attackSimulation.weaponName[i]}: ${attackSimulation.numHits[i]} Hits"
                if(i < attackSimulation.weaponName.count()-1){
                    testString += "\n"
                }
            }
            Log.i("Simulator", testString)
            //endregion

            //region determine number of wounds
            attackSimulation.numWounds.addAll(rollToWound(attackerWeapons, attackSimulation.numHits, defenderProfile))
            testString = "Wounds\n"
            for(i in attackSimulation.weaponName.indices){
                testString += "${attackSimulation.weaponName[i]}: ${attackSimulation.numWounds[i]} wounds"
                if(i < attackSimulation.weaponName.count()-1){
                    testString += "\n"
                }
            }
            Log.i("Simulator", testString)
            //endregion

            //region determine number of failed saves
            attackSimulation.numFailedSaves.addAll(rollSave(attackerWeapons, attackSimulation.numWounds, defenderProfile))
            testString = "Failed Saves\n"
            for(i in attackSimulation.weaponName.indices){
                testString += "${attackSimulation.weaponName[i]}: ${attackSimulation.numFailedSaves[i]} failed saves"
                if(i < attackSimulation.weaponName.count()-1){
                    testString += "\n"
                }
            }
            Log.i("Simulator", testString)
            //endregion

            //region determine damage dealt
            val pair = determineDamage(attackerWeapons, attackSimulation.numFailedSaves, defenderProfile)
            modelsKilled = pair.first
            attackSimulation.damageDealt.addAll(pair.second)
            //endregion

            /*attackSimulation.weaponName.clear()
            attackSimulation.numAttacks.clear()
            attackSimulation.numHits.clear()
            attackSimulation.numWounds.clear()
            attackSimulation.numFailedSaves.clear()
            attackSimulation.damageDealt.clear()*/
        }

        private fun determineNumAttacks(attackerWeapons: List<Weapon>): List<Int>{
            var numAttacks: Int
            val attacksWeaponsList = mutableListOf<Int>()
            attackerWeapons.forEach { weapon ->
                numAttacks = 0
                if(weapon.numAttacks.toIntOrNull() == null){
                    if(weapon.numAttacks.count { it.isDigit() } == 1){
                        numAttacks = weapon.numAttacks.filter { it.isDigit() }.toInt()
                        numAttacks = Random.nextInt(1, numAttacks+1)
                    }
                    else{
                        numAttacks = Random.nextInt(1, weapon.numAttacks.first { it.isDigit() }.digitToInt()+1)+
                                weapon.numAttacks.last { it.isDigit() }.digitToInt()
                    }
                }
                else{
                    numAttacks = weapon.numAttacks.toInt()
                }
                numAttacks *= weapon.numPerUnit
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

            for(i in 0..< numHitsList.count()){
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

            for(i in 0..< numWoundsList.count()){
                numNotSaved = 0
                weapon = attackerWeapons[i]
                //Log.i("Simulator", weapon.name)
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
            var testString = "Damage\n"
            val damageDealtList = mutableListOf<Int>()
            for(i in 0..<failedSavesList.count()){
                damageDealt = 0
                weapon = attackerWeapons[i]
                for(j in 1..failedSavesList[i]) {
                    if (weapon.attack.damage.toIntOrNull() == null) {
                        if (weapon.attack.damage.count { it.isDigit() } == 1) {
                            damage = weapon.attack.damage.filter { it.isDigit() }.toInt()
                            damage = Random.nextInt(1, damage + 1)
                        } else {
                            damage = Random.nextInt(
                                1,
                                weapon.attack.damage.first { it.isDigit() }.digitToInt() + 1
                            ) +
                                    weapon.attack.damage.last { it.isDigit() }.digitToInt()
                        }
                    } else {
                        damage = weapon.attack.damage.toInt()
                    }
                    woundsLeft -= damage
                    if (woundsLeft <= 0) {
                        damageDealt++
                        modelsKilled++
                        woundsLeft = 0
                    }
                }
                damageDealtList.add(damageDealt)
            }


            testString += "Models Killed: $modelsKilled"
            Log.i("Simulator", testString)
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
            //Log.i("Simulator", "Roll: $roll, ap: $ap, Save: $save, Invulnerable: $invulnerable")
            if(roll == 1){
                //Log.i("Simulator", "failed")
                return false
            }

            else if(roll - ap < save || roll < invulnerable){
                //Log.i("Simulator", "failed")
                return false
            }

            //Log.i("Simulator", "true")
            return true
        }
    }
}