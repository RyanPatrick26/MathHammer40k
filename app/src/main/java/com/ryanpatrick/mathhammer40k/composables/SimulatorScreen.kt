package com.ryanpatrick.mathhammer40k.composables

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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ryanpatrick.mathhammer40k.data.Weapon
import com.ryanpatrick.mathhammer40k.data.intercessorWeapons
import com.ryanpatrick.mathhammer40k.data.spaceMarineEquivalent
import com.ryanpatrick.mathhammer40k.data.title

@Composable
fun SimulatorScreen(){
    Column(modifier = Modifier.fillMaxSize().padding(8.dp).verticalScroll(rememberScrollState(), enabled = true),
        horizontalAlignment = Alignment.CenterHorizontally){
        //region attacker
        var isAttackerExpanded by remember{ mutableStateOf(true) }
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
                            Button(onClick = {}, contentPadding = PaddingValues(4.dp),
                                modifier = Modifier.padding(4.dp)){
                                Text("Saved Profiles", fontSize = 12.sp)
                            }
                            Button(onClick = {}, contentPadding = PaddingValues(4.dp),
                                modifier = Modifier.padding(4.dp)){
                                Text("Edit Profile", fontSize = 12.sp)
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
                    Text("Attacker")
                    for(weapon in intercessorWeapons){
                        SimulatorWeaponsListItem(weapon)
                    }
                }
            }
        }
        //endregion

        //region defender
        var isDefenderExpanded by remember { mutableStateOf(true) }
        var keywordsString = ""
        if(spaceMarineEquivalent.keywords.size == 1){
            keywordsString = spaceMarineEquivalent.keywords[0].title
        }
        else{
            for(keyword in spaceMarineEquivalent.keywords){
                keywordsString += if(spaceMarineEquivalent.keywords.last() == keyword)
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
                        Button(onClick = {}, contentPadding = PaddingValues(4.dp),
                            modifier = Modifier.padding(4.dp)){
                            Text("Saved Profiles", fontSize = 12.sp)
                        }
                        Button(onClick = {}, contentPadding = PaddingValues(4.dp),
                            modifier = Modifier.padding(4.dp)){
                            Text("Edit Profile", fontSize = 12.sp)
                        }
                    }
                    Text(spaceMarineEquivalent.profileName)
                    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Column(modifier = Modifier.fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally){
                            Text(text = "Models", fontSize = 12.sp)
                            Text("${spaceMarineEquivalent.modelCount}", fontSize = 12.sp)
                        }
                        Column(modifier = Modifier.fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally){
                            Text(text = "Toughness", fontSize = 12.sp)
                            Text("${spaceMarineEquivalent.toughness}", fontSize = 12.sp)
                        }
                        Column(modifier = Modifier.fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally){
                            Text(text = "Wounds", fontSize = 12.sp)
                            Text("${spaceMarineEquivalent.wounds}", fontSize = 12.sp)
                        }
                        Column(modifier = Modifier.fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally){
                            Text(text = "Save", fontSize = 12.sp)
                            Text("${spaceMarineEquivalent.save}+", fontSize = 12.sp)
                        }
                        if(spaceMarineEquivalent.invulnerable > 0){
                            Column(modifier = Modifier.fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally){
                                Text(text = "Invul Save", fontSize = 12.sp)
                                Text("${spaceMarineEquivalent.invulnerable}+", fontSize = 12.sp)
                            }
                        }
                        if(spaceMarineEquivalent.feelNoPain > 0){
                            Column(modifier = Modifier.fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally){
                                Text(text = "Feel no Pain", fontSize = 12.sp)
                                Text("${spaceMarineEquivalent.feelNoPain}+", fontSize = 12.sp)
                            }
                        }
                    }
                    Text("Keywords: $keywordsString")
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

    if(weapon.abilities.count() == 1){
        abilitiesString = weapon.abilities[0].title
    }
    else{
        for (ability in weapon.abilities){
            abilitiesString += ability.title + ",\n"
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
                Text(text = "${weapon.attack.toHit}+", fontSize = fontSize)
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

@Preview(showBackground = true)
@Composable
fun SimulatorPreview(){
    SimulatorScreen()
}