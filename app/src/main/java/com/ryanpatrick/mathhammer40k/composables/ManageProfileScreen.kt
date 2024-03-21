package com.ryanpatrick.mathhammer40k.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ryanpatrick.mathhammer40k.data.Keywords
import com.ryanpatrick.mathhammer40k.data.Profile
import com.ryanpatrick.mathhammer40k.data.Weapon
import com.ryanpatrick.mathhammer40k.data.title
import com.ryanpatrick.mathhammer40k.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageProfileScreen(id: Long, profileViewModel: ProfileViewModel) {
    val profileName = remember { mutableStateOf("") }
    val modelCount = remember { mutableStateOf("") }
    val toughness = remember { mutableStateOf("") }
    val wounds = remember { mutableStateOf("") }
    val save = remember { mutableStateOf("") }
    val invulnerable = remember { mutableStateOf("") }
    val feelNoPain = remember { mutableStateOf("") }
    val weapons = remember { mutableStateListOf<Weapon>() }
    val keywords = remember { mutableStateListOf<Keywords>() }

    val expanded = remember { mutableStateOf(false) }
    val keywordsString = remember { mutableStateOf("") }

    if (id != 0L) {
        val profile = profileViewModel.getProfileById(id)
            .collectAsState(initial = Profile(0L, "", listOf(), 0,
                    0, 0, 0, keywords = listOf(), roles = listOf()))
        profileName.value = profile.value.profileName
        modelCount.value = "${profile.value.modelCount}"
        toughness.value = "${profile.value.toughness}"
        wounds.value = "${profile.value.wounds}"
        save.value = "${profile.value.save}"
        invulnerable.value = "${profile.value.invulnerable}"
        feelNoPain.value = "${profile.value.feelNoPain}"

        if(weapons.isEmpty()){
            weapons.addAll(profile.value.weapons)
        }
        if(keywords.isEmpty()){
            keywords.addAll(profile.value.keywords)
        }
    }
    else {
        profileName.value = ""
        modelCount.value = ""
        toughness.value = ""
        wounds.value = ""
        save.value = ""
        invulnerable.value = ""
        feelNoPain.value = ""
    }

    keywordsString.value = listToString(keywords.toList().map { it.title })

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = profileName.value, onValueChange = { profileName.value = it },
                label = { Text(text = "Name*", fontSize = 12.sp) },
                modifier = Modifier.weight(2f).padding(end = 4.dp)
            )
            OutlinedTextField(
                value = modelCount.value, onValueChange = { modelCount.value = it },
                label = { Text(text = "Model Count*", fontSize = 12.sp) },
                modifier = Modifier.weight(1f).padding(start = 4.dp)
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = toughness.value, onValueChange = { toughness.value = it },
                label = { Text(text = "Toughness*", fontSize = 12.sp) },
                modifier = Modifier.weight(1f).padding(end = 4.dp)
            )
            OutlinedTextField(
                value = wounds.value, onValueChange = { wounds.value = it },
                label = { Text(text = "Wounds*", fontSize = 12.sp) },
                modifier = Modifier.weight(1f).padding(start = 4.dp, end = 4.dp)
            )
            OutlinedTextField(
                value = save.value, onValueChange = { save.value = it },
                label = { Text(text = "Save*", fontSize = 12.sp) },
                modifier = Modifier.weight(1f).padding(start = 4.dp, end = 4.dp)
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = invulnerable.value, onValueChange = { invulnerable.value = it },
                label = { Text(text = "Invulnerable", fontSize = 12.sp) },
                modifier = Modifier.weight(1f).padding(end = 4.dp)
            )
            OutlinedTextField(
                value = feelNoPain.value, onValueChange = { feelNoPain.value = it },
                label = { Text(text = "Feel No Pain", fontSize = 12.sp) },
                modifier = Modifier.weight(1f).padding(start = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Keywords: ${keywordsString.value}")
        ExposedDropdownMenuBox(expanded = expanded.value, onExpandedChange = { expanded.value = !expanded.value }) {
            TextField(value = "Keywords", onValueChange = {}, readOnly = true, modifier = Modifier.menuAnchor(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) })
            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }) {
                for (keyword in Keywords.entries) {
                    DropdownMenuItem(text = { Text(keyword.title) },
                        onClick = {
                            if (keywords.contains(keyword)) {
                                keywords.remove(keyword)
                            } else {
                                keywords.add(keyword)
                            }
                            keywordsString.value = listToString(keywords.toList().map { it.title })
                        })
                }
            }
        }
        Divider(modifier = Modifier.fillMaxWidth().padding(4.dp), thickness = 2.dp)
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            Text(text = "Weapons")
            Button(onClick = {}, contentPadding = PaddingValues(4.dp)){
                Text("Add Weapon")
            }
        }
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(weapons){weapon->
                ProfileWeaponsListItem(weapon)
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
            Button(onClick = {}){
                Text("Save Profile")
            }
        }
    }
}

fun listToString(list: List<String>): String{
    var string = ""

    if(list.isNotEmpty()){
        if (list.count() == 1) {
            string = list[0]
        } else {
            list.forEach {
                if (list.last() != it) {
                    string += "${it}, "
                } else {
                    string += it
                }
            }
        }
    }

    return string
}

@Composable
fun ProfileWeaponsListItem(weapon: Weapon){
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

    Card(modifier = Modifier.padding(vertical = 4.dp)){
        Row(modifier = Modifier.padding(8.dp)){
            Column(modifier = Modifier.weight(1f).padding(vertical = 8.dp)){
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
            Column{
                IconButton(onClick = {}){
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = {}){
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
                }

            }
        }
    }
}