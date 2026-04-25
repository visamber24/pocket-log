package com.lazysloth.pocketlog.database.repository

import com.lazysloth.pocketlog.database.data.Transaction
import com.lazysloth.pocketlog.database.TransactionItemDao
import com.lazysloth.pocketlog.database.data.TransactionWithAccount
import kotlinx.coroutines.flow.Flow

class OfflineTransactionRepository(
    private val transactionItemDao: TransactionItemDao
) : TransactionRepository {


    override suspend fun insertTransaction(transaction: Transaction,balanceDelta: Double) =
        transactionItemDao.insertTransactionAndUpdateAccountBalance(transaction, balanceDelta = balanceDelta)

    override suspend fun deleteTransaction(transaction: Transaction) =
        transactionItemDao.delete(transaction)

    override suspend fun deleteTransactionById(id: Int?) = transactionItemDao.deleteById(id)

    override suspend fun updateTransaction(transaction: Transaction) {
        return transactionItemDao.update(transaction)
    }

    override fun getAllTransactions(userId: Int?) =
        transactionItemDao.getAllTransactionByUserId(userId)

    override  fun getTransactionWithAccountByTransactionId(id: Int): Flow<TransactionWithAccount?> {
        return transactionItemDao.getTransactionWithAccountByTransactionId(id)
    }

    override fun getTransactionWithAccount(userId: Int): Flow<List<TransactionWithAccount>> {
        return transactionItemDao.getTransactionWithAccount(userId)
    }
}