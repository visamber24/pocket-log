package com.lazysloth.pocketlog.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.lazysloth.pocketlog.database.data.Account1
import com.lazysloth.pocketlog.database.data.TransactionWithAccount
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAccount(account: Account1)
    @Update
    suspend fun updateAccount(account: Account1)
    @Delete
    suspend fun deleteAccount(account: Account1)
    @Query("SELECT * FROM accounts WHERE userId =:userId")
    fun getAccountByUserId(userId:Int): Flow<List<Account1>>
    @Query("SELECT * FROM accounts WHERE id = :accountId")
    fun getAccountByAccountId(accountId: Long?): Flow<Account1>
    @Query("SELECT name FROM accounts WHERE userId=:userId")
    fun getAccountName(userId: Int): Flow<List<String>>


}