package com.lazysloth.pocketlog.database.repository

import com.lazysloth.pocketlog.database.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository  {
     suspend fun insertTransaction(transaction: Transaction)

     suspend fun deleteTransaction(transaction: Transaction)



     suspend fun updateTransaction(transaction: Transaction) 

     fun getTransaction(id: Int): Flow<Transaction?>

    fun getAllTransactions() : Flow<List<Transaction>>



}