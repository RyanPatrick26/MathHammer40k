package com.ryanpatrick.mathhammer40k.composables

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ryanpatrick.mathhammer40k.Screens

@Composable
fun NavGraph(navController: NavController){
    NavHost(navController = navController as NavHostController, startDestination = Screens.SimScreen.route){
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