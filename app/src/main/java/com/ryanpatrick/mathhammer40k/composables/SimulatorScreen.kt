package com.ryanpatrick.mathhammer40k.composables

import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ryanpatrick.mathhammer40k.SimulationResults
import com.ryanpatrick.mathhammer40k.Simulator
import com.ryanpatrick.mathhammer40k.data.Ability
import com.ryanpatrick.mathhammer40k.data.GlobalEffects
import com.ryanpatrick.mathhammer40k.data.Profile
import com.ryanpatrick.mathhammer40k.data.Weapon
import com.ryanpatrick.mathhammer40k.data.title
import com.ryanpatrick.mathhammer40k.viewmodel.ProfileViewModel

val TAG = "SimulationScreen"
val weaponsListState = mutableStateListOf<Weapon>()
var weaponsList = mutableListOf<Weapon>()
val globalType = mutableStateOf("Ranged")
val modifiersList = mutableListOf<GlobalEffects>()
val tempAbilities = mutableListOf<Ability>()
val tempAbilitiesState = mutableStateListOf<Ability>()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimulatorScreen(profileViewModel: ProfileViewModel){
    //region variables and values
    val context = LocalContext.current
    val allProfiles = profileViewModel.getAllProfiles.collectAsState(listOf())
    val attackerProfile = remember{mutableStateOf(Profile(0, "", listOf(),
        keywords = listOf(), roles = listOf()))}
    val defenderProfile = remember{mutableStateOf(Profile(0, "", listOf(),
        keywords = listOf(), roles = listOf()))}

    val showAttackerDialog = remember{mutableStateOf(false)}
    val showDefenderDialog = remember{mutableStateOf(false)}
    val showAbilityDialog = remember{mutableStateOf(false)}

    val attackerDropdownExpanded = remember{mutableStateOf(false)}
    val defenderDropdownExpanded = remember{mutableStateOf(false)}
    val abilitiesDropdwonExpanded = remember{mutableStateOf(false)}

    val stationaryClicked = remember{mutableStateOf(false)}
    val coverClicked = remember{mutableStateOf(false)}
    val halfRangeClicked = remember{mutableStateOf(false)}
    val chargedClicked = remember{mutableStateOf(false)}

    val expectedResults = remember{mutableStateOf("Run Simulation")}
    val expectedPercentage = remember{mutableStateOf("Run Simulation")}
    val destroyUnitPercentage = remember{mutableStateOf("Run Simulation")}

    val selectedAbility = remember{ mutableStateOf(Ability.SUSTAINED_1) }

    var simulationResults: SimulationResults
    var selectedProfile: Profile
    //endregion

    Column(modifier = Modifier.fillMaxSize().padding(8.dp).verticalScroll(rememberScrollState(), enabled = true),
        horizontalAlignment = Alignment.CenterHorizontally){
        //region attacker
        var isAttackerExpanded by remember{ mutableStateOf(true) }
        if(attackerProfile.value.id == 0L){
            attackerProfile.value = profileViewModel.getProfileById(1).collectAsState(Profile(0, "", listOf(),
                keywords = listOf(), roles = listOf())).value
            setDefaultWeapons(attackerProfile.value.weapons)
        }
        Column(modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(8.dp)
            .clickable { isAttackerExpanded = !isAttackerExpanded },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            ExpandableSectionTitle(isExpanded = isAttackerExpanded, title = "Attacker")
            AnimatedVisibility(modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
                .fillMaxWidth().padding(8.dp), visible = isAttackerExpanded){
                Column {
                    //region buttons row
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Button(onClick = { showAttackerDialog.value = true }, contentPadding = PaddingValues(4.dp),
                                modifier = Modifier.padding(4.dp)){
                                Text("Select Profile", fontSize = 12.sp)
                        }
                        Row{
                            Button(onClick = {
                                    val hasWeapons = switchWeaponTypes(attackerProfile.value.weapons, "Melee")
                                    if(!hasWeapons){
                                        Toast.makeText(context, "Profile has no melee weapons", Toast.LENGTH_SHORT).show()
                                    }
                                }, contentPadding = PaddingValues(4.dp), modifier = Modifier.padding(4.dp)){
                                Text("Melee", fontSize = 12.sp)
                            }
                            Button(onClick = {
                                    val hasWeapons = switchWeaponTypes(attackerProfile.value.weapons, "Ranged")
                                    if(!hasWeapons){
                                        Toast.makeText(context, "Profile has no ranged weapons", Toast.LENGTH_SHORT).show()
                                    }
                                }, contentPadding = PaddingValues(4.dp),
                                modifier = Modifier.padding(4.dp)){
                                Text("Ranged", fontSize = 12.sp)
                            }
                        }
                    }
                    //endregion
                    Text(attackerProfile.value.profileName)
                    for(weapon in weaponsListState){
                        SimulatorWeaponsListItem(weapon)
                    }
                }
            }
        }

        //select attacker dialog
        if(showAttackerDialog.value){
            selectedProfile = attackerProfile.value
            AlertDialog(onDismissRequest = {},
                confirmButton = {Row(modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Button(onClick = {
                        attackerProfile.value = selectedProfile.copy()
                        weaponsListState.clear()
                        weaponsListState.addAll(attackerProfile.value.weapons)
                        showAttackerDialog.value = false
                    }){
                        Text(text = "Confirm")
                    }
                    Button(onClick = { showAttackerDialog.value = false }){
                        Text(text = "Cancel")
                    }
                }},
                title = {Text("Select Attacker")},
                text = {
                    ExposedDropdownMenuBox(expanded = attackerDropdownExpanded.value,
                        onExpandedChange = {attackerDropdownExpanded.value = !attackerDropdownExpanded.value}){
                        TextField(value = selectedProfile.profileName, onValueChange = {}, readOnly = true, modifier = Modifier.menuAnchor(),
                            trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(attackerDropdownExpanded.value)})
                        ExposedDropdownMenu(expanded = attackerDropdownExpanded.value, onDismissRequest = {attackerDropdownExpanded.value = false}){
                            allProfiles.value.forEach{profile->
                                DropdownMenuItem(text = {Text(profile.profileName)},
                                    onClick = {
                                        selectedProfile = profile
                                        attackerDropdownExpanded.value = false
                                    }
                                )
                            }
                        }
                    }
                }
            )
        }
        //endregion

        //region defender
        var isDefenderExpanded by remember { mutableStateOf(true) }
        if(defenderProfile.value.id == 0L){
            defenderProfile.value = profileViewModel.getProfileById(1).collectAsState(Profile(0, "", listOf(),
                keywords = listOf(), roles = listOf())).value
        }
        var keywordsString = ""
        if(defenderProfile.value.keywords.size == 1){
            keywordsString = defenderProfile.value.keywords[0].title
        }
        else{
            for(keyword in defenderProfile.value.keywords){
                keywordsString += if(defenderProfile.value.keywords.last() == keyword)
                    keyword.title else "${keyword.title}, "

            }
        }
        Column(modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(8.dp)
            .clickable { isDefenderExpanded = !isDefenderExpanded },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            ExpandableSectionTitle(isExpanded = isDefenderExpanded, title = "Defender")
            AnimatedVisibility(modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
                .fillMaxWidth().padding(8.dp), visible = isDefenderExpanded){
                Column{
                    Row{
                        Button(onClick = {showDefenderDialog.value = true}, contentPadding = PaddingValues(4.dp),
                            modifier = Modifier.padding(4.dp)){
                            Text("Select Profile", fontSize = 12.sp)
                        }
                    }
                    Text(defenderProfile.value.profileName)
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Column(modifier = Modifier.fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally){
                            Text(text = "Models", fontSize = 12.sp)
                            Text("${defenderProfile.value.modelCount}", fontSize = 12.sp)
                        }
                        Column(modifier = Modifier.fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally){
                            Text(text = "Toughness", fontSize = 12.sp)
                            Text("${defenderProfile.value.toughness}", fontSize = 12.sp)
                        }
                        Column(modifier = Modifier.fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally){
                            Text(text = "Wounds", fontSize = 12.sp)
                            Text("${defenderProfile.value.wounds}", fontSize = 12.sp)
                        }
                        Column(modifier = Modifier.fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally){
                            Text(text = "Save", fontSize = 12.sp)
                            Text("${defenderProfile.value.save}+", fontSize = 12.sp)
                        }
                        if(defenderProfile.value.invulnerable > 0){
                            Column(modifier = Modifier.fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally){
                                Text(text = "Invul Save", fontSize = 12.sp)
                                Text("${defenderProfile.value.invulnerable}+", fontSize = 12.sp)
                            }
                        }
                        if(defenderProfile.value.feelNoPain > 0){
                            Column(modifier = Modifier.fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally){
                                Text(text = "Feel no Pain", fontSize = 12.sp)
                                Text("${defenderProfile.value.feelNoPain}+", fontSize = 12.sp)
                            }
                        }
                    }
                    Text("Keywords: $keywordsString")
                }
            }
        }

        //select defender dialog
        if(showDefenderDialog.value){
            selectedProfile = defenderProfile.value
            AlertDialog(onDismissRequest = {},
                confirmButton = {Row(modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Button(onClick = {
                        defenderProfile.value = selectedProfile.copy()
                        showDefenderDialog.value = false
                    }){
                        Text(text = "Confirm")
                    }
                    Button(onClick = { showDefenderDialog.value = false }){
                        Text(text = "Cancel")
                    }
                }},
                title = {Text("Select Defender")},
                text = {
                    ExposedDropdownMenuBox(expanded = defenderDropdownExpanded.value,
                        onExpandedChange = {defenderDropdownExpanded.value = !defenderDropdownExpanded.value}){
                        TextField(value = selectedProfile.profileName, onValueChange = {}, readOnly = true, modifier = Modifier.menuAnchor(),
                            trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(defenderDropdownExpanded.value)})
                        ExposedDropdownMenu(expanded = defenderDropdownExpanded.value, onDismissRequest = {defenderDropdownExpanded.value = false}){
                            allProfiles.value.forEach{profile->
                                DropdownMenuItem(text = {Text(profile.profileName)},
                                    onClick = {
                                        selectedProfile = profile
                                        defenderDropdownExpanded.value = false
                                    }
                                )
                            }
                        }
                    }
                }
            )
        }
        //endregion

        //region global effects
        var isEffectsExpanded by remember { mutableStateOf(true) }
        Column(modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(8.dp)
            .clickable { isEffectsExpanded = !isEffectsExpanded },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            ExpandableSectionTitle(isExpanded = isEffectsExpanded, title = "Global Effects")
            AnimatedVisibility(modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
                .fillMaxWidth().padding(8.dp), visible = isEffectsExpanded){
                Column{
                    //Attack Context Row
                    Text("Attack Context", fontSize = 18.sp)
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween){
                        if(globalType.value == "Ranged"){
                            Row(modifier = Modifier.fillMaxWidth().weight(1f), verticalAlignment = Alignment.CenterVertically){
                                Checkbox(checked = stationaryClicked.value, onCheckedChange = {
                                    if(modifiersList.contains(GlobalEffects.STATIONARY)){
                                        modifiersList.remove(GlobalEffects.STATIONARY)
                                    }
                                    else{
                                        modifiersList.add(GlobalEffects.STATIONARY)
                                    }
                                    stationaryClicked.value = !stationaryClicked.value
                                })
                                Text(GlobalEffects.STATIONARY.title, fontSize = 12.sp)
                            }
                            Row(modifier = Modifier.fillMaxWidth().weight(1f), verticalAlignment = Alignment.CenterVertically){
                                Checkbox(checked = coverClicked.value, onCheckedChange = {
                                    if(modifiersList.contains(GlobalEffects.COVER)){
                                        modifiersList.remove(GlobalEffects.COVER)
                                    }
                                    else{
                                        modifiersList.add(GlobalEffects.COVER)
                                    }
                                    coverClicked.value = !coverClicked.value
                                })
                                Text(GlobalEffects.COVER.title, fontSize = 12.sp)
                            }
                            Row(modifier = Modifier.fillMaxWidth().weight(1f), verticalAlignment = Alignment.CenterVertically){
                                Checkbox(checked = halfRangeClicked.value, onCheckedChange = {
                                    if(modifiersList.contains(GlobalEffects.HALF_RANGE)){
                                        modifiersList.remove(GlobalEffects.HALF_RANGE)
                                    }
                                    else{
                                        modifiersList.add(GlobalEffects.HALF_RANGE)
                                    }
                                    halfRangeClicked.value = !halfRangeClicked.value
                                })
                                Text(GlobalEffects.HALF_RANGE.title, fontSize = 12.sp)
                            }
                        }
                        else if(globalType.value == "Melee"){
                            Row(verticalAlignment = Alignment.CenterVertically){
                                Checkbox(checked = chargedClicked.value, onCheckedChange = {
                                    if(modifiersList.contains(GlobalEffects.CHARGED)){
                                        modifiersList.remove(GlobalEffects.CHARGED)
                                    }
                                    else{
                                        modifiersList.add(GlobalEffects.CHARGED)
                                    }
                                    chargedClicked.value = !chargedClicked.value
                                })
                                Text(GlobalEffects.CHARGED.title, fontSize = 12.sp)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    //Global Abilities Row
                    Text("Global Abilities", fontSize = 18.sp)
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End){
                        tempAbilitiesState.forEach {ability ->
                            GlobalAbilitiesListItem(ability)
                        }
                        Button(onClick = {showAbilityDialog.value = true}){
                            Text("Add Ability")
                        }
                    }
                }
            }
        }

        //Add ability dialog
        if(showAbilityDialog.value){
            AlertDialog(onDismissRequest = {},
                confirmButton = {Row(modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Button(onClick = {
                        if(tempAbilitiesState.count()<=6){
                            tempAbilitiesState.add(selectedAbility.value)
                            tempAbilities.add(selectedAbility.value)
                            showAbilityDialog.value = false
                        }
                        else{
                            Toast.makeText(context, "Max of 6 abilities", Toast.LENGTH_SHORT).show()
                        }
                    }){
                        Text(text = "Confirm")
                    }
                    Button(onClick = { showAttackerDialog.value = false }){
                        Text(text = "Cancel")
                    }
                }},
                title = {Text("Add Ability")},
                text = {
                    ExposedDropdownMenuBox(expanded = abilitiesDropdwonExpanded.value,
                        onExpandedChange = {abilitiesDropdwonExpanded.value = !abilitiesDropdwonExpanded.value}){
                        TextField(value = selectedAbility.value.title, onValueChange = {}, readOnly = true, modifier = Modifier.menuAnchor(),
                            trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(abilitiesDropdwonExpanded.value)})
                        ExposedDropdownMenu(expanded = abilitiesDropdwonExpanded.value, onDismissRequest = {abilitiesDropdwonExpanded.value = false}){
                            Ability.entries.forEach { ability ->
                                DropdownMenuItem(text = {Text(ability.title)},
                                    onClick = {
                                        selectedAbility.value = ability
                                        abilitiesDropdwonExpanded.value = false
                                    }
                                )
                            }
                        }
                    }
                }
            )
        }
        //endregion

        //region simulator
        var isSimulatorExpanded by remember {mutableStateOf(true)}
        Column(modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(8.dp)
            .clickable { isSimulatorExpanded = !isSimulatorExpanded },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            ExpandableSectionTitle(isExpanded = isSimulatorExpanded, title = "Simulator")
            AnimatedVisibility(modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
                .fillMaxWidth().padding(8.dp), visible = isSimulatorExpanded){
                Column{
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Text("Expected Results: ")
                        Text(expectedResults.value)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Text("Chance of x or better: ")
                        Text(expectedPercentage.value)
                    }
                    if(expectedPercentage.value != destroyUnitPercentage.value){
                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween){
                            Text("Chance to kill: ")
                            Text(destroyUnitPercentage.value)
                        }
                    }

                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End){
                        Button(onClick = {
                            simulationResults = Simulator.runSimulation(globalType.value, modifiersList, weaponsList,
                                tempAbilities, defenderProfile.value)
                            expectedResults.value = "${simulationResults.expectedResult}"
                            expectedPercentage.value = "${simulationResults.expectedPercent}%"
                            destroyUnitPercentage.value = "${simulationResults.destroyUnitPercent}%"
                        }){
                            Text("Run Simulations")
                        }
                    }
                }
            }
        }
        //endregion
    }
}

