package com.lazysloth.pocketlog.di

import com.lazysloth.pocketlog.database.repository.AccountRepository
import com.lazysloth.pocketlog.database.repository.OfflineAccountRepository
import com.lazysloth.pocketlog.database.repository.OfflineTransactionRepository
import com.lazysloth.pocketlog.database.repository.PocketLogDatabase
import com.lazysloth.pocketlog.database.repository.TransactionRepository
import com.lazysloth.pocketlog.database.repository.UserRepository
import com.lazysloth.pocketlog.ui.screen.authentication.viewmodel.AuthViewModel
import com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel.AddAccountViewModel
import com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel.EditAccountScreenViewModel
import com.lazysloth.pocketlog.ui.screen.other.viewmodel.AddTransactionScreenViewmodel
import com.lazysloth.pocketlog.ui.screen.home.viewmodel.DashboardScreenViewModel
import com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel.EditTransactionScreenViewmodel
import com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel.ProfileScreenViewmodel
import com.lazysloth.pocketlog.ui.screen.home.viewmodel.AccountScreenViewModel
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
    single { get<PocketLogDatabase>().getAccount() }

    // 3.Preference
    single { UserPersists(get(), get()) }

    // 4.Repository
//    single { TransactionRepository(get()) }
    single<TransactionRepository> {
        OfflineTransactionRepository(get())
    }
    single<UserRepository> { UserRepository(get()) }
    single <AccountRepository>{ OfflineAccountRepository(get()) }

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
            get(), // UserPersists
            get() //accountRepository
        )
    }
    viewModel {
        AddTransactionScreenViewmodel(
            get(),
            get(),
            get()
            )
    }
    viewModel {
        EditTransactionScreenViewmodel(
            get(),
            get(),
            get(),
        )
    }
    viewModel {
        ProfileScreenViewmodel(
            get(),
            get()
        )
    }
    viewModel {
        AddAccountViewModel(
            get(),
            get()

        )
    }
    viewModel {
        AccountScreenViewModel(
            get(),
            get()
        )
    }
    viewModel {
        EditAccountScreenViewModel(
            get(),
            get()
        )
    }


}