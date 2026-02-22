package com.lazysloth.pocketlog.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.lazysloth.pocketlog.ExpenseApplication
import com.lazysloth.pocketlog.ui.screen.home.viewmodel.AddTransactionScreenViewmodel
import com.lazysloth.pocketlog.ui.screen.home.viewmodel.DashboardScreenViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            AddTransactionScreenViewmodel(expenseApplication().container.transactionRepository)
        }
        initializer {
            DashboardScreenViewModel(
                expenseApplication().container.transactionRepository
            )
        }
    }
}

fun CreationExtras.expenseApplication(): ExpenseApplication = 
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ExpenseApplication)