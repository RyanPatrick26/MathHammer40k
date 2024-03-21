package com.ryanpatrick.mathhammer40k.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ryanpatrick.mathhammer40k.Screens
import com.ryanpatrick.mathhammer40k.data.Profile
import com.ryanpatrick.mathhammer40k.data.title
import com.ryanpatrick.mathhammer40k.viewmodel.ProfileViewModel

@Composable
fun ProfilesListScreen(profileViewModel: ProfileViewModel, controller: NavHostController){
    val profilesList = profileViewModel.getAllProfiles.collectAsState(initial = listOf())
    //val profilesList = listOf(spaceMarineEquivalent)
    Column(modifier = Modifier.fillMaxSize()){
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            Text("Name", modifier = Modifier.weight(0.75f))
            Text("Roles", modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.weight(0.75f))
        }
        LazyColumn {
            items(profilesList.value){profile ->
                ProfileItem(profile, controller)
            }
        }
    }
}

@Composable
fun ProfileItem(profile: Profile, controller: NavHostController){
    var rolesString = ""
    if(profile.roles.count() == 1){
        rolesString = profile.roles[0].title
    }
    else{
        for(role in profile.roles){
            if(profile.roles.last() == role){
                rolesString += role.title
            }
            else{
                rolesString += "${role.title}, "
            }
        }
    }
    Card(modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 8.dp, end = 8.dp),
        elevation = CardDefaults.cardElevation(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){
            Text(text = profile.profileName, fontSize = 12.sp, modifier = Modifier.weight(0.75f))
            Text(text = rolesString, fontSize = 12.sp, modifier = Modifier.weight(1f))
            Row(modifier = Modifier.weight(0.75f), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End){
                IconButton(onClick = {controller.navigate(Screens.ManageProfileScreen.route + "/${profile.id}")}){
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = {}){
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }



}