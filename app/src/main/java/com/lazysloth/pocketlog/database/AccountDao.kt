package com.lazysloth.pocketlog.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lazysloth.pocketlog.database.data.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAccount(account: Account)
    @Update
    suspend fun updateAccount(account: Account)
    @Delete
    suspend fun deleteAccount(account: Account)
    @Query("SELECT * FROM accounts WHERE userId =:userId")
    fun getAccountByUserId(userId:Int): Flow<List<Account>>
    @Query("SELECT * FROM accounts WHERE id = :accountId")
    fun getAccountByAccountId(accountId: Long?): Flow<Account>
    @Query("SELECT name FROM accounts WHERE userId=:userId")
    fun getAccountName(userId: Int): Flow<List<String>>


}