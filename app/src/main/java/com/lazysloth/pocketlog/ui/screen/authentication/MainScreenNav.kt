package com.lazysloth.pocketlog.ui.screen.authentication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.lazysloth.pocketlog.database.data.PasswordManager
import com.lazysloth.pocketlog.ui.navigationitem.AuthenticationNavigation
import com.lazysloth.pocketlog.ui.screen.authentication.viewmodel.AuthViewModel
import com.lazysloth.pocketlog.ui.screen.authentication.viewmodel.AuthViewModelFactory
import com.lazysloth.pocketlog.ui.screen.home.HomeScreen

@Composable
fun MainScreenNav(navController: NavHostController = rememberNavController(), modifier: Modifier) {
    val context = LocalContext.current
    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(PasswordManager(context))
    )

    val passwordExists by viewModel.passwordExists.collectAsState()
    NavHost(
        startDestination = AuthenticationNavigation.LOGIN.name,
        navController = navController
    ) {
        composable(route = AuthenticationNavigation.LOGIN.name) {
            LoginScreen(
                onForgetClick = { navController.navigate(AuthenticationNavigation.FORGET.name) },
                onNewUserClick = {
                    navController.navigate(
                        AuthenticationNavigation.SIGNUP.name
                    )

                },
                onClickGo = {
                    navController.navigate(AuthenticationNavigation.HOME_SCREEN.name) {
                        popUpTo(
                            navController.graph.findStartDestination().id
                        ) { inclusive = true }
                    }
                })
        }

        composable(route = AuthenticationNavigation.SIGNUP.name) {
            SignupScreen(
                onClickGo = { navController.navigate(AuthenticationNavigation.HOME_SCREEN.name) },
                onClickAlreadyAUser = { navController.navigate(AuthenticationNavigation.LOGIN.name) },
            )
        }
        composable(route = AuthenticationNavigation.FORGET.name) {
            ForgetPasswordScreen(
                onClickNext = { navController.navigate(AuthenticationNavigation.NEW_PASSWORD.name) }
            )
        }
        composable(route = AuthenticationNavigation.NEW_PASSWORD.name) {
            CreateNewPasswordScreen(
                onClickNext = {
                    navController.navigate(AuthenticationNavigation.LOGIN.name) {
                        popUpTo(
                            navController.graph.findStartDestination().id
                        ) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = AuthenticationNavigation.HOME_SCREEN.name) {
            HomeScreen()
        }
    }

}