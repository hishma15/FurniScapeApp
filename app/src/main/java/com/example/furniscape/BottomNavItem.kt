package com.example.furniscape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label:String) {
    object Home :BottomNavItem("Home", Icons.Default.Home, "Home")
    object Explore :BottomNavItem("Explore", Icons.Default.Explore, "Explore")
    object Cart :BottomNavItem("Cart", Icons.Default.ShoppingCart, "Cart")
    object Profile :BottomNavItem("Profile", Icons.Default.Person, "Profile")
}