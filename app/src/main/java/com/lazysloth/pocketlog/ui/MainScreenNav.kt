package com.lazysloth.pocketlog.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lazysloth.pocketlog.ui.navigationitem.AuthenticationNavigation
import com.lazysloth.pocketlog.ui.screen.authentication.CreateNewPasswordScreen
import com.lazysloth.pocketlog.ui.screen.authentication.ForgetPasswordScreen
import com.lazysloth.pocketlog.ui.screen.authentication.LoginScreen
import com.lazysloth.pocketlog.ui.screen.authentication.SignupScreen
import com.lazysloth.pocketlog.ui.screen.home.HomeScreen
import com.lazysloth.pocketlog.ui.screen.home.TransactionDetailsScreen
import com.lazysloth.pocketlog.ui.screen.other.AddTransactionScreen
import com.lazysloth.pocketlog.ui.theme.PocketLogTheme

@Composable
fun MainScreenNav(navController: NavHostController = rememberNavController(), modifier: Modifier) {
    val context = LocalContext.current

    NavHost(
        startDestination = AuthenticationNavigation.LOGIN.name,
        navController = navController
    ) {
        composable(route = "login") {
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
                onClickGo = { navController.navigate(AuthenticationNavigation.LOGIN.name) },
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
            HomeScreen(
                onClickAdd = {
                    navController.navigate("addTransaction")
                },
                onClickAi = {},
                onClickTransactionDetails = {
                    navController.navigate("transactionDetails")
                }
            )
        }
        composable("addTransaction") {
            AddTransactionScreen(
                popBackStack = {navController.popBackStack()}
            )
        }
        composable("transactionDetails") {
            TransactionDetailsScreen(onBack = { navController.navigate("Home_screen") })
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    PocketLogTheme {
        val navController: NavHostController =rememberNavController()
        MainScreenNav(navController, modifier = Modifier)
    }
}