package com.lazysloth.pocketlog.database.repository

import com.lazysloth.pocketlog.database.data.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository  {

     suspend fun insertTransaction(transaction: Transaction)

     suspend fun deleteTransaction(transaction: Transaction)

    suspend fun deleteTransactionById(id:Int?)

     suspend fun updateTransaction(transaction: Transaction) 

     fun getTransactionByTransactionId(id: Int): Flow<Transaction?>

    fun getAllTransactions(userId: Int?) : Flow<List<Transaction>>



}