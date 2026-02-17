package com.lazysloth.pocketlog.database.repository

import com.lazysloth.pocketlog.database.Records
import kotlinx.coroutines.flow.Flow

interface TransactionRepository  {
     suspend fun insertTransaction(records: Records)



     suspend fun deleteTransaction(records: Records)



//     suspend fun updateTransaction() {
//        TODO("Not yet implemented")
//    }

//     fun getItems(id: Int): Flow<Records> {
//        TODO("Not yet implemented")
//    }

    fun getAllTransactions() : Flow<List<Records>>



}