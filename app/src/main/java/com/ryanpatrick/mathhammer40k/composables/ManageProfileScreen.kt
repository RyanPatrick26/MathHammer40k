package com.ryanpatrick.mathhammer40k.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ryanpatrick.mathhammer40k.data.Keywords
import com.ryanpatrick.mathhammer40k.data.Profile
import com.ryanpatrick.mathhammer40k.data.Roles
import com.ryanpatrick.mathhammer40k.data.Weapon
import com.ryanpatrick.mathhammer40k.viewmodel.ProfileViewModel

@Composable
fun ManageProfileScreen(id: Long, profileViewModel: ProfileViewModel){
    val profileName = remember { mutableStateOf("") }
    val modelCount = remember { mutableStateOf("") }
    val toughness = remember{ mutableStateOf("") }
    val wounds = remember { mutableStateOf("") }
    val save = remember { mutableStateOf("") }
    val invulnerable = remember { mutableStateOf("") }
    val feelNoPain = remember { mutableStateOf("") }
    val keywords = remember{mutableStateOf(listOf<Keywords>())}

    val expanded = remember{mutableStateOf(false)}

    if(id != 0L){
        val profile = profileViewModel.getProfileById(id)
            .collectAsState(initial = Profile(0L, "", listOf(), 0, 0, 0, 0, keywords = listOf(), roles = listOf()))
        profileName.value = profile.value.profileName
        modelCount.value = "${profile.value.modelCount}"
        toughness.value = "${profile.value.toughness}"
        wounds.value = "${profile.value.wounds}"
        save.value = "${profile.value.save}"
        invulnerable.value = "${profile.value.invulnerable}"
        feelNoPain.value = "${profile.value.feelNoPain}"
    }
    else{
        profileName.value = ""
        modelCount.value = ""
        toughness.value = ""
        wounds.value = ""
        save.value = ""
        invulnerable.value = ""
        feelNoPain.value = ""
    }

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)){
        Row(modifier = Modifier.fillMaxWidth()){
            OutlinedTextField(value = profileName.value, onValueChange = {profileName.value = it},
                label = {Text(text = "Name*", fontSize = 12.sp)},
                modifier = Modifier.weight(2f).padding(end = 4.dp))
            OutlinedTextField(value = modelCount.value, onValueChange = {modelCount.value = it},
                label = {Text(text = "Model Count*", fontSize = 12.sp)},
                modifier = Modifier.weight(1f).padding(start = 4.dp))
        }
        Row(modifier = Modifier.fillMaxWidth()){
            OutlinedTextField(value = toughness.value, onValueChange = {toughness.value = it},
                label = {Text(text = "Toughness*", fontSize = 12.sp)},
                modifier = Modifier.weight(1f).padding(end = 4.dp))
            OutlinedTextField(value = wounds.value, onValueChange = {wounds.value = it},
                label = {Text(text = "Wounds*", fontSize = 12.sp)},
                modifier = Modifier.weight(1f).padding(start = 4.dp, end = 4.dp))
            OutlinedTextField(value = save.value, onValueChange = {save.value = it},
                label = {Text(text = "Save*", fontSize = 12.sp)},
                modifier = Modifier.weight(1f).padding(start = 4.dp, end = 4.dp))
        }
        Row(modifier = Modifier.fillMaxWidth()){
            OutlinedTextField(value = invulnerable.value, onValueChange = {invulnerable.value = it},
                label = {Text(text = "Invulnerable", fontSize = 12.sp)},
                modifier = Modifier.weight(1f).padding(end = 4.dp))
            OutlinedTextField(value = feelNoPain.value, onValueChange = {feelNoPain.value = it},
                label = {Text(text = "Feel No Pain", fontSize = 12.sp)},
                modifier = Modifier.weight(1f).padding(start = 4.dp))
        }
        Text(text = "Keywords: ")
        DropdownMenu(expanded = expanded.value, onDismissRequest = {expanded.value = false}){

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManageProfilePreview(){
    ManageProfileScreen(0, viewModel())
}