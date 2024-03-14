package com.ryanpatrick.mathhammer40k

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val title: String, val route: String, val icon: ImageVector) {
    object SimScreen: Screens(title = "Simulator", route = "sim_screen", icon = Icons.Default.Settings)
    object ManageProfileScreen: Screens(title = "Manage Profile", route = "manage_screen", Icons.Default.Person)
    object ProfilesListScreen: Screens(title = "Profiles List", route = "profiles_list", Icons.Default.List)
}

val appScreens = listOf(Screens.SimScreen, Screens.ProfilesListScreen, Screens.ManageProfileScreen)