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
import com.lazysloth.pocketlog.di.UserPersists
import com.lazysloth.pocketlog.ui.navigationitem.AuthenticationNavigation
import com.lazysloth.pocketlog.ui.screen.authentication.CreateNewPasswordScreen
import com.lazysloth.pocketlog.ui.screen.authentication.ForgetPasswordScreen
import com.lazysloth.pocketlog.ui.screen.authentication.LoginScreen
import com.lazysloth.pocketlog.ui.screen.authentication.SignupScreen
import com.lazysloth.pocketlog.ui.screen.contentscreen.TransactionDetailsScreen
import com.lazysloth.pocketlog.ui.screen.contentscreen.TransactionEditScreen
import com.lazysloth.pocketlog.ui.screen.home.HomeScreen
import com.lazysloth.pocketlog.ui.screen.other.AddTransactionScreen
import com.lazysloth.pocketlog.ui.screen.other.SettingsScreen
import com.lazysloth.pocketlog.ui.theme.PocketLogTheme

@Composable
fun MainScreenNav(
    session: UserPersists,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier
) {
    val context = LocalContext.current

    NavHost(
        startDestination = if (session.currentId != -1) {
            AuthenticationNavigation.HOME_SCREEN.name
        } else {
            "login"
        },
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
                onClickGo = {
                    navController.navigate(AuthenticationNavigation.LOGIN.name) {
                        popUpTo(
                            navController.graph.findStartDestination().id
                        ) { inclusive = true }
                    }
                },
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
                },
                onClickSetting = {
                    navController.navigate("setting_screen")
                },
                onClickEdit = {
                    navController.navigate("transaction_edit_screen")
                }
            )
        }
        composable("transaction_edit_screen") {
            TransactionEditScreen(
                onSavePopBackStack = {
                    navController.popBackStack()
                }
            )
        }
        composable("addTransaction") {
            AddTransactionScreen(
                popBackStack = {navController.popBackStack()}
            )
        }
        composable("transactionDetails") {
            TransactionDetailsScreen(onBack = { navController.popBackStack() })
        }
        composable(route = "setting_screen") {
            SettingsScreen(onClickLogout = {
                session.logout()
                navController.navigate("login") {
                    popUpTo(
                        navController.graph.findStartDestination().id
                    ) {
                        inclusive = true
                    }
                }
            })
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    PocketLogTheme {
        val navController: NavHostController = rememberNavController()
        val context = LocalContext.current
        MainScreenNav(session = UserPersists(context),navController, modifier = Modifier)
    }
}