package com.lazysloth.pocketlog.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionItemDao {
    @Insert
    suspend fun insert(records: Records)

    @Delete
    suspend fun delete(records: Records)

    @Update
    suspend fun update(records: Records)

    @Query("SELECT * from items WHERE id = :id ")
    fun getItems(id: Int) : Flow<Records>
    @Query("SELECT * FROM items WHERE amount= 1")
    fun getAllTransactionItems() : Flow<List<Records>>
}