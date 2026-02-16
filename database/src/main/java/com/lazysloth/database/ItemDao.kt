package com.lazysloth.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert
    suspend fun insert()

    @Delete
    suspend fun delete()

    @Update
    suspend fun update()

    @Query("SELECT * from items WHERE id = :id ")
    fun getItems(id: Int) : Flow<Records>
    @Query("SELECT * FROM items WHERE amount= 1")
    fun getAllItems()
}