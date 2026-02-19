package com.lazysloth.pocketlog.database.repository

import com.lazysloth.pocketlog.database.Transaction
import com.lazysloth.pocketlog.database.TransactionItemDao

class OfflineTransactionRepository(
    private val transactionItemDao: TransactionItemDao
) : TransactionRepository {
    override suspend fun insertTransaction(transaction: Transaction) = transactionItemDao.insert(transaction)

    override suspend fun deleteTransaction(transaction: Transaction) = transactionItemDao.delete(transaction)

    override suspend fun updateTransaction(transaction: Transaction) {
        return transactionItemDao.update(transaction)
    }

    override fun getAllTransactions() = transactionItemDao.getAllTransactionItems()

}