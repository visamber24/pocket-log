package com.lazysloth.pocketlog.database

import android.content.Context
import com.lazysloth.pocketlog.database.repository.AuthRepository
import com.lazysloth.pocketlog.database.repository.OfflineTransactionRepository
import com.lazysloth.pocketlog.database.repository.PocketLogDatabase
import com.lazysloth.pocketlog.database.repository.TransactionRepository

interface AppContainer {
    val authRepository : AuthRepository
    val transactionRepository : TransactionRepository
}

class AppDataContainer(
    private val context : Context
) : AppContainer{
    override val transactionRepository: TransactionRepository by lazy {
        OfflineTransactionRepository(PocketLogDatabase.getDatabase(context).itemDao())
    }
    override val authRepository: AuthRepository
        get() = TODO("Not yet implemented")
}