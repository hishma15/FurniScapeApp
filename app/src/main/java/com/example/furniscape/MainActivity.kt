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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.furniscape.ui.screen.HomeScreen
import com.example.furniscape.ui.screen.LoginScreen
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
    val navController = rememberNavController()

    Scaffold (
        topBar = {
//            Add TopBar only for main screens
            val showTopBar = when (navController.currentBackStackEntry?.destination?.route) {
                FurniScape.Login.name, FurniScape.Welcome.name, FurniScape.Register.name -> false
                else ->true
            }
            if (showTopBar){
//                TopAppBar()
            }
        }

    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = FurniScape.Welcome.name,
//            modifier = Modifier.padding(innerPadding)
//            modifier = Modifier.fillMaxSize()
        ) {
            composable(FurniScape.Welcome.name){
                WelcomeScreen(
                    onGetStartedClicked = {
                        navController.navigate(FurniScape.Login.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(FurniScape.Login.name) {
                LoginScreen(
                    onLoginClicked = { },
                    onRegisterClicked = {
                        navController.navigate(FurniScape.Register.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(FurniScape.Register.name){
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

            composable(FurniScape.Home.name) {
                //  For main screens, apply innerPadding
                HomeScreen(
                    modifier = Modifier.padding(innerPadding)
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
    Home
}


