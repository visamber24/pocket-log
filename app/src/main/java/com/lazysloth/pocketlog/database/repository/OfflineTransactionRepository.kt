package com.lazysloth.pocketlog.database.repository

import com.lazysloth.pocketlog.database.Records
import com.lazysloth.pocketlog.database.TransactionItemDao

class OfflineTransactionRepository(
    private val transactionItemDao: TransactionItemDao
) : TransactionRepository {
    override suspend fun insertTransaction(records: Records) = transactionItemDao.insert(records)

    override suspend fun deleteTransaction(records: Records) = transactionItemDao.delete(records)

    override fun getAllTransactions() = transactionItemDao.getAllTransactionItems()

}