package com.example.furniscape

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.furniscape.ui.component.BottomNavigationBar
import com.example.furniscape.ui.component.FurniScapeAppBar
import com.example.furniscape.ui.screen.ExploreScreen
import com.example.furniscape.ui.screen.HomeScreen
import com.example.furniscape.ui.screen.LoginScreen
import com.example.furniscape.ui.screen.ProductDetailScreen
import com.example.furniscape.ui.screen.RegisterScreen
import com.example.furniscape.ui.screen.WelcomeScreen
import com.example.furniscape.ui.theme.FurniScapeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FurniScapeTheme {
                FurniScapeApp()
            }
        }
    }
}

//@Composable
//fun FurniScapeApp(){
//    WelcomeScreen(
//        onGetStartedClicked = { }
//    )
//}

@Composable
fun FurniScapeApp(){
//    WelcomeScreen(
//        onGetStartedClicked = { }
//    )

    // Create and remember a NavController instance to handle navigation between composables
    val navController = rememberNavController()

    //Observe the current back stack entry as a state to reactively respond to navigation changes
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    //Get Current route (screen identifier) from the back stack entry or null if none
    val currentRoute = navBackStackEntry?.destination?.route

//    Screens that should NOT show the TopAppBar
    val noAppBarRoutes = listOf(
        FurniScape.Login.name,
        FurniScape.Welcome.name,
        FurniScape.Register.name,
        "productDetails/{productId}" // To remove FurniScapeAppBar. so the OtherAppBar will be visible
//        "productDetails"   *************
    )

//    To show appbar in screens
    Scaffold (
        topBar = {
            if (currentRoute !in noAppBarRoutes) {
                FurniScapeAppBar()
            }
//            **************************
//            if (currentRoute != null &&
//                noAppBarRoutes.none { currentRoute.startsWith(it.substringBefore("/{")) }) {
//                FurniScapeAppBar()
//            }
        },
        bottomBar = {
            if (currentRoute in bottomNavItems.map {  it.route }) {
                BottomNavigationBar(navController, bottomNavItems)
            }
        }


    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = FurniScape.Welcome.name,
//            modifier = Modifier.padding(innerPadding)
//            modifier = Modifier.fillMaxSize()
        ) {
            composable(route = FurniScape.Welcome.name){
                WelcomeScreen(
                    onGetStartedClicked = {
                        navController.navigate(FurniScape.Login.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = FurniScape.Login.name) {
                LoginScreen(
                    onLoginClicked = {
                        navController.navigate(FurniScape.Home.name)
                    },
                    onRegisterClicked = {
                        navController.navigate(FurniScape.Register.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = FurniScape.Register.name){
                RegisterScreen(
                    onRegisterClicked = {
//                        navigate back to login screen or home
                        navController.popBackStack()
                    },
                    onLoginClicked = {
                        navController.navigate(FurniScape.Login.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(route = FurniScape.Home.name) {
                //  For main screens, apply innerPadding
                HomeScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }

            composable(route = FurniScape.Explore.name) {
                //  For main screens, apply innerPadding
                ExploreScreen(
                    navController = navController, // Navigate tot he product details screen
                    modifier = Modifier.padding(innerPadding)
                )
            }

            composable(
                route = "productDetails/{productId}",
                arguments = listOf(navArgument("productId") {type = NavType.IntType})
            ){
                BackStackEntry ->
                val productId = BackStackEntry.arguments?.getInt("productId")?: -1
                ProductDetailScreen(
                    productId = productId,
                    onBackClick = {navController.popBackStack()}
                )
            }

        }
    }
}

@Preview
@Composable
fun FurniScapeAppPreviewLight(){
    FurniScapeTheme() {
        FurniScapeApp()
    }
}

@Preview
@Composable
fun FurniScapeAppPreviewDark(){
    FurniScapeTheme (darkTheme = true){
        FurniScapeApp()
    }
}

enum class FurniScape {
    Welcome,
    Login,
    Register,
    Home,
    Explore
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Explore,
    BottomNavItem.Cart,
    BottomNavItem.User
)

