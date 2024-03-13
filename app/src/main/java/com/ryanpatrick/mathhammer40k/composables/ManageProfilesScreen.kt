package com.ryanpatrick.mathhammer40k.composables

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ryanpatrick.mathhammer40k.data.Profile
import com.ryanpatrick.mathhammer40k.data.spaceMarineEquivalent
import com.ryanpatrick.mathhammer40k.viewmodel.ProfileViewModel
@Composable
fun ManageProfilesScreen(/*profileViewModel: ProfileViewModel*/){
    //val profilesList = profileViewModel.getAllProfiles.collectAsState(initial = listOf())
    val profilesList = listOf(spaceMarineEquivalent)
    Column(modifier = Modifier.fillMaxSize()){
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            Text("Name", modifier = Modifier.weight(0.75f))
            Text("Roles", modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.weight(0.75f))
        }
        LazyColumn {
            items(profilesList){profile ->
                ProfileItem(profile)
            }
        }
    }

}

@Composable
fun ProfileItem(profile: Profile){
    var rolesString = ""
    if(profile.roles.count() == 1){
        rolesString = profile.roles[0].name
    }
    else{
        for(role in profile.roles){
            if(profile.roles.last() == role){
                rolesString += role.name
            }
            else{
                rolesString += "${role.name}, "
            }
        }
    }
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically){
        Text(text = profile.profileName, fontSize = 12.sp, modifier = Modifier.weight(0.75f))
        Text(text = rolesString, fontSize = 12.sp, modifier = Modifier.weight(1f))
        Row(modifier = Modifier.weight(0.75f), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End){
            IconButton(onClick = {}){
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = {}){
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManageProfilesPreview(){
    ManageProfilesScreen()
}