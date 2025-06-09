@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.example.furniscape

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.furniscape.ui.NavigationType
import com.example.furniscape.ui.component.BottomNavigationBar
import com.example.furniscape.ui.component.FurniScapeAppBar
import com.example.furniscape.ui.screen.CartScreen
import com.example.furniscape.ui.screen.ExploreScreen
import com.example.furniscape.ui.screen.HomeScreen
import com.example.furniscape.ui.screen.LoginScreen
import com.example.furniscape.ui.screen.ProductDetailScreen
import com.example.furniscape.ui.screen.ProfileScreen
import com.example.furniscape.ui.screen.RegisterScreen
import com.example.furniscape.ui.screen.WelcomeScreen
import com.example.furniscape.ui.theme.FurniScapeTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FurniScapeTheme {
                val windowSize = calculateWindowSizeClass(this)
                FurniScapeApp(windowSize = windowSize.widthSizeClass)
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

//@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FurniScapeApp(windowSize: WindowWidthSizeClass){
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
        "productDetails/{productId}", // To remove FurniScapeAppBar. so the OtherAppBar will be visible
//        "productDetails"   *************
        "explore/{categoryId}"
    )

    val navigationType = when (windowSize) {
        WindowWidthSizeClass.Compact -> NavigationType.BOTTOM_NAVIGATION
        WindowWidthSizeClass.Medium -> NavigationType.NAVIGATION_RAIL
        WindowWidthSizeClass.Expanded -> NavigationType.NAVIGATION_RAIL
        else -> NavigationType.PERMANENT_NAVIGATION_DRAWER


    }

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
            if (currentRoute in bottomNavItems.map {  it.route } && navigationType == NavigationType.BOTTOM_NAVIGATION ) {
                BottomNavigationBar(navController, bottomNavItems)
            }
        }

//        bottomBar = {
//            if (
//                (currentRoute?.startsWith(FurniScape.Explore.name.lowercase()) == true ||
//                        currentRoute in bottomNavItems.map { it.route }) &&
//                navigationType == NavigationType.BOTTOM_NAVIGATION
//            ) {
//                BottomNavigationBar(navController, bottomNavItems)
//            }
//        }


    ){ innerPadding ->

        Row {


            // show NavigationRail if in medium/expanded size
            // Adding top padding too Navigation Rail
            if (
                navigationType == NavigationType.NAVIGATION_RAIL &&
                currentRoute in bottomNavItems.map { it.route }
            ) {
                FurniScapeNavRail(
                    navController = navController,
                    items = bottomNavItems,
                    modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
                )
            }

//            // show NavigationRail if in medium/expanded size
//            if (navigationType == NavigationType.NAVIGATION_RAIL && currentRoute in bottomNavItems.map {it.route}) {
//                FurniScapeNavRail(navController, bottomNavItems)
//            }

            val bottomNavRoutes = listOf(
                FurniScape.Home.name,
                FurniScape.Explore.name,
                FurniScape.Cart.name,
                FurniScape.Profile.name
            )

            //main navigation content
//            NavHost(
            AnimatedNavHost(

                navController = navController,
                startDestination = FurniScape.Welcome.name,
//            modifier = Modifier.padding(innerPadding)
//            modifier = Modifier.fillMaxSize()

                enterTransition = {
                    if (
                        initialState.destination.route in bottomNavRoutes &&
                        targetState.destination.route in bottomNavRoutes
                    ) {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(300)
                        ) + fadeIn()
                    } else {
                        EnterTransition.None
                    }
                },

                exitTransition = {
                    if (
                        initialState.destination.route in bottomNavRoutes &&
                        targetState.destination.route in bottomNavRoutes
                    ) {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(300)
                        ) + fadeOut()
                    } else {
                        ExitTransition.None
                    }
                },

                popEnterTransition = {
                    if (
                        initialState.destination.route in bottomNavRoutes &&
                        targetState.destination.route in bottomNavRoutes
                    ) {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(300)
                        ) + fadeIn()
                    } else {
                        EnterTransition.None
                    }
                },

                popExitTransition = {
                    if (
                        initialState.destination.route in bottomNavRoutes &&
                        targetState.destination.route in bottomNavRoutes
                    ) {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(300)
                        ) + fadeOut()
                    } else {
                        ExitTransition.None
                    }
                }

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
                        modifier = Modifier.padding(innerPadding),
                        navController = navController
                    )
                }

                composable(route = FurniScape.Explore.name) {
                    //  For main screens, apply innerPadding
                    ExploreScreen(
                        navController = navController, // Navigate tot he product details screen
                        modifier = Modifier.padding(innerPadding),
                        categoryFilter = "all"  //default for bottom nav
                    )
                }

                //passing according to selected category
                composable(
                    route = "explore/{categoryId}",
                    arguments = listOf(navArgument("categoryId") { defaultValue = "all" })
                ) { backStackEntry ->
                    val categoryId = backStackEntry.arguments?.getString("categoryId") ?: "all"
                    ExploreScreen(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        categoryFilter = categoryId
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
                        onBackClick = {navController.popBackStack()},
                        navController = navController
                    )
                }

                composable(route = FurniScape.Profile.name) {
                    //  For main screens, apply innerPadding
                    ProfileScreen(
                        onLogOutClick = {
//                            Navigate to Login and clear backstack
                            navController.navigate(FurniScape.Login.name) {
                                popUpTo(0) { inclusive = true}
                            }
                        },
                        windowWidthSizeClass = windowSize, // orientation change
                        modifier = Modifier.padding(innerPadding)
                    )
                }

                composable(route = FurniScape.Cart.name) {
                    //  For main screens, apply innerPadding
                    CartScreen(
//                        navController = navController, // Navigate to the product details screen
                        modifier = Modifier.padding(innerPadding)
                    )
                }

            }

        }


    }
}

@Preview
@Composable
fun FurniScapeAppPreviewLight(){
    FurniScapeTheme() {
        FurniScapeApp(windowSize = WindowWidthSizeClass.Compact)
    }
}

@Preview
@Composable
fun FurniScapeAppPreviewDark(){
    FurniScapeTheme (darkTheme = true){
        FurniScapeApp(windowSize = WindowWidthSizeClass.Compact)
    }
}

enum class FurniScape {
    Welcome,
    Login,
    Register,
    Home,
    Explore,
    Profile,
    Cart
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Explore,
    BottomNavItem.Cart,
    BottomNavItem.Profile
)


// ORIENTATION CHANGE
@Composable
fun FurniScapeNavRail(
    navController: NavController,
    items: List<BottomNavItem>,
    modifier: Modifier = Modifier
) {
    NavigationRail(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach{ item ->
            NavigationRailItem(
                selected = currentRoute == item.route,
                onClick = { navController.navigate(item.route) },
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.label) }
            )
        }
    }
}

