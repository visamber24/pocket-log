/*
package com.lazysloth.pocketlog.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.lazysloth.pocketlog.ExpenseApplication
import com.lazysloth.pocketlog.ui.screen.authentication.viewmodel.AuthViewModel
import com.lazysloth.pocketlog.ui.screen.home.viewmodel.AddTransactionScreenViewmodel
import com.lazysloth.pocketlog.ui.screen.home.viewmodel.DashboardScreenViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            println("DEBUG: Factory is running")
            AddTransactionScreenViewmodel(
                expenseApplication().container.transactionRepository,
                expenseApplication().container.userRepository
            )
        }
        initializer {
            AuthViewModel(
                expenseApplication().container.userRepository,
                expenseApplication().container.transactionRepository
            )
        }

        initializer {
            DashboardScreenViewModel(
                expenseApplication().container.transactionRepository,
                expenseApplication().container.userRepository
                ,expenseApplication().container.
            )
        }
//        initializer {
//            UserPersists(
//                expenseApplication().container.transactionRepository
//            )
//        }

    }
}

fun CreationExtras.expenseApplication(): ExpenseApplication {
    val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
    if (app is ExpenseApplication) return app

    // Fallback: Try to get application from the ViewModelStoreOwner
    // This is often needed in newer Compose/Lifecycle versions
    val owner = this[androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY]
    if (owner is android.app.Activity) {
        return owner.application as ExpenseApplication
    }

    throw IllegalStateException("Could not find ExpenseApplication. Current key value: $app")
}*/
