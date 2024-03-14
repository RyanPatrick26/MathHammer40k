package com.ryanpatrick.mathhammer40k.composables

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ryanpatrick.mathhammer40k.appScreens
import com.ryanpatrick.mathhammer40k.viewmodel.NavViewModel

@Composable
fun MainScaffold(){
    val navViewModel: NavViewModel = viewModel()
    val currentScreen = remember{navViewModel.currentScreen.value}
    val title = remember{mutableStateOf(currentScreen.title)}
    val controller = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {TopBar(title.value)},
        bottomBar = {BottomBar(controller, currentRoute, navViewModel, title)}){
        Column(modifier = Modifier.padding(it)){
            NavGraph(controller)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String){
    Surface(shadowElevation = 3.dp){
        TopAppBar(title = {Text(text = title, style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.onPrimaryContainer)},
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer))
    }
}

@Composable
fun BottomBar(controller: NavHostController, route: String?, navViewModel: NavViewModel, title: MutableState<String>){
   NavigationBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
        appScreens.forEach{
            item ->
            NavigationBarItem(selected = route == item.route,
                onClick = {
                    title.value = item.title
                    controller.navigate(item.route)
                },
                icon = {Icon(imageVector = item.icon, contentDescription = item.title)},
                label = {Text(text = item.title)})
        }
   }
}