@Composable
fun ExpandableSectionTitle(modifier: Modifier = Modifier, isExpanded: Boolean, title: String){
    val icon = if(isExpanded) Icons.Rounded.KeyboardArrowDown else Icons.Rounded.KeyboardArrowDown
    Row(modifier = modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primaryContainer),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
        Row(modifier = modifier.wrapContentWidth()){
            Text(title, style = MaterialTheme.typography.headlineMedium, modifier = modifier.padding(8.dp))
        }
        Image(modifier = modifier.size(32.dp).padding(8.dp), imageVector = icon, contentDescription = null,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimaryContainer))
    }
}

@Composable
fun SimulatorWeaponsListItem(weapon: Weapon){
    var abilitiesString = ""
    val fontSize = 12.sp
    val checked = remember{ mutableStateOf(true) }
    val hitText = if(weapon.attack.toHit == null) "-" else "${weapon.attack.toHit}+"

    checked.value = weaponsList.contains(weapon)

    if(weapon.abilities.count() == 1){
        abilitiesString = weapon.abilities[0].title
    }
    else{
        for (ability in weapon.abilities){
            abilitiesString += if(weapon.abilities.last() != ability) ability.title + ", "
             else ability.title

        }
    }

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked.value,
            onCheckedChange = {
                checked.value = !checked.value
                if(checked.value) weaponsList.add(weapon)
                else weaponsList.remove(weapon)
        })
        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)){
            Text(weapon.name)
            Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly){
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "Count", fontSize = fontSize)
                    Text(text = "${weapon.numPerUnit}", fontSize = fontSize)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "Attacks", fontSize = fontSize)
                    Text(text = weapon.numAttacks, fontSize = fontSize)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "To Hit", fontSize = fontSize)
                    Text(text = hitText, fontSize = fontSize)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "Strength", fontSize = fontSize)
                    Text(text = "${weapon.attack.strength}", fontSize = fontSize)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "AP", fontSize = fontSize)
                    Text(text = if(weapon.attack.ap == 0) "${weapon.attack.ap}"
                    else "-${weapon.attack.ap}", fontSize = fontSize)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "Damage", fontSize = fontSize)
                    Text(weapon.attack.damage, fontSize = fontSize)
                }
            }
            if(weapon.abilities.isNotEmpty()){
                Text("Abilities: $abilitiesString")
            }
        }
    }
}

