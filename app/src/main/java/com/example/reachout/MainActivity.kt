package com.example.reachout

import android.R.attr.padding
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reachout.ui.common.Navbar
import com.example.reachout.ui.screen.auth.AuthViewModel
import com.example.reachout.ui.screen.auth.signin
import com.example.reachout.ui.screen.auth.signup
import com.example.reachout.ui.screen.chat.ChatScreen
import com.example.reachout.ui.screen.home.Home
import com.example.reachout.ui.screen.profile.ProfileView
import androidx.navigation.compose.currentBackStackEntryAsState

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

                setContent {
                    val navController: NavHostController = rememberNavController()
                    val authViewModel: AuthViewModel = viewModel()

                    // Observe current route
                    val navBackStackEntry = navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry.value?.destination?.route

                    // Only show navbar on certain routes
                    val showNavbar = currentRoute != "register" && currentRoute != "login"

                    Scaffold(
                        bottomBar = {
                            if (showNavbar) {
                                Navbar(navController)
                            }
                        }
                    ) { padding ->
                        NavHost(
                            navController = navController,
                            startDestination = "register",
                            modifier = Modifier.padding(padding)
                        ) {
                            composable("register") {
                                signup(
                                    onSignInClick = { navController.navigate("login") },
                                    onregistersuccess = { navController.navigate("home") }
                                )
                            }
                            composable("login") {
                                signin(
                                    onSignUpClick = { navController.navigate("register") },
                                    onloginsuccess = { navController.navigate("home") }
                                )
                            }
                            composable("home") { Home(navController = navController) }
                            composable("profile") { ProfileView(navController = navController) }
                            composable("chat/{otherUserId}") { backStackEntry ->
                                val otherUserId = backStackEntry.arguments?.getString("otherUserId") ?: ""
                                ChatScreen(authViewModel = authViewModel, otherUserId = otherUserId)
                            }
                        }
                    }
                }

    }
}

