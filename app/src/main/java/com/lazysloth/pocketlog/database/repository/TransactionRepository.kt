package com.lazysloth.pocketlog.database.repository

import com.lazysloth.pocketlog.database.data.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository  {
    suspend fun assignUserId(transaction: Transaction)
     suspend fun insertTransaction(transaction: Transaction)

     suspend fun deleteTransaction(transaction: Transaction)

    suspend fun deleteTransactionById(id:Int?)

     suspend fun updateTransaction(transaction: Transaction) 

     fun getTransaction(id: Int): Flow<Transaction?>

    fun getAllTransactions(userId: Int?) : Flow<List<Transaction>>



}