package com.lazysloth.pocketlog.ui.screen.authentication

import LoginScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.lazysloth.pocketlog.ui.navigationitem.AuthenticationNavigation
import com.lazysloth.pocketlog.ui.screen.home.HomeScreen

@Composable
fun MainScreenNav(navController: NavHostController = rememberNavController(),modifier: Modifier) {

    NavHost(
        startDestination = AuthenticationNavigation.LOGIN.name,
        navController = navController
    ) {
        composable(route = AuthenticationNavigation.LOGIN.name) {
            LoginScreen(
                onForgetClick = { navController.navigate(AuthenticationNavigation.FORGET.name) },
                onNewUserclick = {
                    navController.navigate(
                        AuthenticationNavigation.SIGNUP.name
                    )
                },
                onClickGo = { navController.navigate(AuthenticationNavigation.HOME_SCREEN.name) })
        }
        composable(route = AuthenticationNavigation.SIGNUP.name) {
            SignupScreen(
                onClickGo = { navController.navigate(AuthenticationNavigation.HOME_SCREEN.name) },
                onClickAlreadyAUser = { navController.navigate(AuthenticationNavigation.LOGIN.name) },
            )
        }
        composable(route = AuthenticationNavigation.FORGET.name) {
            ForgetPasswordScreen(
                onClickNext = {navController.navigate(AuthenticationNavigation.NEW_PASSWORD.name)}
            )
        }
        composable(route = AuthenticationNavigation.NEW_PASSWORD.name) {
            CreateNewPasswordScreen(
                onClickNext = {navController.navigate(AuthenticationNavigation.LOGIN.name)}
            )
        }
        composable(route = AuthenticationNavigation.HOME_SCREEN.name) {
            HomeScreen()
        }
    }

}