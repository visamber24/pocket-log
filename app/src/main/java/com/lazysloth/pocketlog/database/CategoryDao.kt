package com.lazysloth.pocketlog.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lazysloth.pocketlog.database.data.Category1
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category1: Category1)
    @Update
    suspend fun update(category1: Category1)
    @Delete
    suspend fun delete(category1: Category1)
//    @Query("SELECT * FROM category WHERE userId=:userId")
//    fun getCategoryByUserId(userId: Int): Flow<List<Category1>>
    @Query("SELECT * FROM category WHERE id=:id")
    fun getCategoryByCategoryId(id: Long): Flow<Category1>
}