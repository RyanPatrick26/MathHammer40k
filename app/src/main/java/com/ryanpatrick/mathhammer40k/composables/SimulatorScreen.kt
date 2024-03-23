package com.ryanpatrick.mathhammer40k.composables

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ryanpatrick.mathhammer40k.Simulator
import com.ryanpatrick.mathhammer40k.data.Profile
import com.ryanpatrick.mathhammer40k.data.Weapon
import com.ryanpatrick.mathhammer40k.data.spaceMarineEquivalent
import com.ryanpatrick.mathhammer40k.data.title
import com.ryanpatrick.mathhammer40k.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimulatorScreen(profileViewModel: ProfileViewModel){
    Column(modifier = Modifier.fillMaxSize().padding(8.dp).verticalScroll(rememberScrollState(), enabled = true),
        horizontalAlignment = Alignment.CenterHorizontally){
        val allProfiles = profileViewModel.getAllProfiles.collectAsState(listOf())
        val attackerProfile = remember{mutableStateOf(Profile(0, "", listOf(),
            keywords = listOf(), roles = listOf()))}
        val defenderProfile = remember{mutableStateOf(Profile(0, "", listOf(),
            keywords = listOf(), roles = listOf()))}

        val showAttackerDialog = remember{mutableStateOf(false)}
        val showDefenderDialog = remember{mutableStateOf(false)}

        val attackerDropdownExpanded = remember{mutableStateOf(false)}
        val defenderDropdownExpanded = remember{mutableStateOf(false)}

        var selectedProfile: Profile

        //region attacker
        var isAttackerExpanded by remember{ mutableStateOf(true) }
        if(attackerProfile.value.id == 0L){
            attackerProfile.value = profileViewModel.getProfileById(3).collectAsState(Profile(0, "", listOf(),
                keywords = listOf(), roles = listOf())).value
        }
        Column(modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(8.dp)
            .clickable { isAttackerExpanded = !isAttackerExpanded },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            ExpandableSectionTitle(isExpanded = isAttackerExpanded, title = "Attacker")
            AnimatedVisibility(modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
                .fillMaxWidth().padding(8.dp), visible = isAttackerExpanded){
                Column {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Row{
                            Button(onClick = { showAttackerDialog.value = true }, contentPadding = PaddingValues(4.dp),
                                modifier = Modifier.padding(4.dp)){
                                Text("Select Profile", fontSize = 12.sp)
                            }
                        }
                        Row{
                            Button(onClick = {}, contentPadding = PaddingValues(4.dp),
                                modifier = Modifier.padding(4.dp)){
                                Text("Melee", fontSize = 12.sp)
                            }
                            Button(onClick = {}, contentPadding = PaddingValues(4.dp),
                                modifier = Modifier.padding(4.dp)){
                                Text("Ranged", fontSize = 12.sp)
                            }
                        }
                    }
                    Text(attackerProfile.value.profileName)
                    for(weapon in attackerProfile.value.weapons){
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
            defenderProfile.value = profileViewModel.getProfileById(2).collectAsState(Profile(0, "", listOf(),
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
                        Text("Test Data")
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Text("Chance of x or better: ")
                        Text("Test Data")
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Text("Chance to kill: ")
                        Text("Test Data")
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End){
                        Button(onClick = {Simulator.runSimulation(attackerProfile.value.weapons, defenderProfile.value)}){
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
    val hitText = if(weapon.attack.toHit == null) "-" else "${weapon.attack.toHit}+"

    if(weapon.abilities.count() == 1){
        abilitiesString = weapon.abilities[0].title
    }
    else{
        for (ability in weapon.abilities){
            if(weapon.abilities.last() != ability){
                abilitiesString += ability.title + ", "
            }
            else{
                abilitiesString += ability.title
            }
        }
    }

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