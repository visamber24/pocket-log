package com.lazysloth.pocketlog.di

import com.lazysloth.pocketlog.database.repository.OfflineTransactionRepository
import com.lazysloth.pocketlog.database.repository.PocketLogDatabase
import com.lazysloth.pocketlog.database.repository.TransactionRepository
import com.lazysloth.pocketlog.database.repository.UserRepository
import com.lazysloth.pocketlog.ui.screen.authentication.viewmodel.AuthViewModel
import com.lazysloth.pocketlog.ui.screen.other.viewmodel.AddTransactionScreenViewmodel
import com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel.DashboardScreenViewModel
import com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel.EditTransactionScreenViewmodel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // 1.DATABASE
    single {
        PocketLogDatabase.getDatabase(get())
    }

    // 2.DAO
    single { get<PocketLogDatabase>().userDao() }
    single { get<PocketLogDatabase>().getTransactionItem() }

    // 3.Preference
    single { UserPersists(get()) }

    // 4.Repository
    single<TransactionRepository> {
        OfflineTransactionRepository(get())
    }
    single<UserRepository> { UserRepository(get()) }

    // 5.viewmodel

    viewModel {
        AuthViewModel(
            get(),
            get()
        )
    }

    viewModel {
        DashboardScreenViewModel(
            get(), // TransactionRepository
            get(), // UserRepository
            get()  // UserPersists
        )
    }
    viewModel {
        AddTransactionScreenViewmodel(
            get(),
            get(),

        )
    }
    viewModel{
        EditTransactionScreenViewmodel(
            get(),
            get()
        )
    }
}