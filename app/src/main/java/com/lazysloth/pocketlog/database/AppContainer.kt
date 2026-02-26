package com.lazysloth.pocketlog.database

import android.content.Context
import com.lazysloth.pocketlog.database.repository.UserRepository
import com.lazysloth.pocketlog.database.repository.OfflineTransactionRepository
import com.lazysloth.pocketlog.database.repository.PocketLogDatabase
import com.lazysloth.pocketlog.database.repository.TransactionRepository

interface AppContainer {
    val authRepository : UserRepository
    val transactionRepository : TransactionRepository
}

class AppDataContainer(
    private val context : Context
) : AppContainer{
    override val transactionRepository: TransactionRepository by lazy {
        OfflineTransactionRepository(PocketLogDatabase.getDatabase(context).getTransactionItem())
    }
    override val authRepository: UserRepository by lazy {
        UserRepository(PocketLogDatabase.getDatabase(context).userDao())
    }

}