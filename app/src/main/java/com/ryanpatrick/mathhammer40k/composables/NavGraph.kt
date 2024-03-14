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
fun NavGraph(navController: NavController, profileViewModel: ProfileViewModel = viewModel()){
    NavHost(navController = navController as NavHostController, startDestination = Screens.SimScreen.route){
        composable(Screens.SimScreen.route){
            SimulatorScreen()
        }
        composable(Screens.ProfilesListScreen.route){
            ProfilesListScreen()
        }
        composable(Screens.ManageProfileScreen.route){
            ManageProfileScreen()
        }
    }
}