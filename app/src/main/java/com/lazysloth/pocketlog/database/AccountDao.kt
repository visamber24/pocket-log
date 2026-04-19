package com.lazysloth.pocketlog.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lazysloth.pocketlog.database.data.Account1

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveAccount(account: Account1)
    @Update
    suspend fun updateAccount(account: Account1)
    @Delete
    suspend fun deleteAccount(account: Account1)
    @Query("SELECT * FROM accounts WHERE userId =:userId")
    suspend fun getAccountByUserId(userId:Int): Account1
}