package com.lazysloth.pocketlog.ui

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.lazysloth.pocketlog.ui.screen.authentication.VerificationScreen
import com.lazysloth.pocketlog.ui.screen.authentication.viewmodel.AuthViewModel
import com.lazysloth.pocketlog.ui.screen.contentscreen.AddAccountScreen
import com.lazysloth.pocketlog.ui.screen.contentscreen.AddCategoryScreen
import com.lazysloth.pocketlog.ui.screen.contentscreen.AddTransactionScreen
import com.lazysloth.pocketlog.ui.screen.contentscreen.EditAccountScreen
import com.lazysloth.pocketlog.ui.screen.contentscreen.TransactionDetailsScreen
import com.lazysloth.pocketlog.ui.screen.contentscreen.TransactionEditScreen
import com.lazysloth.pocketlog.ui.screen.home.HomeScreen
import com.lazysloth.pocketlog.ui.screen.other.CameraPreviewScreen
import com.lazysloth.pocketlog.ui.screen.other.SettingsScreen
import com.lazysloth.pocketlog.ui.screen.other.viewmodel.CameraViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenNav(
    context: Context,
    session: UserPersists,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier,
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState
) {

    var startRoute by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(Unit) {
//        val userExists = session.checkUser()
        startRoute = if (session.currentId != null) {
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
                        navController.navigate(AuthenticationNavigation.VERIFYING_SCREEN.name) {
                            popUpTo(
                                navController.graph.findStartDestination().id
                            ) { inclusive = true }
                        }
                    },
                    onClickAlreadyAUser = { navController.navigate(AuthenticationNavigation.LOGIN.name) },
                )
            }
            composable(route = AuthenticationNavigation.VERIFYING_SCREEN.name) {
                VerificationScreen(
                    onVerificationSuccess ={
                        navController.navigate(AuthenticationNavigation.HOME_SCREEN.name)
                    } ,
                    onBackClick = { navController.popBackStack() }
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
                    onClickAiCam = {
                        navController.navigate("camera")
                    },
                    onClickAddAccount = {
                        navController.navigate("add_account")
                    },
                    onClickEditAccount = {
                        navController.navigate("edit_account")
                    },
                    onClickAddCategory = {
                        navController.navigate("add_category_screen")
                    }

                )
            }

            composable("addTransaction") {
                AddTransactionScreen(
                    popBackStack = { navController.popBackStack() },

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
                val authViewModel : AuthViewModel = koinViewModel()
                SettingsScreen(onClickLogout = {
//                    session.logout()
                    authViewModel.logOut()
                    navController.navigate("login") {
                        popUpTo(
                            navController.graph.findStartDestination().id
                        ) {
                            inclusive = true
                        }
                    }
                })
            }

            composable(route = "camera") {
                CameraPreviewScreen(
                    onClickGallery = {navController.navigate("Gallery")},
                    scope = scope,
                    scaffoldState = scaffoldState,
                    navToAddTransitionScreen = {
                        navController.navigate("addTransaction") {
                            popUpTo("camera") {
                                inclusive = true
                            }
                        }
                    }
                )
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
            composable(route= "add_category_screen") {
                AddCategoryScreen(
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