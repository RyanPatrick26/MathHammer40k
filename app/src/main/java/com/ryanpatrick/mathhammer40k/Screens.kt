package com.ryanpatrick.mathhammer40k

sealed class Screens(val title: String, val route: String) {
    object SimScreen: Screens(title = "Simulator", route = "sim_screen")
    object ManageProfilesScreen: Screens(title = "Manage Profiles", route = "manage_screen")
    object CreateProfileScreen: Screens(title = "Create Profile", route = "create_screen")
}