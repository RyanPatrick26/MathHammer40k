package com.ryanpatrick.mathhammer40k.composables

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ryanpatrick.mathhammer40k.Screens

@Composable
fun NavGraph(){
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SimScreen.route){
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