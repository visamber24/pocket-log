package com.lazysloth.pocketlog.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.lazysloth.pocketlog.ui.screen.contentscreen.AddAccountScreen
import com.lazysloth.pocketlog.ui.screen.contentscreen.TransactionDetailsScreen
import com.lazysloth.pocketlog.ui.screen.contentscreen.TransactionEditScreen
import com.lazysloth.pocketlog.ui.screen.home.HomeScreen
import com.lazysloth.pocketlog.ui.screen.contentscreen.AddTransactionScreen
import com.lazysloth.pocketlog.ui.screen.contentscreen.EditAccountScreen
import com.lazysloth.pocketlog.ui.screen.other.SettingsScreen

@Composable
fun MainScreenNav(
    session: UserPersists,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier
) {
    var startRoute by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(Unit) {
        val userExists = session.checkUser()
        startRoute = if (session.currentId != -1 && userExists) {
            AuthenticationNavigation.HOME_SCREEN.name
        } else {
            "login"
        }
    }
    if (startRoute != null) {
        NavHost(
            startDestination = startRoute!!,
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
                    onClickTransactionDetails = {
                        navController.navigate("transactionDetails")
                    },
                    onClickEdit = {
                        navController.navigate("transaction_edit_screen")
                    },
                    onClickSetting = {
                        navController.navigate("setting_screen")
                    },
                    onClickAi = {},
                    onClickAddAccount = {
                        navController.navigate("add_account")
                    },
                    onClickEditAccount = {
                        navController.navigate("edit_account")
                    }

                )
            }

            composable("addTransaction") {
                AddTransactionScreen(
                    popBackStack = { navController.popBackStack() }
                )
            }
            composable("transactionDetails") {
                TransactionDetailsScreen(onBack = { navController.popBackStack()})
            }
            composable("transaction_edit_screen") {
                TransactionEditScreen(
                    onSavePopBackStack = {
                        navController.popBackStack()
                    }
                )
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
            composable(route = "add_account") {
                AddAccountScreen(
                    popBackStack = { navController.popBackStack() }
                )
            }
            composable(route="edit_account"){
                EditAccountScreen(
                    popBackStack = {navController.popBackStack()}
                )
            }
        }
    } else {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

}

//@Preview(showSystemUi = true)
//@Composable
//fun MainScreenPreview() {
//    PocketLogTheme {
//        val navController: NavHostController = rememberNavController()
//        val context = LocalContext.current
//        MainScreenNav(session = UserPersists(context),navController, modifier = Modifier)
//    }
//}