@Composable
fun GlobalAbilitiesListItem(ability: Ability){
    val checked = remember{mutableStateOf(true)}

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
        Row(modifier = Modifier.fillMaxWidth().weight(5f), verticalAlignment = Alignment.CenterVertically){
            Checkbox(checked = checked.value, onCheckedChange = {
                checked.value = !checked.value
                if(checked.value){
                    tempAbilities.add(ability)
                }
                else{
                    tempAbilities.remove(ability)
                }
            })
            Text(ability.title)
        }
        IconButton(modifier = Modifier.fillMaxWidth().weight(1f), onClick = {
            tempAbilities.remove(ability)
            tempAbilitiesState.remove(ability)
        }){
            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
        }
    }

}

fun setDefaultWeapons(allWeaponsList: List<Weapon>){
    if(!allWeaponsList.none{it.type == "Ranged"}) {
        weaponsListState.clear()
        weaponsListState.addAll(allWeaponsList.filter { it.type == "Ranged" })
    }
    else if(allWeaponsList.none{it.type == "Ranged"} && !allWeaponsList.none{it.type == "Melee"}){
        weaponsListState.clear()
        weaponsListState.addAll(allWeaponsList.filter { it.type == "Melee" })
    }
    weaponsList = weaponsListState.toMutableList()
}

fun switchWeaponTypes(allWeaponsList: List<Weapon>, type: String): Boolean{
    weaponsListState.clear()
    if(!allWeaponsList.none { it.type == type }) {
        globalType.value = type
        weaponsListState.addAll(allWeaponsList.filter { it.type == type })
    }
    weaponsList = weaponsListState.toMutableList()
    return !allWeaponsList.none { it.type == type }
}