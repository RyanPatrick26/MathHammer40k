package com.ryanpatrick.mathhammer40k.composables

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ryanpatrick.mathhammer40k.Screens
import com.ryanpatrick.mathhammer40k.viewmodel.ProfileViewModel

@Composable
fun NavGraph(profileViewModel: ProfileViewModel = viewModel()){
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.ManageProfilesScreen.route){
        composable(Screens.SimScreen.route){
            SimulatorScreen()
        }
        composable(Screens.ManageProfilesScreen.route){
            ManageProfilesScreen()
        }
        composable(Screens.CreateProfileScreen.route){
            CreateProfileScreen()
        }
    }
}