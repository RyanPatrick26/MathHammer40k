package com.ryanpatrick.mathhammer40k.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import com.ryanpatrick.mathhammer40k.data.Ability

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
                    WeaponsListItem(abilitiesList = listOf(Ability("Abilities", "")))
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
fun WeaponsListItem(name: String = "Weapon", count: Int = -1, attacks: String = "Attacks", strength: Int = -1,
                    ap: Int = -1, damage: String = "Damage", abilitiesList: List<Ability>){
    var abilitiesString = ""

    if(abilitiesList.count() == 1){
        abilitiesString = abilitiesList[0].name
    }
    else{
        for (ability in abilitiesList){
            abilitiesString += ability.name + ",\n"
        }
    }

    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly){
        val fontSize = 12.sp
        Text(name, fontSize = fontSize)
        Text(text = if(count < 0) "Count" else "$count", fontSize = fontSize)
        Text(attacks, fontSize = fontSize)
        Text(text = if(strength < 0) "Strength" else "$strength", fontSize = fontSize)
        Text(text = if(ap < 0) "AP" else "$ap", fontSize = fontSize)
        Text(damage, fontSize = fontSize)
        Text(abilitiesString, fontSize = fontSize)
    }
}

@Preview(showBackground = true)
@Composable
fun SimulatorPreview(){
    SimulatorScreen()
}