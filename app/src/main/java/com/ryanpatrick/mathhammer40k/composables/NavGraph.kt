package com.ryanpatrick.mathhammer40k.composables

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ryanpatrick.mathhammer40k.Screens
import com.ryanpatrick.mathhammer40k.viewmodel.ProfileViewModel

@Composable
fun NavGraph(navController: NavController, profileViewModel: ProfileViewModel = viewModel(), title: MutableState<String>){
    NavHost(navController = navController as NavHostController, startDestination = Screens.SimScreen.route){
        composable(Screens.SimScreen.route){
            title.value = Screens.SimScreen.title
            SimulatorScreen()
        }
        composable(Screens.ProfilesListScreen.route){
            title.value = Screens.ProfilesListScreen.title
            ProfilesListScreen(profileViewModel, navController)
        }
        composable(Screens.ManageProfileScreen.route + "/{id}",
            arguments = listOf(navArgument("id"){
                type = NavType.LongType
                defaultValue = 0L
                nullable = false
            })){entry->
            val id = if(entry.arguments != null) entry.arguments!!.getLong("id") else 0L
            title.value = Screens.ManageProfileScreen.title
            ManageProfileScreen(id, profileViewModel)
        }
    }
}