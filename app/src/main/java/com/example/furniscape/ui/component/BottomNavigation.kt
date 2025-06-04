package com.example.furniscape.ui.component

//import androidx.compose.material3.Icon
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem

import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.furniscape.BottomNavItem


@Composable
fun BottomNavigationBar(navController: NavController, items: List<BottomNavItem>){
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier =  Modifier.height(72.dp)
    ){
        // Observes the current route on the back stack to know which screen is currently shown.
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        //Used to highlight the selected tab
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach{ item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route){
//                            Avoid building up a large stack
                            popUpTo(navController.graph.startDestinationId){
                                saveState = true  //Helps retain state (scroll position)
                            }
                            launchSingleTop = true  //Avoid launching multiple copies of the same screen
                            restoreState = true  //Helps retain state (scroll position)
                        }
                    }
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.label)
                },
                label = { Text(item.label) },
                selectedContentColor = MaterialTheme.colorScheme.onSurface,
                unselectedContentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

//@Composable
//fun BottomNavigationBar(
//    navController: NavController,
//    items: List<BottomNavItem>
//) {
//    NavigationBar(
//        containerColor = MaterialTheme.colorScheme.secondaryContainer,
//        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
//        tonalElevation = 3.dp
//    ) {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//
//        items.forEach { item ->
//            NavigationBarItem(
//                selected = currentRoute == item.route,
//                onClick = {
//                    if (currentRoute != item.route) {
//                        navController.navigate(item.route) {
//                            popUpTo(navController.graph.startDestinationId) {
//                                saveState = true
//                            }
//                            launchSingleTop = true
//                            restoreState = true
//                        }
//                    }
//                },
//                icon = {
//                    Icon(
//                        imageVector = item.icon,
//                        contentDescription = item.label
//                    )
//                },
//                label = {
//                    Text(item.label)
//                },
//                alwaysShowLabel = true, // You can set this to false for icon-only mode
//                colors = NavigationBarItemDefaults.colors(
//                    selectedIconColor = MaterialTheme.colorScheme.onSurface,
//                    selectedTextColor = MaterialTheme.colorScheme.onSurface,
//                    indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
//                    unselectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
//                    unselectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer
//                )
//            )
//        }
//    }
//}


